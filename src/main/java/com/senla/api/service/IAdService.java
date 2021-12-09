package com.senla.api.service;

import com.senla.model.dto.AdDto;
import com.senla.model.dto.filter.AdFilter;

import java.util.List;

public interface IAdService {

    List<AdDto> getByFilter(AdFilter adFilter);

    void createAd(AdDto adDto);

    void editAd(AdDto adDto);

    void deleteAd(Long id);
}
