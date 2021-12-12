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
import com.senla.modelMapperMethods.ExtendedModelMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Log4j
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
    public void registration(UserDto userDto) {
        log.debug("Method: registration, входящий: " + userDto.toString());
        UserProfile userProfile = modelMapper.map(userDto, UserProfile.class);
        UserLogin userLoginBuf = modelMapper.map(userDto, UserLogin.class);
        UserLogin userLogin = new UserLogin();
        userLogin.setUsername(userLoginBuf.getUsername());
        userLogin.setPassword(passwordEncoder.encode(userLoginBuf.getPassword()));
        userProfile.setRole(Role.ROLE_USER);
        userProfile.setAvgRating(0D);
        userProfile.setUserLogin(userLogin);
        log.debug("Method: registration, выходящий: " + userLogin.toString());
        log.debug("Method: registration, выходящий: " + userProfile.toString());
        userLoginDao.save(userLogin);
        userDao.save(userProfile);

    }

    @Override
    public void editPassword(UserCredentialsDto userCredentialsDto) {
        log.debug("Method: editPassword, входящий: " + userCredentialsDto.toString());
        UserProfile currentUser = modelMapper.map(userService.getCurrentUserProfile(), UserProfile.class);
        UserLogin userLogin = new UserLogin();
        userLogin.setId(userCredentialsDto.getId());
        userLogin.setPassword(passwordEncoder.encode(userCredentialsDto.getPassword()));
        if (currentUser.getRole().equals(Role.ROLE_USER)) {
            if (userLogin.getId().equals(currentUser.getId())) {
                log.debug("Method: editPassword, выходящий: " + userLogin.toString());
                userLoginDao.update(userLogin);
            } else {
                throw new SecurityException("Вы не можете редактировать чужой пароль");
            }
        } else if (currentUser.getRole().equals(Role.ROLE_ADMIN)) {
            log.debug("Method: editPassword, выходящий: " + userLogin.toString());
            userLoginDao.update(userLogin);
        }
    }


    @Override
    public void editProfile(UserProfileDto userProfileDto) {
        log.debug("Method: editProfile, входящий: " + userProfileDto.toString());
        UserProfile currentUser = modelMapper.map(userService.getCurrentUserProfile(), UserProfile.class);
        UserProfile userProfile = userDao.get(userProfileDto.getId());
        if (userProfileDto.getFullName() != null) {
            userProfile.setFullName(userProfileDto.getFullName());
        }
        if (currentUser.getRole().equals(Role.ROLE_USER)) {
            if (currentUser.getId().equals(userProfile.getId())) {
                log.debug("Method: editProfile, выходящий: " + userProfile.toString());
                userDao.update(userProfile);
            } else {
                throw new SecurityException("Вы не можете редактировать чужой профиль");
            }
        } else if (userProfile.getRole().equals(Role.ROLE_ADMIN)) {
            if (userProfileDto.getRole() != null) {
                userProfile.setRole(userProfileDto.getRole());
            }
            log.debug("Method: editProfile, выходящий: " + userProfile.toString());
            userDao.update(userProfile);
        }
    }

    @Override
    public UserProfileDto getById(Long id) {
        log.debug("Method: getById, входящий: " + id);
        UserProfileDto userProfileDto = modelMapper.map(userDao.get(id), UserProfileDto.class);
        log.debug("Method: getById, выходящий: " + userProfileDto.toString());
        return userProfileDto;
    }

    @Override
    public UserProfileDto getUserProfileByUsername(String username) {
        log.debug("Method: getUserProfileByUsername, входящий: " + username);
        UserFilter userFilter = new UserFilter();
        userFilter.setUsername(username);
        UserProfileDto userProfileDto = userDao.getByFilter(userFilter).stream()
                .findFirst()
                .map(userProfile -> modelMapper.map(userProfile, UserProfileDto.class))
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        log.debug("Method: getUserProfileByUsername, выходящий: " + userProfileDto.toString());
        return userProfileDto;
    }

    @Override
    public UserCredentialsDto getEncryptedUserCredentials(UserDto userDto) {
        log.debug("Method: getEncryptedUserCredentials, входящий: " + userDto.toString());
        UserCredentialsDto userCredentialsByUsername = getUserProfileByUsername(userDto.getUsername()).getUserLogin();
        if (!ObjectUtils.isEmpty(userCredentialsByUsername)) {
            if (passwordEncoder.matches(userDto.getPassword(), userCredentialsByUsername.getPassword())) {
                log.debug("Method: getEncryptedUserCredentials, выходящий: " + userCredentialsByUsername.toString());
                return userCredentialsByUsername;
            }
        }
        throw new SecurityException("Неправильный логин или пароль");
    }

    @Override
    public UserProfileDto getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        UserProfileDto userProfileDto = getUserProfileByUsername(currentPrincipalName);
        log.debug("Method: getCurrentUserProfile, выходящий: " + userProfileDto.toString());
        return userProfileDto;
    }

    @Override
    public List<AdDto> salesHistory(Long id) {
        log.debug("Method: salesHistory, входящий: " + id);
        AdFilter adFilter = new AdFilter();
        adFilter.setUserId(id);
        adFilter.setStatus(AdStatus.CLOSED);
        List<AdDto> adDtos = modelMapper.mapList(adDao.getByFilter(adFilter), AdDto.class);
        log.debug("Method: salesHistory, выходящий: " + adDtos.toString());
        return adDtos;
    }
}
