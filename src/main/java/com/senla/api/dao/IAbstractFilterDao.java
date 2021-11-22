package com.senla.api.dao;

import com.senla.model.AbstractModel;

import java.util.List;

public interface IAbstractFilterDao<T extends AbstractModel, S> extends IAbstractDao<T> {

    List<T> getByFilter(S filter);
}
