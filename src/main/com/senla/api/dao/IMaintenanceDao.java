package com.senla.api.dao;

import com.senla.model.Maintenance;

import java.util.List;

public interface IMaintenanceDao {

    List<Maintenance> getAll();

    Maintenance get(Long id);

    Maintenance update(Maintenance maintenance);

    void save(Maintenance maintenance);

    void delete(Long id);
}
