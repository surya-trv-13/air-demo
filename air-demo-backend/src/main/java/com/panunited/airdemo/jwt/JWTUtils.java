package com.panunited.airdemo.jwt;

import com.panunited.airdemo.enums.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JWTUtils {

    @Value("${security.secret-key}")
    private String jwtSecret;

    @Value("${security.accessTokenExpirationInMs}")
    private long jwtExpirationMs;

    @Value("${security.refreshTokenExpirationInMs}")
    private long jwtRefreshMs;

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        log.debug("Authorization Header: {}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove Bearer prefix
        }
        return null;
    }

    public Long getUserIdFromJwtToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload();
        return Long.parseLong(claims.getSubject());
    }

    public Map<String, Object> getMetadataFromJWT(String token) {
        Claims claims = Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token).getPayload();
        Object plantId = claims.get("plantId");
        Object batchingPlantId = claims.get("batchingPlantId");
        Map<String, Object> metadataMap = new HashMap<>();
        if (plantId != null) {
            metadataMap.put("plantId", plantId.toString());
        }
        if (batchingPlantId != null) {
            metadataMap.put("batchingPlantId", batchingPlantId.toString());
        }
        return metadataMap;
    }

    public Set<Role> getUserRolesFromJWT(String token) {
        Claims claims = Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token).getPayload();
        if (claims.get("roles", ArrayList.class) == null ) {
            return Collections.emptySet();
        }
        return (Set<Role>) claims.get("roles", ArrayList.class)
                .stream()
                .map(s -> Role.getRoleByName((String) s))
                .collect(Collectors.toSet());
    }


    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            System.out.println("Validate");
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String generateAccessToken(Long userId, Set<Role> roles, Map<String, Object> metadata) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        Claims claims = Jwts.claims().subject(userId.toString()).build();
        return Jwts.builder().claims(claims).claims(Map.of("roles", roles)).claims(metadata).issuedAt(now).expiration(expiryDate)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtRefreshMs);

        Claims claims = Jwts.claims().subject(userId.toString()).build();
        return Jwts.builder().claims(claims).issuedAt(now).expiration(expiryDate)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
}
