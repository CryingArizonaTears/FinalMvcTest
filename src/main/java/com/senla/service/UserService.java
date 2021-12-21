package com.senla.service;

import com.senla.annotation.Logging;
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
import com.senla.modelMapperMethods.ExtendedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private ExtendedModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IUserService userService;


    @Override
    @Logging
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
    @Logging
    public void editPassword(UserCredentialsDto userCredentialsDto) {
        UserProfile currentUser = modelMapper.map(userService.getCurrentUserProfile(), UserProfile.class);
        UserLogin userLogin = new UserLogin();
        userLogin.setId(userCredentialsDto.getId());
        userLogin.setPassword(passwordEncoder.encode(userCredentialsDto.getPassword()));
        if (currentUser.getRole().equals(Role.ROLE_USER)) {
            if (userLogin.getId().equals(currentUser.getId())) {
                userLoginDao.update(userLogin);
            } else {
                throw new SecurityException("Вы не можете редактировать чужой пароль");
            }
        } else if (currentUser.getRole().equals(Role.ROLE_ADMIN)) {
            userLoginDao.update(userLogin);
        }
    }


    @Override
    @Logging
    public void editProfile(UserProfileDto userProfileDto) {
        UserProfile currentUser = modelMapper.map(userService.getCurrentUserProfile(), UserProfile.class);
        UserProfile userProfile = userDao.get(userProfileDto.getId());
        if (userProfileDto.getFullName() != null) {
            userProfile.setFullName(userProfileDto.getFullName());
        }
        if (currentUser.getRole().equals(Role.ROLE_USER)) {
            if (currentUser.getId().equals(userProfile.getId())) {
                userDao.update(userProfile);
            } else {
                throw new SecurityException("Вы не можете редактировать чужой профиль");
            }
        } else if (userProfile.getRole().equals(Role.ROLE_ADMIN)) {
            if (userProfileDto.getRole() != null) {
                userProfile.setRole(userProfileDto.getRole());
            }
            userDao.update(userProfile);
        }
    }

    @Override
    @Logging
    public UserProfileDto getById(Long id) {
        return modelMapper.map(userDao.get(id), UserProfileDto.class);
    }

    @Override
    @Logging
    public UserProfileDto getUserProfileByUsername(String username) {
        UserFilter userFilter = new UserFilter();
        userFilter.setUsername(username);
        return userDao.getByFilter(userFilter).stream()
                .findFirst()
                .map(userProfile -> modelMapper.map(userProfile, UserProfileDto.class))
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    @Override
    @Logging
    public UserCredentialsDto getEncryptedUserCredentials(UserDto userDto) {
        UserCredentialsDto userCredentialsByUsername = getUserProfileByUsername(userDto.getUsername()).getUserLogin();
        if (!ObjectUtils.isEmpty(userCredentialsByUsername)) {
            if (passwordEncoder.matches(userDto.getPassword(), userCredentialsByUsername.getPassword())) {
                return userCredentialsByUsername;
            }
        }
        throw new SecurityException("Неправильный логин или пароль");
    }

    @Override
    @Logging
    public UserProfileDto getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return getUserProfileByUsername(currentPrincipalName);
    }

    @Override
    @Logging
    public List<AdDto> salesHistory(Long id) {
        AdFilter adFilter = new AdFilter();
        adFilter.setUserId(id);
        adFilter.setStatus(AdStatus.CLOSED);
        return modelMapper.mapList(adDao.getByFilter(adFilter), AdDto.class);
    }
}
