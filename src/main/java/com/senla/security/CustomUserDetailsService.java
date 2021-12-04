package com.senla.security;

import com.senla.api.service.IUserService;
import com.senla.model.UserProfile;
import com.senla.modelMapperMethods.ExtendedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserService userService;
    @Autowired
    private ExtendedModelMapper modelMapper;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProfile userProfile = modelMapper.map(userService.getByUsername(username), UserProfile.class);
        CustomUserDetails customUserDetails = modelMapper.map(userProfile.getUserLogin(), CustomUserDetails.class);
        customUserDetails.setGrantedAuthorities(Collections.singletonList(new SimpleGrantedAuthority(userProfile.getRole().getName())));
        return customUserDetails;
    }
}
