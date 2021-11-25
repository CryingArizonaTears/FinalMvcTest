package com.senla.security;

import com.senla.api.service.IUserService;
import com.senla.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProfile userProfile = userService.getByUsername(username);
        return CustomUserDetails.fromUserToCustomUserDetails(userProfile);
    }
}
