package com.senla.service;

import com.senla.api.dao.IAdDao;
import com.senla.api.service.IAdService;
import com.senla.api.service.IUserService;
import com.senla.model.*;
import com.senla.model.dto.AdDto;
import com.senla.model.dto.UserProfileDto;
import com.senla.model.dto.filter.AdFilter;
import com.senla.modelMapperMethods.ExtendedModelMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Log4j
@Transactional
@Service
public class AdService implements IAdService {
    @Autowired
    private IAdDao adDao;
    @Autowired
    private ExtendedModelMapper modelMapper;
    @Autowired
    private IUserService userService;

    @Override
    public List<AdDto> getByFilter(AdFilter adFilter) {
        log.debug("Method: getByFilter, входящий: " + adFilter.toString());
        if (!checkAnonymousUser()) {
            UserProfile user = modelMapper.map(userService.getCurrentUserProfile(), UserProfile.class);
            if (user.getRole().equals(Role.ROLE_ADMIN)) {
                List<AdDto> adDtos = modelMapper.mapList(adDao.getByFilter(adFilter), AdDto.class);
                log.debug("Method: getByFilter, выходящий: " + adDtos.toString());
                return adDtos;
            }
        }
        adFilter.setStatus(AdStatus.OPEN);
        List<AdDto> adDtos = modelMapper.mapList(adDao.getByFilter(adFilter), AdDto.class);
        log.debug("Method: getByFilter, выходящий: " + adDtos.toString());
        return adDtos;
    }

    @Override
    public void createAd(AdDto adDto) {
        log.debug("Method: createAd, входящий: " + adDto.toString());
        UserProfile currentUser = modelMapper.map(userService.getCurrentUserProfile(), UserProfile.class);
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
        log.debug("Method: createAd, выходящий: " + ad.toString());
        adDao.save(ad);
    }

    @Override
    public void editAd(AdDto adDto) {
        log.debug("Method: editAd, входящий: " + adDto.toString());
        UserProfileDto currentUser = userService.getCurrentUserProfile();
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
                log.debug("Method: editAd, выходящий: " + ad.toString());
                adDao.update(ad);
            } else {
                throw new SecurityException("Вы не можете редактировать чужие объявления");
            }
        } else if (currentUser.getRole().equals(Role.ROLE_ADMIN)) {
            checkAdParams(adDto, ad);
            log.debug("Method: editAd, выходящий: " + ad.toString());
            adDao.update(ad);
        }
    }

    @Override
    public void deleteAd(Long id) {
        log.debug("Method: deleteAd, входящий: " + id);
        UserProfile currentUser = modelMapper.map(userService.getCurrentUserProfile(), UserProfile.class);
        Ad ad = adDao.get(id);
        if (currentUser.getRole().equals(Role.ROLE_USER)) {
            if (ad.getUserProfile().equals(currentUser)) {
                ad.setStatus(AdStatus.CLOSED);
                log.debug("Method: deleteAd, выходящий: " + ad.toString());
                adDao.update(ad);
            } else {
                throw new SecurityException("Вы не можете редактировать чужие объявления");
            }
        } else if (currentUser.getRole().equals(Role.ROLE_ADMIN)) {
            ad.setStatus(AdStatus.CLOSED);
            log.debug("Method: deleteAd, выходящий: " + ad.toString());
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

    private boolean checkAnonymousUser() {
        boolean bool = SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser");
        log.debug("Method: checkAnonymousUser, выходящий: " + bool);
        return bool;
    }

}
