package com.senla.service;

import com.senla.api.dao.IAdDao;
import com.senla.api.service.IAdService;
import com.senla.model.Ad;
import com.senla.model.AdStatus;
import com.senla.model.dto.AdDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class AdService implements IAdService {
    @Autowired
    private IAdDao adDao;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List getCurrentAds() {
        List<Ad> ads = adDao.getCurrentAds();
        List<AdDto> adsDto = ads
                .stream()
                .map(ad -> modelMapper.map(ad, AdDto.class))
                .collect(Collectors.toList());
        return adsDto;
    }

    @Override
    public List getByName(String name) {
        return adDao.getByName(name);
    }

    @Override
    public AdDto getById(Long id) {
        return modelMapper.map(adDao.get(id), AdDto.class);
    }

    @Override
    public List filterByCategory(Long id) {
        List<Ad> ads = adDao.filterByCategory(id);
        List<AdDto> adsDto = ads
                .stream()
                .map(ad -> modelMapper.map(ad, AdDto.class))
                .collect(Collectors.toList());
        return adsDto;
    }

    @Override
    public List filterByUserId(Long id) {
        List<Ad> ads = adDao.filterByUserId(id);
        List<AdDto> adsDto = ads
                .stream()
                .map(ad -> modelMapper.map(ad, AdDto.class))
                .collect(Collectors.toList());
        return adsDto;
    }

    @Override
    public List filterByPrice(Double from, Double to) {
        List<Ad> ads = adDao.filterByPrice(from, to);
        List<AdDto> adsDto = ads
                .stream()
                .map(ad -> modelMapper.map(ad, AdDto.class))
                .collect(Collectors.toList());
        return adsDto;
    }

    public List filterClosedByUserId(Long id) {
        List<Ad> ads = adDao.filterClosedAdsByUserId(id);
        List<AdDto> adsDto = ads
                .stream()
                .map(ad -> modelMapper.map(ad, AdDto.class))
                .collect(Collectors.toList());
        return adsDto;
    }


    @Override
    public void createAd(AdDto adDto) {
        Ad ad = modelMapper.map(adDto, Ad.class);
        ad.setStatus(AdStatus.OPEN);
        ad.setCreationDate(LocalDate.now());
        ad.setComments(new ArrayList<>());
        ad.setMaintenances(new ArrayList<>());
        adDao.save(ad);
    }


    @Override
    public void editAd(Long id, AdDto adDto) {
        Ad ad = adDao.get(id);
        Ad dtoAd = modelMapper.map(adDto, Ad.class);
        ad.setName(dtoAd.getName());
        if (dtoAd.getCategory() != null){
            ad.setCategory(dtoAd.getCategory());
        }
        ad.setDescription(dtoAd.getDescription());
        ad.setPrice(dtoAd.getPrice());
        adDao.update(ad);
    }


    @Override
    public void deleteAd(Long id) {
        Ad ad = adDao.get(id);
        ad.setStatus(AdStatus.CLOSED);
        adDao.update(ad);
    }

}
