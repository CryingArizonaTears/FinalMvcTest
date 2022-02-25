package com.senla.service;

import com.senla.annotation.Logging;
import com.senla.api.dao.IAdDao;
import com.senla.api.service.IAdService;
import com.senla.api.service.IUserAuthenticationService;
import com.senla.model.*;
import com.senla.model.dto.AdDto;
import com.senla.model.dto.UserProfileDto;
import com.senla.model.dto.filter.AdFilter;
import com.senla.modelMapperMethods.ExtendedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private ExtendedModelMapper modelMapper;
    @Autowired
    private IUserAuthenticationService userAuthenticationService;

    @Override
    @Logging
    public List<AdDto> getByFilter(AdFilter adFilter) {
        if (!checkAnonymousUser()) {
            UserProfile user = modelMapper.map(userAuthenticationService.getCurrentUserProfile(), UserProfile.class);
            if (user.getRole().equals(Role.ROLE_ADMIN)) {
                return modelMapper.mapList(adDao.getByFilter(adFilter), AdDto.class);
            }
        }
        adFilter.setStatus(AdStatus.OPEN);
        return modelMapper.mapList(adDao.getByFilter(adFilter), AdDto.class);
    }

    @Override
    @Logging
    public void createAd(AdDto adDto) {
        UserProfile currentUser = modelMapper.map(userAuthenticationService.getCurrentUserProfile(), UserProfile.class);
        Ad ad = new Ad();
        ad.setStatus(AdStatus.OPEN);
        ad.setCreationDate(LocalDate.now());
        if (currentUser.getRole().equals(Role.ROLE_USER)) {
            ad.setId(adDto.getId());
            ad.setName(adDto.getName());
            ad.setDescription(adDto.getDescription());
            ad.setUserProfile(currentUser);
        } else if (currentUser.getRole().equals(Role.ROLE_ADMIN)) {
            checkAdParams(adDto, ad);
        }
        adDao.save(ad);
    }

    @Override
    @Logging
    public void editAd(AdDto adDto) {
        UserProfileDto currentUser = userAuthenticationService.getCurrentUserProfile();
        Ad ad = adDao.get(adDto.getId());
        if (adDto.getName() != null) {
            ad.setName(adDto.getName());
        }
        if (adDto.getDescription() != null) {
            ad.setDescription(adDto.getDescription());
        }
        if (adDto.getPrice() != null) {
            ad.setPrice(adDto.getPrice());
        }
        if (currentUser.getRole().equals(Role.ROLE_USER)) {
            if (currentUser.getId().equals(ad.getUserProfile().getUserLogin().getId())) {
                adDao.update(ad);
            } else {
                throw new SecurityException("Вы не можете редактировать чужие объявления");
            }
        } else if (currentUser.getRole().equals(Role.ROLE_ADMIN)) {
            checkAdParams(adDto, ad);
            adDao.update(ad);
        }
    }

    @Override
    @Logging
    public void deleteAd(Long id) {
        UserProfile currentUser = modelMapper.map(userAuthenticationService.getCurrentUserProfile(), UserProfile.class);
        Ad ad = adDao.get(id);
        if (currentUser.getRole().equals(Role.ROLE_USER)) {
            if (ad.getUserProfile().equals(currentUser)) {
                ad.setStatus(AdStatus.CLOSED);
                adDao.update(ad);
            } else {
                throw new SecurityException("Вы не можете редактировать чужие объявления");
            }
        } else if (currentUser.getRole().equals(Role.ROLE_ADMIN)) {
            ad.setStatus(AdStatus.CLOSED);
            adDao.update(ad);
        }
    }

    private void checkAdParams(AdDto adDto, Ad ad) {
        if (adDto.getAdStatus() != null) {
            ad.setStatus(adDto.getAdStatus());
        }
        if (adDto.getPremiumUntilDate() != null) {
            ad.setPremiumUntilDate(adDto.getPremiumUntilDate());
        }
        if (adDto.getCategory() != null) {
            ad.setCategory(modelMapper.map(adDto.getCategory(), Category.class));
        }
        if (adDto.getMaintenances() != null) {
            ad.setMaintenances(modelMapper.mapList(adDto.getMaintenances(), Maintenance.class));
        }
        if (adDto.getComments() != null) {
            ad.setComments(modelMapper.mapList(adDto.getComments(), Comment.class));
        }
        if (adDto.getCreationDate() != null) {
            ad.setCreationDate(adDto.getCreationDate());
        }
        if (adDto.getUserProfile() != null) {
            ad.setUserProfile(modelMapper.map(adDto.getUserProfile(), UserProfile.class));
        }
    }

    @Logging
    private boolean checkAnonymousUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser");
    }

}
