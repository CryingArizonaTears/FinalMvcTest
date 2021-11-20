package com.senla.api.dao;

import com.senla.model.Rating;

import java.util.List;

public interface IRatingDao {

    List<Rating> getAll();

    List<Rating> getByFilter(Object entity);

    Rating get(Long id);

    Rating update(Rating rating);

    void save(Rating rating);

    void delete(Long id);
}
