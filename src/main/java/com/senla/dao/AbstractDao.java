package com.senla.dao;

import com.senla.annotation.Logging;
import com.senla.api.dao.IAbstractDao;
import com.senla.model.AbstractModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class AbstractDao<T extends AbstractModel> implements IAbstractDao<T> {
    @Autowired
    protected SessionFactory sessionFactory;

    @Transactional
    @Logging
    public List<T> getAll() {
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getClazz());
        Root<T> root = query.from(getClazz());
        CriteriaQuery<T> all = query.select(root);
        return getCurrentSession().createQuery(all).getResultList();
    }

    @Transactional
    @Logging
    public T get(Long id) {
        return getCurrentSession().find(getClazz(), id);
    }

    @Logging
    public T update(T entity) {
        getCurrentSession().merge(entity);
        return entity;
    }

    @Logging
    public void save(T entity) {
        getCurrentSession().persist(entity);
    }

    @Logging
    public void delete(Long id) {
        T bufEntity = getCurrentSession().find(getClazz(), id);
        getCurrentSession().remove(bufEntity);
    }

    protected abstract Class<T> getClazz();

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
