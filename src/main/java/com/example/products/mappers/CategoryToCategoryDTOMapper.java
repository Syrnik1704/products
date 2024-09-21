package com.example.products.mappers;

import com.example.products.entity.Category;
import com.example.products.entity.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper
public abstract class CategoryToCategoryDTOMapper {

    public CategoryDTO mapToCategoryDTO(Category category){
        return  toCategoryDTO(category);
    }

    @Mappings({})
    protected abstract CategoryDTO toCategoryDTO(Category category);
}

