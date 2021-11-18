package com.senla.api.dao;

import com.senla.model.Chat;
import com.senla.model.dto.filter.ChatFilter;

import java.util.List;

public interface IChatDao {

    List<Chat> getAll();

    Chat get(Long id);

    Chat update(Chat chat);

    void save(Chat chat);

    void delete(Long id);

    List<Chat> getByFilter(ChatFilter chatFilter);
}
