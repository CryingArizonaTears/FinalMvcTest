package com.senla.controllers;

import com.senla.model.dto.CategoryDto;
import com.senla.model.dto.filter.CategoryFilter;
import com.senla.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

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
