package com.senla.controllers;

import com.senla.api.service.ICategoryService;
import com.senla.model.dto.CategoryDto;
import com.senla.model.dto.filter.CategoryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategoriesByFilter(CategoryFilter categoryFilter) {
        return ResponseEntity.ok(categoryService.getByFilter(categoryFilter));
    }

    @PostMapping()
    public ResponseEntity<Void> createCategory(@RequestBody CategoryDto categoryDto) {
        categoryService.createCategory(categoryDto);
        return ResponseEntity.noContent().build();
    }

}
