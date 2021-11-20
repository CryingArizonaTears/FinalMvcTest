package com.senla.dao;

import com.senla.model.AbstractModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class AbstractDao<T extends AbstractModel> {

    @Autowired
    protected SessionFactory sessionFactory;

    @Transactional
    public List<T> getAll() {
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getClazz());
        Root<T> root = query.from(getClazz());
        CriteriaQuery<T> all = query.select(root);
        return getCurrentSession().createQuery(all).getResultList();
    }

    public List<T> getByFilter(Object entity) {
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getClazz());
        Root<T> root = query.from(getClazz());
        query.where(getPredicates(entity, builder, root));
        CriteriaQuery<T> all = query.select(root);
        return getCurrentSession().createQuery(all).getResultList();
    }

    @Transactional
    public T get(Long id) {
        return getCurrentSession().find(getClazz(), id);
    }


    public T update(T entity) {
        getCurrentSession().merge(entity);
        return entity;
    }


    public void save(T entity) {
        getCurrentSession().persist(entity);
    }


    public void delete(Long id) {
        T bufEntity = getCurrentSession().find(getClazz(), id);
        getCurrentSession().remove(bufEntity);
    }

    protected abstract Predicate[] getPredicates(Object object, CriteriaBuilder criteriaBuilder, Root root);

    protected abstract Class<T> getClazz();

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
