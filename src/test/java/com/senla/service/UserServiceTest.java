package com.senla.service;

import com.senla.api.dao.IUserLoginDao;
import com.senla.api.dao.IUserProfileDao;
import com.senla.api.service.IUserAuthenticationService;
import com.senla.model.Role;
import com.senla.model.UserLogin;
import com.senla.model.UserProfile;
import com.senla.model.dto.UserCredentialsDto;
import com.senla.model.dto.UserDto;
import com.senla.model.dto.UserProfileDto;
import com.senla.modelMapperMethods.ExtendedModelMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private IUserAuthenticationService userAuthenticationService;
    @Mock
    private IUserLoginDao userLoginDao;
    @Mock
    private IUserProfileDao userDao;
    @Mock
    private ExtendedModelMapper modelMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    private final UserProfile userProfile = new UserProfile();
    private final UserLogin userLogin = new UserLogin();

    @BeforeEach
    public void beforeTests() {
        UserDto userDto = new UserDto();
        userLogin.setId(1L);
        userProfile.setId(1L);
        userLogin.setUsername("testUsername");
        userLogin.setPassword("testPasswordDecoded");
        userProfile.setFullName("testFullName");
        Mockito.when(passwordEncoder.encode(ArgumentMatchers.eq("testPasswordDecoded"))).thenReturn("testPasswordEncoded");
        Mockito.when(modelMapper.map(userDto, UserProfile.class)).thenReturn(userProfile);
        Mockito.when(modelMapper.map(userDto, UserLogin.class)).thenReturn(userLogin);
        userService.registration(userDto);
    }


    @Test
    void testRegistration_Successful() {

        Mockito.verify(userLoginDao).save(ArgumentMatchers.argThat(userLoginForSave -> userLogin.getUsername().equals(userLoginForSave.getUsername()) && "testPasswordEncoded".equals(userLoginForSave.getPassword())));
        Mockito.verify(userDao).save(ArgumentMatchers.argThat(userProfileForSave -> userProfile.getFullName().equals(userProfileForSave.getFullName()) && Role.ROLE_USER.equals(userProfileForSave.getRole()) &&
                Objects.equals(0D, userProfileForSave.getAvgRating())));
    }


    @Test
    void testEditPassword_Successful() {

        UserCredentialsDto userCredentialsDto = new UserCredentialsDto();
        userCredentialsDto.setId(1L);
        userCredentialsDto.setPassword("testEditPasswordDecoded");
        Mockito.when(modelMapper.map(userAuthenticationService.getCurrentUserProfile(), UserProfile.class)).thenReturn(userProfile);
        Mockito.when(passwordEncoder.encode(Mockito.any())).thenReturn("testEditPasswordEncoded");
        userService.editPassword(userCredentialsDto);
        Mockito.verify(userLoginDao).update(ArgumentMatchers.argThat(userLoginForUpdate -> "testEditPasswordEncoded".equals(userLoginForUpdate.getPassword())));
    }

    @Test
    void testEditProfile_Successful() {

        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setId(1L);
        userProfileDto.setFullName("testEditUsername");
        Mockito.when(userDao.get(userProfileDto.getId())).thenReturn(userProfile);
        Mockito.when(modelMapper.map(userAuthenticationService.getCurrentUserProfile(), UserProfile.class)).thenReturn(userProfile);
        userService.editProfile(userProfileDto);
        Mockito.verify(userDao).update(ArgumentMatchers.argThat(userLoginForUpdate -> "testEditUsername".equals(userLoginForUpdate.getFullName())));

    }

}