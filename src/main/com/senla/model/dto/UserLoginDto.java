package com.senla.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {
    private Long id;
    private String username;
    private String password;
    private UserProfileDto userProfile;
}
