package com.senla.service;

import com.senla.annotation.Logging;
import com.senla.api.dao.IUserProfileDao;
import com.senla.api.service.IUserAuthenticationService;
import com.senla.model.dto.UserCredentialsDto;
import com.senla.model.dto.UserDto;
import com.senla.model.dto.UserProfileDto;
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

@Transactional
@Service
public class UserAuthenticationService implements IUserAuthenticationService {

    @Autowired
    private IUserProfileDao userDao;
    @Autowired
    private ExtendedModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Logging
    public UserProfileDto getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return getUserProfileByUsername(currentPrincipalName);
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

}
