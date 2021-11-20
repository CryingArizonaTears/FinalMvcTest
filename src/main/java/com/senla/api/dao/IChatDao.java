package com.senla.api.dao;

import com.senla.model.Chat;

import java.util.List;

public interface IChatDao {

    List<Chat> getAll();

    List<Chat> getByFilter(Object entity);

    Chat get(Long id);

    Chat update(Chat chat);

    void save(Chat chat);

    void delete(Long id);
}
