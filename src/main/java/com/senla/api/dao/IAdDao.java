package com.senla.api.dao;

import com.senla.model.Ad;
import com.senla.model.dto.filter.AdFilter;

import java.util.List;

public interface IAdDao {

    List<Ad> getAll();

    Ad get(Long id);

    Ad update(Ad ad);

    void save(Ad ad);

    void delete(Long id);

    List<Ad> getByFilter(AdFilter adFilter);
}
