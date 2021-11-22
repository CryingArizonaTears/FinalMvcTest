package com.senla.api.dao;

import com.senla.model.AbstractModel;

import java.util.List;

public interface IAbstractDao<T extends AbstractModel> {

    List<T> getAll();

    T get(Long id);

    T update(T chat);

    void save(T chat);

    void delete(Long id);
}
