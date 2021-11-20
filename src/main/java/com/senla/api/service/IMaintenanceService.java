package com.senla.api.service;

import com.senla.model.dto.MaintenanceDto;
import com.senla.model.dto.filter.MaintenanceFilter;

import java.util.List;

public interface IMaintenanceService {

    void createMaintenance(MaintenanceDto maintenanceDto) ;

    List<MaintenanceDto> getByFilter(MaintenanceFilter maintenanceFilter);

    void addMaintenanceToAd(Long adId, Long maintenanceId);
}
