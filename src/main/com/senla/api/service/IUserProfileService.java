package com.senla.api.service;

import com.senla.model.UserProfile;
import com.senla.model.dto.UserProfileDto;

public interface IUserProfileService {

    void registration(String username, String password, String fullName);

    void editProfile(Long id, String username, String password, String fullName);

    public UserProfileDto getById(Long id);
}
