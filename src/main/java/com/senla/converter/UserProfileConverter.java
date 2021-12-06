package com.senla.converter;

import com.senla.model.dto.UserProfileDto;
import com.senla.security.CustomUserDetails;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

public class UserProfileConverter implements Converter<UserProfileDto, CustomUserDetails> {
    @Override
    public CustomUserDetails convert(MappingContext<UserProfileDto, CustomUserDetails> mappingContext) {
        UserProfileDto source = mappingContext.getSource();
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUsername(source.getUserLogin().getUsername());
        customUserDetails.setPassword(source.getUserLogin().getPassword());
        customUserDetails.setGrantedAuthorities(Collections.singletonList(new SimpleGrantedAuthority(source.getRole().getName())));
        return customUserDetails;
    }
}
