package com.senla.dao;

import com.senla.api.dao.IChatDao;
import com.senla.model.Chat;
import org.springframework.stereotype.Repository;

@Repository
public class ChatDao extends AbstractDao<Chat> implements IChatDao {

    @Override
    protected Class<Chat> getClazz() {
        return Chat.class;
    }
}
