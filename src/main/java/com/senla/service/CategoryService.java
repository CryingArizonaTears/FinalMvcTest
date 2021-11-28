package com.senla.service;

import com.senla.api.dao.ICategoryDao;
import com.senla.api.service.ICategoryService;
import com.senla.model.Category;
import com.senla.model.Role;
import com.senla.model.UserProfile;
import com.senla.model.dto.CategoryDto;
import com.senla.model.dto.filter.CategoryFilter;
import com.senla.modelMapperMethods.ModelMapperMapList;
import com.senla.security.AuthenticationGetUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private ICategoryDao categoryDao;
    @Autowired
    private ModelMapperMapList modelMapper;

    @Override
    public void createCategory(CategoryDto categoryDto) {
            Category category = modelMapper.map(categoryDto, Category.class);
            categoryDao.save(category);
    }

    @Override
    public List<CategoryDto> getByFilter(CategoryFilter categoryFilter) {
        return modelMapper.mapList(categoryDao.getByFilter(categoryFilter), CategoryDto.class);
    }
}
