package com.senla.dao;

import com.senla.api.dao.IUserProfileDao;
import com.senla.model.UserProfile;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository
public class UserProfileDao extends AbstractDao<UserProfile> implements IUserProfileDao {

    @Override
    protected Class<UserProfile> getClazz() {
        return UserProfile.class;
    }

    @Override
    protected Predicate[] getPredicates(Object object, CriteriaBuilder criteriaBuilder, Root root) {
        return null;
    }
}
