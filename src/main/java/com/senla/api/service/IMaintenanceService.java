package com.senla.api.service;

import com.senla.model.dto.MaintenanceDto;
import com.senla.model.dto.filter.MaintenanceFilter;

public interface IMaintenanceService {

    void createMaintenance(MaintenanceDto maintenanceDto) ;

    void getByFilter(MaintenanceFilter maintenanceFilter);

    void addMaintenanceToAd(Long adId, Long maintenanceId);
}
