package com.senla.api.dao;

import com.senla.model.Category;

import java.util.List;

public interface ICategoryDao {

    List<Category> getAll();

    Category get(Long id);

    Category update(Category category);

    void save(Category category);

    void delete(Long id);
}