package com.senla.api.service;

import com.senla.model.UserProfile;
import com.senla.model.UserLogin;
import com.senla.model.dto.AdDto;
import com.senla.model.dto.UserDto;
import com.senla.model.dto.UserCredentialsDto;
import com.senla.model.dto.UserProfileDto;

import java.util.List;

public interface IUserService {

    void registration(UserDto userDto);

    void editLogin(UserCredentialsDto userCredentialsDto);

    void editProfile(UserProfileDto userProfileDto);

    UserProfileDto getById(Long id);

    UserProfile getByUsername(String username);

    UserLogin getByUsernameAndPassword(UserDto userDto);

    List<AdDto> salesHistory(Long id);
}
