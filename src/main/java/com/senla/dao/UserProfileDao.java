package com.senla.dao;

import com.senla.api.dao.IUserProfileDao;
import com.senla.model.UserProfile;
import org.springframework.stereotype.Repository;

@Repository
public class UserProfileDao extends AbstractDao<UserProfile> implements IUserProfileDao {

    @Override
    protected Class<UserProfile> getClazz() {
        return UserProfile.class;
    }

}
