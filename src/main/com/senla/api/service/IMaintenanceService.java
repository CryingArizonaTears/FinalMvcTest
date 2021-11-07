package com.senla.api.service;

public interface IMaintenanceService {

    void createMaintenance(String name, String description, Double price, Integer plusDays);

    void addMaintenanceToAd(Long adId, Long maintenanceId);
}
