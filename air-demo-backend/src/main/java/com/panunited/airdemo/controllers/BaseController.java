package com.panunited.airdemo.controllers;

import com.panunited.airdemo.exception.InvalidPrincipalException;
import com.panunited.airdemo.security.models.UserPrincipal;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseController {
    public long getLoginUserId(UserPrincipal principal) throws InvalidPrincipalException {
        if (principal == null) {
            throw new InvalidPrincipalException("Empty or unknown login credentials.");
        }
        return principal.getId();
    }
}
