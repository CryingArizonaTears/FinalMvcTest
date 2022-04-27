package com.senla.security;

import com.senla.api.service.IUserAuthenticationService;
import com.senla.api.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserAuthenticationService userAuthenticationService;
    @Qualifier("getModelMapper")
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return modelMapper.map(userAuthenticationService.getUserProfileByUsername(username), CustomUserDetails.class);
    }
}
