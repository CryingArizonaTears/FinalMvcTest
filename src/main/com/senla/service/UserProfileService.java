package com.senla.service;

import com.senla.api.dao.IUserLoginDao;
import com.senla.api.dao.IUserProfileDao;
import com.senla.api.service.IUserProfileService;
import com.senla.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@Service
public class UserProfileService implements IUserProfileService {
    @Autowired
    private IUserLoginDao userLoginDao;
    @Autowired
    private IUserProfileDao userProfileDao;


    @Override
    public void registration(String username, String password, String fullName) {

        UserProfile userProfile = new UserProfile();
        UserLogin userLogin = new UserLogin();
        userProfile.setFullName(fullName);
        userProfile.setRole(Role.USER);
        userProfile.setAvgRating(0D);
        userLogin.setUsername(username);
        userLogin.setPassword(password);
        userLogin.setUserProfile(userProfile);
        userProfile.setRatings(new ArrayList<>());
        userProfile.setChats(new ArrayList<>());
        userProfile.setAds(new ArrayList<>());
        userProfileDao.save(userProfile);
        userLoginDao.save(userLogin);

    }

    @Override
    public void editProfile(Long id, String username, String password, String fullName) {

        UserProfile userProfile = userProfileDao.get(id);
        UserLogin userLogin = userLoginDao.get(id);
        userProfile.setFullName(fullName);
        userLogin.setUsername(username);
        userLogin.setPassword(password);
        userProfileDao.update(userProfile);
        userLoginDao.update(userLogin);

    }
}
