package com.senla.dao;

import com.senla.api.dao.IUserLoginDao;
import com.senla.model.UserLogin;
import org.springframework.stereotype.Repository;

@Repository
public class UserLoginDao extends AbstractDao<UserLogin> implements IUserLoginDao {

    @Override
    protected Class<UserLogin> getClazz() {
        return UserLogin.class;
    }
}
