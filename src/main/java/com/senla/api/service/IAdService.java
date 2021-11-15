package com.senla.api.service;

import com.senla.model.Ad;
import com.senla.model.dto.AdDto;

import java.util.List;

public interface IAdService {

    List getCurrentAds();

    List getByName(String name);

    AdDto getById(Long id);

    List filterByCategory(Long id);

    List filterByUserId(Long id);

    List filterByPrice(Double from, Double to);

    List filterClosedByUserId(Long id);

    void createAd(AdDto adDto);

    void editAd(Long id, AdDto adDto);

    void deleteAd(Long id);
}
