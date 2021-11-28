package com.senla.controllers;

import com.senla.api.service.IAdService;
import com.senla.api.service.ICategoryService;
import com.senla.api.service.IMaintenanceService;
import com.senla.model.dto.AdDto;
import com.senla.model.dto.CategoryDto;
import com.senla.model.dto.MaintenanceDto;
import com.senla.model.dto.filter.AdFilter;
import com.senla.model.dto.filter.CategoryFilter;
import com.senla.model.dto.filter.MaintenanceFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/get")
public class GetController {

    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IAdService adService;
    @Autowired
    private IMaintenanceService maintenanceService;

    @GetMapping("/ads")
    public ResponseEntity<List<AdDto>> getAdsByFilter(AdFilter filter) {
        return ResponseEntity.ok(adService.getByFilter(filter));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getCategoriesByFilter(CategoryFilter categoryFilter) {
        return ResponseEntity.ok(categoryService.getByFilter(categoryFilter));
    }


    @GetMapping("/maintenances")
    public ResponseEntity<List<MaintenanceDto>> getMaintenancesByFilter(MaintenanceFilter maintenanceFilter) {
        return ResponseEntity.ok(maintenanceService.getByFilter(maintenanceFilter));
    }

}
