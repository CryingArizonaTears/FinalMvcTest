package com.senla.service;

import com.senla.api.dao.IAdDao;
import com.senla.api.service.IAdService;
import com.senla.model.Ad;
import com.senla.model.AdStatus;
import com.senla.model.Category;
import com.senla.model.UserProfile;
import com.senla.model.dto.AdDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class AdService implements IAdService {
    @Autowired
    private IAdDao adDao;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Ad> getCurrentAds() {
        return adDao.getCurrentAds();
    }

    @Override
    public List<Ad> getByName(String name) {
        return adDao.getByName(name);
    }

    @Override
    public AdDto getById(Long id) {
        Ad ad = adDao.get(id);
        return modelMapper.map(ad, AdDto.class);
    }

    @Override
    public List<Ad> filterByCategory(Long id) {
        return adDao.filterByCategory(id);
    }

    @Override
    public List<Ad> filterByUserId(Long id) {
        return adDao.filterByUserId(id);
    }

    @Override
    public List<Ad> filterByPrice(Double from, Double to) {
        return adDao.filterByPrice(from, to);
    }

    public List filterClosedByUserId(Long id) {
        return adDao.filterClosedAdsByUserId(id);
    }


    @Override
    public void createAd(String name, Long categoryId, String description, Double price, Long userId) {
        Ad ad = new Ad();
        ad.setName(name);
        Category category = new Category();
        category.setId(categoryId);
        ad.setCategory(category);
        ad.setDescription(description);
        ad.setPrice(price);
        ad.setStatus(AdStatus.OPEN);
        ad.setCreationDate(LocalDate.now());
        UserProfile userProfile = new UserProfile();
        userProfile.setId(userId);
        ad.setUserProfile(userProfile);
        ad.setComments(new ArrayList<>());
        adDao.save(ad);
    }


    @Override
    public void editAd(Long adId, String name, Long categoryId, String description, Double price) {
        Ad ad = adDao.get(adId);
        ad.setName(name);
        Category category = new Category();
        category.setId(categoryId);
        ad.setCategory(category);
        ad.setDescription(description);
        ad.setPrice(price);
        adDao.update(ad);
    }


    @Override
    public void deleteAd(Long id) {
        Ad ad = adDao.get(id);
        ad.setStatus(AdStatus.CLOSED);
        adDao.update(ad);
    }

}
