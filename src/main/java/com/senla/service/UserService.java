package com.senla.service;

import com.senla.api.dao.IAdDao;
import com.senla.api.dao.IUserLoginDao;
import com.senla.api.dao.IUserProfileDao;
import com.senla.api.service.IUserService;
import com.senla.model.AdStatus;
import com.senla.model.Role;
import com.senla.model.UserLogin;
import com.senla.model.UserProfile;
import com.senla.model.dto.AdDto;
import com.senla.model.dto.UserDto;
import com.senla.model.dto.UserLoginDto;
import com.senla.model.dto.UserProfileDto;
import com.senla.model.dto.filter.AdFilter;
import com.senla.modelMapperMethods.ModelMapperMapList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class UserService implements IUserService {
    @Autowired
    private IUserLoginDao userLoginDao;
    @Autowired
    private IUserProfileDao userProfileDao;
    @Autowired
    private IAdDao adDao;
    @Autowired
    private ModelMapperMapList modelMapper;


    @Override
    public void registration(UserDto userDto) {

        UserProfile userProfile = modelMapper.map(userDto, UserProfile.class);
        UserLogin userLogin = modelMapper.map(userDto, UserLogin.class);
        userProfile.setRole(Role.USER);
        userProfile.setAvgRating(0D);
        userProfile.setUserLogin(userLogin);
        userLoginDao.save(userLogin);
        userProfileDao.save(userProfile);

    }

    @Override
    public void editLogin(UserLoginDto userLoginDto) {

        UserLogin userLogin = modelMapper.map(userLoginDto, UserLogin.class);
        userLoginDao.update(userLogin);
    }

    @Override
    public void editProfile(UserProfileDto userProfileDto) {

        UserProfile dtoUserProfile = modelMapper.map(userProfileDto, UserProfile.class);
        UserProfile userProfile = userProfileDao.get(dtoUserProfile.getId());
        userProfile.setFullName(dtoUserProfile.getFullName());
        userProfileDao.update(userProfile);

    }

    @Override
    public UserProfileDto getById(Long id) {
        UserProfile userProfile = userProfileDao.get(id);
        return modelMapper.map(userProfile, UserProfileDto.class);
    }


    @Override
    public List<AdDto> salesHistory(Long id) {
        AdFilter adFilter = new AdFilter();
        adFilter.setUserId(id);
        adFilter.setStatus(AdStatus.CLOSED);
        return modelMapper.mapList(adDao.getByFilter(adFilter), AdDto.class);
    }
}
