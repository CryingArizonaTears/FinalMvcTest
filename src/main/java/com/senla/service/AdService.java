package com.senla.service;

import com.senla.api.dao.IAdDao;
import com.senla.api.dao.IUserProfileDao;
import com.senla.api.service.IAdService;
import com.senla.model.Ad;
import com.senla.model.AdStatus;
import com.senla.model.Role;
import com.senla.model.UserProfile;
import com.senla.model.dto.AdDto;
import com.senla.model.dto.filter.AdFilter;
import com.senla.model.dto.filter.UserFilter;
import com.senla.modelMapperMethods.ModelMapperMapList;
import com.senla.security.AuthenticationGetUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
public class AdService implements IAdService {
    @Autowired
    private IAdDao adDao;
    @Autowired
    private ModelMapperMapList modelMapper;
    @Autowired
    private AuthenticationGetUser authenticationGetUser;
    @Autowired
    private IUserProfileDao userProfileDao;

    @Override
    public List<AdDto> getByFilter(AdFilter adFilter) {
        UserProfile user = authenticationGetUser.getUserProfileByAuthentication();
        if (user.getRole().equals(Role.ROLE_USER)) {
            adFilter.setStatus(AdStatus.OPEN);
        }
        return modelMapper.mapList(adDao.getByFilter(adFilter), AdDto.class);
    }


    @Override
    public void createAd(AdDto adDto) {
        UserProfile creator = authenticationGetUser.getUserProfileByAuthentication();
        Ad ad = modelMapper.map(adDto, Ad.class);
        ad.setStatus(AdStatus.OPEN);
        ad.setCreationDate(LocalDate.now());
        if (creator.getRole().equals(Role.ROLE_USER)) {
            ad.setUserProfile(creator);
        }
        if (creator.getRole().equals(Role.ROLE_ADMIN)) {
            if (adDto.getCreationDate() != null) {
                ad.setCreationDate(adDto.getCreationDate());
            }
        }
        adDao.save(ad);
    }

    @Override
    public void editAd(AdDto adDto) {
        UserProfile userProfile = authenticationGetUser.getUserProfileByAuthentication();
        Ad dtoAd = modelMapper.map(adDto, Ad.class);
        Ad ad = adDao.get(adDto.getId());
        ad.setName(dtoAd.getName());
        ad.setDescription(dtoAd.getDescription());
        ad.setPrice(dtoAd.getPrice());
        if (userProfile.getRole().equals(Role.ROLE_USER)) {
            ad.setUserProfile(userProfile);
            if (userProfile.getAds().stream().anyMatch(ad1 -> ad1.getId().equals(adDto.getId()))) {
                adDao.update(ad);
            } else {
                throw new SecurityException("Вы не можете редактировать чужие штуки");
            }
        }
        if (userProfile.getRole().equals(Role.ROLE_ADMIN)) {
            if (dtoAd.getUserProfile() != null) {
                UserFilter userFilter = new UserFilter();
                userFilter.setId(dtoAd.getUserProfile().getId());
                UserProfile userProfileByFilter = userProfileDao.getByFilter(userFilter).stream().findFirst().orElse(null);
                ad.setUserProfile(userProfileByFilter);
            }
            if (dtoAd.getCategory() != null) {
                ad.setCategory(dtoAd.getCategory());
            }
            adDao.update(ad);
        }
    }

    @Override
    public void deleteAd(Long id) {
        UserProfile userProfile = authenticationGetUser.getUserProfileByAuthentication();
        Ad ad = adDao.get(id);
        if (userProfile.getRole().equals(Role.ROLE_USER)) {
            if (ad.getUserProfile().equals(userProfile)) {
                ad.setStatus(AdStatus.CLOSED);
                adDao.update(ad);
            } else {
                throw new SecurityException("Вы не можете редактировать чужие штуки");
            }
        }
        if (userProfile.getRole().equals(Role.ROLE_ADMIN)) {
            ad.setStatus(AdStatus.CLOSED);
            adDao.update(ad);
        }
    }

}
