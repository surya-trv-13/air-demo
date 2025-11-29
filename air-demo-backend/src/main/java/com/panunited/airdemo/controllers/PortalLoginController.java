package com.panunited.airdemo.controllers;

import com.panunited.airdemo.dto.PortalLoginRequest;
import com.panunited.airdemo.dto.PortalLoginResponse;
import com.panunited.airdemo.exception.ExceptionConstants;
import com.panunited.airdemo.service.WebLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("WebAuthController")
@RequestMapping("/api/")
@Slf4j
public class PortalLoginController {

    private final WebLoginService webLoginService;

    public PortalLoginController(WebLoginService webLoginService) {
        this.webLoginService = webLoginService;
    }

    @PostMapping("login")
    public ResponseEntity<PortalLoginResponse> login(@RequestBody PortalLoginRequest loginRequest) throws Exception {
        log.info("portal login end--" + loginRequest.toString());
        PortalLoginResponse response = new PortalLoginResponse();
        try {
            response = webLoginService.login(loginRequest);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(ExceptionConstants.LOGIN_BAD_CREDENTIAL);
        } catch (AuthenticationServiceException e) {
            throw new AuthenticationServiceException(ExceptionConstants.LOGIN_AUTHENTICATION_SERVICE_ISSUE);
        } catch (AuthenticationCredentialsNotFoundException e) {
            throw new AuthenticationCredentialsNotFoundException(
                    ExceptionConstants.LOGIN_AUTHENTICATION_CREDENTIALS_NOT_FOUND);

        } catch (CredentialsExpiredException e) {
            throw new CredentialsExpiredException(ExceptionConstants.LOGIN_AUTHENTICATION_CREDENTIALS_EXPIRED);
        }
        log.info("portal login end--" + response.toString());
        return ResponseEntity.ok(response);
    }
}
