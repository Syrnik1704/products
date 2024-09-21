package com.example.products.mappers;

import com.example.products.entity.Category;
import com.example.products.entity.ProductEntity;
import com.example.products.entity.ProductFormDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public abstract class ProductFormToProductEntityMapper {

    public ProductEntity mapToProductEntity(ProductFormDTO productFormDTO) {
        return toProductEntity(productFormDTO);
    }

    @Mappings({
            @Mapping(expression = "java(map(productFormDTO.getCategory()))", target = "category"),
            @Mapping(target = "imageUrls", source = "imagesUid")
    })
    protected abstract ProductEntity toProductEntity(ProductFormDTO productFormDTO);

    protected Category map(String uuid) {
        Category category = new Category();
        category.setShortId(uuid);
        return category;
    }
}

