package com.example.products.mappers;

import com.example.products.entity.ProductEntity;
import com.example.products.entity.SimpleProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public abstract class ProductEntityToSimpleProductMapper {
    public SimpleProductDTO mapToSimpleProduct(ProductEntity productEntity) {
        return toSimpleProductDTO(productEntity);
    }

    @Mappings({
            @Mapping(target = "imageUrls", expression = "java(getImageUrl(productEntity.getImageUrls()))")
    })
    protected abstract SimpleProductDTO toSimpleProductDTO(ProductEntity productEntity);

    String getImageUrl(String[] images) {
        return images != null && images.length >= 1 ? images[0] : null;
    }
}
