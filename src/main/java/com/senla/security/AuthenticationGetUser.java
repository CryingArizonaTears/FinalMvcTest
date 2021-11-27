package com.senla.security;

import com.senla.api.service.IUserService;
import com.senla.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationGetUser {

    @Autowired
    private IUserService userService;

    public UserProfile getUserProfileByAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userService.getByUsername(currentPrincipalName);
    }


}
