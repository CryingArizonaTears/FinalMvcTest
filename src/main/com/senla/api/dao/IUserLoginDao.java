package com.senla.api.dao;

import com.senla.model.UserLogin;

import java.util.List;

public interface IUserLoginDao {

    List<UserLogin> getAll();

    UserLogin get(Long id);

    UserLogin update(UserLogin userLogin);

    void save(UserLogin userLogin);

    void delete(Long id);
}
