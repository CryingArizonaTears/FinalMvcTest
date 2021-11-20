package com.senla.api.service;

import com.senla.model.dto.CategoryDto;
import com.senla.model.dto.filter.CategoryFilter;

import java.util.List;

public interface ICategoryService {

    void createCategory(CategoryDto categoryDto);

    List<CategoryDto> getByFilter(CategoryFilter categoryFilter);
}
