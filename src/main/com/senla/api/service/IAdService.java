package com.senla.api.service;

import com.senla.model.Ad;

import java.util.List;

public interface IAdService {

    List<Ad> getCurrentAds();

    List<Ad> getByName(String name);

    Ad getById(Long id);

    List<Ad> filterByCategory(Long id);

    List<Ad> filterByUserId(Long id);

    List<Ad> filterByPrice(Double from, Double to);

    List filterClosedByUserId(Long id);

    void createAd(String name, Long categoryId, String description, Double price, Long userId);

    void editAd(Long adId, String name, Long categoryId, String description, Double price);

    void deleteAd(Long id);
}
