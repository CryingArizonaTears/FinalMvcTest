package com.senla.controllers;

import com.senla.api.service.ICategoryService;
import com.senla.model.dto.CategoryDto;
import com.senla.model.dto.filter.CategoryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CatregoryController {


    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategoriesByFilter(CategoryFilter categoryFilter) {
        return ResponseEntity.ok(categoryService.getByFilter(categoryFilter));
    }
}
