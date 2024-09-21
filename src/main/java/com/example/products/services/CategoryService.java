package com.example.products.services;

import com.example.products.entity.Category;
import com.example.products.entity.CategoryDTO;
import com.example.products.exceptions.ObjectExistInDBException;
import com.example.products.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public void createCategory(CategoryDTO categoryDTO) throws ObjectExistInDBException {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setShortId(UUID.randomUUID().toString().replace("-","").substring(0,12));

        categoryRepository.findByName(category.getName()).ifPresent(value->{
            throw new ObjectExistInDBException("Category exist in DB with this name");
        });
        categoryRepository.save(category);
    }

    public Optional<Category> findCategoryByShortId(String shortID){
        return categoryRepository.findByShortId(shortID);
    }

}
