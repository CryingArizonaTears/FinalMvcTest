package com.senla.dao;

import com.senla.api.dao.ICategoryDao;
import com.senla.model.Category;
import com.senla.model.dto.filter.CategoryFilter;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
    public List<Category> getByFilter(CategoryFilter categoryFilter) {
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Category> query = builder.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);
        query.where(categoryPredicate(categoryFilter, builder, root));
        CriteriaQuery<Category> all = query.select(root);
        return getCurrentSession().createQuery(all).getResultList();
    }

    private Predicate[] categoryPredicate(CategoryFilter categoryFilter, CriteriaBuilder builder, Root<Category> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!ObjectUtils.isEmpty(categoryFilter.getName())) {
            predicates.add(builder.like(root.get("name"), "%" + categoryFilter.getName() + "%"));
        }
        return predicates.toArray(new Predicate[]{});
    }
}
