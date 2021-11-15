package com.senla.service;

import com.senla.api.dao.IAdDao;
import com.senla.api.service.IAdService;
import com.senla.model.Ad;
import com.senla.model.AdStatus;
import com.senla.model.dto.AdDto;
import com.senla.modelMapperMethods.ModelMapperMapList;
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
    @Autowired
    private ModelMapperMapList modelMapperMapList;

    public List<AdDto> getAll(String sort, Long id, Double from, Double to) {
        return modelMapperMapList.mapList(adDao.getAll(sort, id, from, to), AdDto.class);
    }

    @Override
    public List<AdDto> getByName(String name) {
        return modelMapperMapList.mapList(adDao.getByName(name), AdDto.class);
    }

    @Override
    public AdDto getById(Long id) {
        return modelMapper.map(adDao.get(id), AdDto.class);
    }

    public List<AdDto> filterClosedByUserId(Long id) {
        return modelMapperMapList.mapList(adDao.filterClosedAdsByUserId(id), AdDto.class);
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
    public void editAd(AdDto adDto) {
        Ad dtoAd = modelMapper.map(adDto, Ad.class);
        Ad ad = adDao.get(adDto.getId());
        ad.setName(dtoAd.getName());
        if (dtoAd.getCategory() != null) {
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
