package com.senla.dao;

import com.senla.api.dao.IAbstractFilterDao;
import com.senla.model.AbstractModel;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class AbstractFilterDao<T extends AbstractModel, S> extends AbstractDao<T> implements IAbstractFilterDao<T, S> {
    @Autowired
    protected SessionFactory sessionFactory;

    public List<T> getByFilter(S filter) {
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getClazz());
        Root<T> root = query.from(getClazz());
        query.where(getPredicates(filter, builder, root));
        CriteriaQuery<T> all = query.select(root);
        return getCurrentSession().createQuery(all).getResultList();
    }

    protected abstract Predicate[] getPredicates(S filter, CriteriaBuilder criteriaBuilder, Root<T> root);
}
