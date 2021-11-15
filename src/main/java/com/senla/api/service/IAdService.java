package com.senla.api.service;

import com.senla.model.dto.AdDto;

import java.util.List;

public interface IAdService {

    List<AdDto> getByName(String name);

    AdDto getById(Long id);

    List<AdDto> getAll(String sort, Long id, Double from, Double to);

    List<AdDto> filterClosedByUserId(Long id);

    void createAd(AdDto adDto);

    void editAd(AdDto adDto);

    void deleteAd(Long id);
}
