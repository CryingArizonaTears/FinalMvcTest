package com.senla.dao;

import com.senla.api.dao.ICategoryDao;
import com.senla.model.Category;
import com.senla.model.dto.filter.CategoryFilter;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDao extends AbstractDao<Category> implements ICategoryDao {

    @Override
    protected Class<Category> getClazz() {
        return Category.class;
    }

    @Override
    protected Predicate[] getPredicates(Object object, CriteriaBuilder criteriaBuilder, Root root) {
        return categoryPredicate((CategoryFilter) object, criteriaBuilder, root);
    }

    private Predicate[] categoryPredicate(CategoryFilter categoryFilter, CriteriaBuilder builder, Root<Category> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!ObjectUtils.isEmpty(categoryFilter.getName())) {
            predicates.add(builder.like(root.get("name"), "%" + categoryFilter.getName() + "%"));
        }
        return predicates.toArray(new Predicate[]{});
    }
}
