package com.senla.api.service;

public interface IUserProfileService {

    void registration(String username, String password, String fullName);

    void editProfile(Long id, String username, String password, String fullName);
}
