package com.senla.dao;

import com.senla.model.AbstractModel;
import com.senla.model.Maintenance;
import com.senla.model.dto.filter.MaintenanceFilter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    public List<T> getByFilter(Entity entity) {
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getClazz());
        Root<T> root = query.from(getClazz());
        try {
            getMethod().setAccessible(true);
            Object qwe = getMethod().invoke(entity, builder, root);
            query.where((Predicate) qwe);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
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

    protected abstract Method getMethod();

    protected abstract Class<T> getClazz();

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
