package com.example.products.mediator;

import com.example.products.entity.CategoryDTO;
import com.example.products.mappers.CategoryToCategoryDTOMapper;
import com.example.products.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryMediator {
    private final CategoryService categoryService;
    private final CategoryToCategoryDTOMapper categoryToCategoryDTOMapper;

    public ResponseEntity<List<CategoryDTO>> getCategories() {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        categoryService.getCategories().forEach(value -> {
            categoryDTOS.add(categoryToCategoryDTOMapper.mapToCategoryDTO(value));
        });
        return ResponseEntity.ok(categoryDTOS);
    }
}
