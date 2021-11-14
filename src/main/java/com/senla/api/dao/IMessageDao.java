package com.senla.api.dao;

import com.senla.model.Message;

import java.util.List;

public interface IMessageDao {

    List<Message> getAll();

    Message get(Long id);

    Message update(Message message);

    void save(Message message);

    void delete(Long id);
}
