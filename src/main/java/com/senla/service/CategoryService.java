package com.senla.service;

import com.senla.api.dao.ICategoryDao;
import com.senla.api.service.ICategoryService;
import com.senla.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private ICategoryDao categoryDao;

    @Override
    public void createCategory(String name) {
        Category category = new Category();
        category.setName(name);
        categoryDao.save(category);
    }
}
