package com.senla.api.service;

import com.senla.model.dto.UserCredentialsDto;
import com.senla.model.dto.UserDto;
import com.senla.model.dto.UserProfileDto;

public interface IUserAuthenticationService {

    UserProfileDto getCurrentUserProfile();
    UserProfileDto getUserProfileByUsername(String username);
    UserCredentialsDto getEncryptedUserCredentials(UserDto userDto);
}
