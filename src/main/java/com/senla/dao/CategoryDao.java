package com.senla.dao;

import com.senla.api.dao.ICategoryDao;
import com.senla.model.Category;
import com.senla.model.Category_;
import com.senla.model.dto.filter.CategoryFilter;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDao extends AbstractFilterDao<Category, CategoryFilter> implements ICategoryDao {

    @Override
    protected Class<Category> getClazz() {
        return Category.class;
    }

    @Override
    protected Predicate[] getPredicates(CategoryFilter categoryFilter, CriteriaBuilder builder, Root<Category> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!ObjectUtils.isEmpty(categoryFilter.getName())) {
            predicates.add(builder.like(root.get(Category_.NAME), "%" + categoryFilter.getName() + "%"));
        }
        return predicates.toArray(new Predicate[]{});
    }
}
