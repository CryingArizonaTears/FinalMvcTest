package com.senla.dao;

import com.senla.api.dao.IMessageDao;
import com.senla.model.Message;
import org.springframework.stereotype.Repository;

@Repository
public class MessageDao extends AbstractDao<Message> implements IMessageDao {

    @Override
    protected Class<Message> getClazz() {
        return Message.class;
    }
}
