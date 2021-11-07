package com.senla.api.dao;

import com.senla.model.UserProfile;

import java.util.List;

public interface IUserProfileDao {

    List<UserProfile> getAll();

    UserProfile get(Long id);

    UserProfile update(UserProfile userProfile);

    void save(UserProfile userProfile);

    void delete(Long id);
}
