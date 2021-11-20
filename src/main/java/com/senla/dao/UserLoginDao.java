package com.senla.dao;

import com.senla.api.dao.IUserLoginDao;
import com.senla.model.UserLogin;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository
public class UserLoginDao extends AbstractDao<UserLogin> implements IUserLoginDao {

    @Override
    protected Class<UserLogin> getClazz() {
        return UserLogin.class;
    }

    @Override
    protected Predicate[] getPredicates(Object object, CriteriaBuilder criteriaBuilder, Root root) {
        return null;
    }
}
