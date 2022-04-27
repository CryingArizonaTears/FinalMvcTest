package com.senla.service;

import com.senla.api.dao.ICategoryDao;
import com.senla.model.dto.CategoryDto;
import com.senla.modelMapperMethods.ExtendedModelMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;
    @Mock
    private ICategoryDao categoryDao;
    @Spy
    private ExtendedModelMapper modelMapper;


    @Test
    void testCreateCategory_Successful() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("testName");
        categoryService.createCategory(categoryDto);

        Mockito.verify(categoryDao).save(ArgumentMatchers.argThat(categoryForSave ->
                categoryForSave.getName().equals(categoryDto.getName())));
    }
}