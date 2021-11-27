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
import com.senla.model.dto.UserCredentialsDto;
import com.senla.model.dto.UserDto;
import com.senla.model.dto.UserProfileDto;
import com.senla.model.dto.filter.AdFilter;
import com.senla.model.dto.filter.UserFilter;
import com.senla.modelMapperMethods.ModelMapperMapList;
import com.senla.security.AuthenticationGetUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Transactional
@Service
public class UserService implements IUserService {
    @Autowired
    private IUserLoginDao userLoginDao;
    @Autowired
    private IUserProfileDao userDao;
    @Autowired
    private IAdDao adDao;
    @Autowired
    private ModelMapperMapList modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationGetUser authenticationGetUser;


    @Override
    public void registration(UserDto userDto) {

        UserProfile userProfile = modelMapper.map(userDto, UserProfile.class);
        UserLogin userLoginBuf = modelMapper.map(userDto, UserLogin.class);
        UserLogin userLogin = new UserLogin();
        userLogin.setUsername(userLoginBuf.getUsername());
        userLogin.setPassword(passwordEncoder.encode(userLoginBuf.getPassword()));
        userProfile.setRole(Role.ROLE_USER);
        userProfile.setAvgRating(0D);
        userProfile.setUserLogin(userLogin);
        userLoginDao.save(userLogin);
        userDao.save(userProfile);

    }

    @Override
    public void editPassword(UserCredentialsDto userCredentialsDto) {

        UserProfile userProfile = authenticationGetUser.getUserProfileByAuthentication();
        UserLogin userLogin = modelMapper.map(userCredentialsDto, UserLogin.class);
        if (userProfile.getRole().equals(Role.ROLE_USER)) {
            if (userLogin.getId().equals(userProfile.getId())) {
                userLoginDao.update(userLogin);
            } else {
                throw new SecurityException("Вы не можете редактировать чужие штуки");
            }
        }
        if (userProfile.getRole().equals(Role.ROLE_ADMIN)) {
            userLoginDao.update(userLogin);
        }
    }


    @Override
    public void editProfile(UserProfileDto userProfileDto) {

        UserProfile userProfileAuth = authenticationGetUser.getUserProfileByAuthentication();
        UserProfile dtoUserProfile = modelMapper.map(userProfileDto, UserProfile.class);
        UserProfile userProfile = userDao.get(dtoUserProfile.getId());
        userProfile.setFullName(dtoUserProfile.getFullName());
        if (userProfileAuth.getRole().equals(Role.ROLE_USER)) {
            if (userProfileAuth.getId().equals(userProfile.getId())) {
                userDao.update(userProfile);
            } else {
                throw new SecurityException("Вы не можете редактировать чужие штуки");
            }
        }
        if (userProfile.getRole().equals(Role.ROLE_ADMIN)) {
            userDao.update(userProfile);
        }
    }

    @Override
    public UserProfileDto getById(Long id) {
        UserFilter userFilter = new UserFilter();
        userFilter.setId(id);
        return modelMapper.map(userDao.getByFilter(userFilter).stream().findFirst().orElse(null), UserProfileDto.class);
    }

    @Override
    public UserProfile getByUsername(String username) {
        UserFilter userFilter = new UserFilter();
        userFilter.setUsername(username);

        return userDao.getByFilter(userFilter).stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public UserLogin getByUsernameAndPassword(UserDto userDto) {
        UserLogin userLoginDto = modelMapper.map(userDto, UserLogin.class);
        UserLogin userLogin = getByUsername(userLoginDto.getUsername()).getUserLogin();
        if (!ObjectUtils.isEmpty(userLogin)) {
            if (passwordEncoder.matches(userLoginDto.getPassword(), userLogin.getPassword())) {
                return userLogin;
            }
        }
        throw new NullPointerException();
    }

    @Override
    public List<AdDto> salesHistory(Long id) {
        AdFilter adFilter = new AdFilter();
        adFilter.setUserId(id);
        adFilter.setStatus(AdStatus.CLOSED);
        return modelMapper.mapList(adDao.getByFilter(adFilter), AdDto.class);
    }
}
