package com.senla.dao;

import com.senla.api.dao.ICategoryDao;
import com.senla.model.Category;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDao extends AbstractDao<Category> implements ICategoryDao {

    @Override
    protected Class<Category> getClazz() {
        return Category.class;
    }
}
