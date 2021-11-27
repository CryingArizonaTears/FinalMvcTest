package com.senla.controllers;

import com.senla.api.service.IMaintenanceService;
import com.senla.model.dto.MaintenanceDto;
import com.senla.model.dto.filter.MaintenanceFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/maintenances")
public class MaintenanceController {

    @Autowired
    private IMaintenanceService maintenanceService;

    @GetMapping
    public ResponseEntity<List<MaintenanceDto>> getMaintenancesByFilter(MaintenanceFilter maintenanceFilter) {
        return ResponseEntity.ok(maintenanceService.getByFilter(maintenanceFilter));
    }
}
