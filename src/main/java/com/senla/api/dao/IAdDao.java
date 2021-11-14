package com.senla.api.dao;

import com.senla.model.Ad;

import java.util.List;

public interface IAdDao {

    List<Ad> getAll();

    Ad get(Long id);

    Ad update(Ad ad);

    void save(Ad ad);

    void delete(Long id);

    List<Ad> getCurrentAds();

    List filterClosedAdsByUserId(Long id);

    List<Ad> getByName(String name);

    List<Ad> filterByCategory(Long id);

    List<Ad> filterByUserId(Long id);

    List<Ad> filterByPrice(Double from, Double to);
}
