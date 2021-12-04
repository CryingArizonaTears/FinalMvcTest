package com.senla.api.service;

import com.senla.model.dto.AdDto;
import com.senla.model.dto.UserCredentialsDto;
import com.senla.model.dto.UserDto;
import com.senla.model.dto.UserProfileDto;

import java.util.List;

public interface IUserService {

    void registration(UserDto userDto);

    void editPassword(UserCredentialsDto userCredentialsDto);

    void editProfile(UserProfileDto userProfileDto);

    UserProfileDto getById(Long id);

    UserProfileDto getByUsername(String username);

    UserCredentialsDto getByUsernameAndPassword(UserDto userDto);

    UserProfileDto getCurrentUserProfile();

    List<AdDto> salesHistory(Long id);
}
