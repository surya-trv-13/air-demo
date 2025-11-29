package com.panunited.airdemo.service;

import com.panunited.airdemo.models.PortalUser;
import com.panunited.airdemo.repositories.PortalUserRepository;
import com.panunited.airdemo.dto.PortalLoginRequest;
import com.panunited.airdemo.dto.PortalLoginResponse;
import com.panunited.airdemo.enums.Role;
import com.panunited.airdemo.exception.ExceptionConstants;
import com.panunited.airdemo.exception.InvalidPrincipalException;
import com.panunited.airdemo.exception.ResourceNotFoundException;
import com.panunited.airdemo.jwt.JWTUtils;
import com.panunited.airdemo.models.BatchingPlant;
import com.panunited.airdemo.models.Plant;
import com.panunited.airdemo.models.Users;
import com.panunited.airdemo.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service("WebAuthService")
@Slf4j
public class WebLoginService {

    private final PortalUserRepository portalUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final UserRepository userRepository;

    public WebLoginService(PortalUserRepository portalUserRepository, PasswordEncoder passwordEncoder, JWTUtils jwtUtils, UserRepository userRepository) {
        this.portalUserRepository = portalUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @Transactional
    public PortalLoginResponse login(PortalLoginRequest loginRequest) throws Exception {
        try {

            Optional<Users> users = portalUserRepository.findUsersByUsername(loginRequest.getUsername());
            if (users.isPresent()) {
                Users user = users.get();
                if (!Boolean.TRUE.equals(user.getIsActive())) {
                    throw new InvalidPrincipalException(ExceptionConstants.LOGIN_INACTIVE_ACCOUNT);
                }

                if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                    throw new InvalidPrincipalException("Empty or unknown login credentials.");
                }
                Set<Role> roles = userRepository.getRoles(user.getId());
                Map<String, Object> metadata = getMetadata(user.getId());
                String accessToken = jwtUtils.generateAccessToken(user.getId(), roles, metadata);
                String refreshToken = jwtUtils.generateRefreshToken(user.getId());

                this.userRepository.updateUserLastLoginTime(user.getId());
                return new PortalLoginResponse("" ,accessToken, refreshToken);
            } else {
                throw new ResourceNotFoundException(String.format(ExceptionConstants.NOT_FOUND, "User"));
            }
        } catch(Exception ee) {
            throw new InvalidPrincipalException(ExceptionConstants.LOGIN_BAD_CREDENTIAL);
        }
    }

    private Map<String, Object> getMetadata(Long userId) {
        Map<String, Object> metadata = new HashMap<>();
        Optional<PortalUser> portalUserOptional =   portalUserRepository.findPortalUser(userId);
       portalUserOptional.ifPresent(portalUser -> {
            Plant plant = portalUser.getPlant();
            BatchingPlant batchingPlant = portalUser.getBatchingPlant();
            if (plant != null) {
                metadata.put("plantId", plant.getId());
            }
            if (batchingPlant != null) {
                metadata.put("batchingPlantId", batchingPlant.getId());
            }
        });
        return metadata;
    }

}
