package com.senla.service;

import com.senla.api.dao.ICategoryDao;
import com.senla.api.service.ICategoryService;
import com.senla.model.Category;
import com.senla.model.dto.CategoryDto;
import com.senla.model.dto.filter.CategoryFilter;
import com.senla.modelMapperMethods.ExtendedModelMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j
@Transactional
@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private ICategoryDao categoryDao;
    @Autowired
    private ExtendedModelMapper modelMapper;

    @Override
    public void createCategory(CategoryDto categoryDto) {
        log.debug("Method: createCategory, входящий: " + categoryDto.toString());
        Category category = modelMapper.map(categoryDto, Category.class);
        log.debug("Method: createCategory, выходящий: " + category.toString());
        categoryDao.save(category);
    }

    @Override
    public List<CategoryDto> getByFilter(CategoryFilter categoryFilter) {
        log.debug("Method: getByFilter, входящий: " + categoryFilter.toString());
        List<CategoryDto> categoryDtos = modelMapper.mapList(categoryDao.getByFilter(categoryFilter), CategoryDto.class);
        log.debug("Method: getByFilter, выходящий: " + categoryDtos.toString());
        return categoryDtos;
    }
}
