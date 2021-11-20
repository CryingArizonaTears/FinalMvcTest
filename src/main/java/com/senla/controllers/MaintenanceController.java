package com.senla.controllers;

import com.senla.model.dto.AdDto;
import com.senla.model.dto.MaintenanceDto;
import com.senla.model.dto.filter.MaintenanceFilter;
import com.senla.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/maintenance")
public class MaintenanceController {

    @Autowired
    MaintenanceService maintenanceService;

    @PostMapping
    public ResponseEntity<Void> createMaintenance(@RequestBody MaintenanceDto maintenanceDto) {
        maintenanceService.createMaintenance(maintenanceDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceDto>> getMaintenancesByFilter (MaintenanceFilter maintenanceFilter){
        return ResponseEntity.ok();
    }
}
