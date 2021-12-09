package com.senla.controllers;

import com.senla.api.service.ICategoryService;
import com.senla.api.service.IMaintenanceService;
import com.senla.model.dto.CategoryDto;
import com.senla.model.dto.MaintenanceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin")
public class AdminControllers {

    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IMaintenanceService maintenanceService;

    @PostMapping("/category")
    public ResponseEntity<Void> createCategory(@RequestBody CategoryDto categoryDto) {
        categoryService.createCategory(categoryDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/maintenance")
    public ResponseEntity<Void> createMaintenance(@RequestBody MaintenanceDto maintenanceDto) {
        maintenanceService.createMaintenance(maintenanceDto);
        return ResponseEntity.noContent().build();
    }

}
