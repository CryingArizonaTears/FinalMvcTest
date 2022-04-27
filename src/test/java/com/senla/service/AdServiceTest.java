package com.senla.service;

import com.senla.api.dao.IAdDao;
import com.senla.api.dao.IUserLoginDao;
import com.senla.api.dao.IUserProfileDao;
import com.senla.api.service.IUserAuthenticationService;
import com.senla.model.*;
import com.senla.model.dto.AdDto;
import com.senla.model.dto.CategoryDto;
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

import java.time.LocalDate;
import java.util.Objects;

@ExtendWith(MockitoExtension.class)
class AdServiceTest {

    @InjectMocks
    private UserService userService;
    @InjectMocks
    private AdService adService;
    @Mock
    private IAdDao adDao;
    @Mock
    private ExtendedModelMapper modelMapper;
    @Mock
    private IUserAuthenticationService userAuthenticationService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private IUserLoginDao userLoginDao;
    @Mock
    private IUserProfileDao userProfileDao;
    private final ExtendedModelMapper realModelMapper = new ExtendedModelMapper();

    private final UserProfile userProfile = new UserProfile();
    private final AdDto adDto = new AdDto();
    @Mock
    private Category category;

    @BeforeEach
    public void beforeTests() {
        userProfile.setId(1L);
        userProfile.setRole(Role.ROLE_USER);

        adDto.setName("testName");
        adDto.setDescription("testDescription");
        adDto.setPrice(1D);
        adDto.setCategory(realModelMapper.map(category, CategoryDto.class));
        adDto.setAdStatus(AdStatus.OPEN);
        adDto.setUserProfile(realModelMapper.map(userProfile, UserProfileDto.class));
        adDto.setCreationDate(LocalDate.now());

        Mockito.when(modelMapper.map(userAuthenticationService.getCurrentUserProfile(), UserProfile.class)).thenReturn(userProfile);
        Mockito.when(modelMapper.map(adDto.getCategory(), Category.class)).thenReturn(category);
        adService.createAd(adDto);
    }

    @Test
    void testCreateAd_Successful() {

        Mockito.verify(adDao).save(ArgumentMatchers.argThat(adForSave ->
                adDto.getName().equals(adForSave.getName()) &&
                        adDto.getDescription().equals(adForSave.getDescription()) &&
                        adDto.getPrice().equals(adForSave.getPrice()) &&
                        Objects.equals(category, adForSave.getCategory()) &&
                        Objects.equals(userProfile, adForSave.getUserProfile())));
    }

    @Test
    void testEditAd_Successful() {
        adDto.setName("testEditName");
        adDto.setPrice(2D);
        adDto.setDescription("testEditDescription");
        Mockito.when(userAuthenticationService.getCurrentUserProfile()).thenReturn(realModelMapper.map(userProfile, UserProfileDto.class));
        Mockito.when(adDao.get(adDto.getId())).thenReturn(realModelMapper.map(adDto, Ad.class));
        adService.editAd(adDto);
        Mockito.verify(adDao).update(ArgumentMatchers.argThat(adForEdit ->
                adForEdit.getName().equals("testEditName") &&
                        adForEdit.getDescription().equals("testEditDescription") &&
                        adForEdit.getPrice() == 2D));
    }

    @Test
    void testDeleteAd_Successful() {
        Mockito.when(adDao.get(1L)).thenReturn(realModelMapper.map(adDto, Ad.class));
        adService.deleteAd(1L);
        Mockito.verify(adDao).update(ArgumentMatchers.argThat(adForDelete ->
                adForDelete.getStatus().equals(AdStatus.CLOSED)));
    }
}