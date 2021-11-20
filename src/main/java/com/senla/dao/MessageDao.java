package com.senla.dao;

import com.senla.api.dao.IMessageDao;
import com.senla.model.Message;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository
public class MessageDao extends AbstractDao<Message> implements IMessageDao {

    @Override
    protected Class<Message> getClazz() {
        return Message.class;
    }

    @Override
    protected Predicate[] getPredicates(Object object, CriteriaBuilder criteriaBuilder, Root root) {
        return null;
    }
}
