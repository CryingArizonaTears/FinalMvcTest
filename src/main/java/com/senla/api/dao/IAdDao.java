package com.senla.api.dao;

import com.senla.model.Ad;

import java.util.List;

public interface IAdDao {

    List<Ad> getAll();

    Ad get(Long id);

    Ad update(Ad ad);

    void save(Ad ad);

    void delete(Long id);

    List<Ad> getAll(String sort, Long id, Double from, Double to);

    List<Ad> filterClosedAdsByUserId(Long id);

    List<Ad> getByName(String name);
}
