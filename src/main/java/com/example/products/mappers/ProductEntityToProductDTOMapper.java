package com.example.products.mappers;

import com.example.products.entity.Category;
import com.example.products.entity.CategoryDTO;
import com.example.products.entity.ProductDTO;
import com.example.products.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public abstract class ProductEntityToProductDTOMapper {

    public ProductDTO mapToProductDTO(ProductEntity productEntity){
        return toDTO(productEntity);
    }

    @Mappings({
            @Mapping(expression = "java(toCategoryDTO(productEntity.getCategory()))",target = "categoryDTO")
    })
    protected abstract ProductDTO toDTO(ProductEntity productEntity);

    @Mappings({})
    protected abstract CategoryDTO toCategoryDTO(Category category);
}

