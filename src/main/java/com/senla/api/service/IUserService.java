package com.senla.api.service;

import com.senla.model.dto.UserDto;
import com.senla.model.dto.UserLoginDto;
import com.senla.model.dto.UserProfileDto;

public interface IUserService {

    void registration(UserDto userDto);

    public void editLogin(UserLoginDto userLoginDto);

    public void editProfile(Long id, UserProfileDto userProfileDto);

    public UserProfileDto getById(Long id);
}
