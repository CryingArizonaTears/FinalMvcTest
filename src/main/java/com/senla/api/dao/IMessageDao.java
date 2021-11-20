package com.senla.api.dao;

import com.senla.model.Message;

import javax.persistence.Entity;
import java.util.List;

public interface IMessageDao {

    List<Message> getAll();

    List<Message> getByFilter(Entity entity);

    Message get(Long id);

    Message update(Message message);

    void save(Message message);

    void delete(Long id);
}
