package com.example.products.mediator;

import com.example.products.entity.*;
import com.example.products.exceptions.CategoryDontExistException;
import com.example.products.mappers.ProductEntityToProductDTOMapper;
import com.example.products.mappers.ProductEntityToSimpleProductMapper;
import com.example.products.mappers.ProductFormToProductEntityMapper;
import com.example.products.services.CategoryService;
import com.example.products.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMediator {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductEntityToSimpleProductMapper productEntityToSimpleProductMapper;
    private final ProductEntityToProductDTOMapper productEntityToProductDTOMapper;
    private final ProductFormToProductEntityMapper productFormToProductEntityMapper;
    @Value("${file-service.url}")
    private String FILE_SERVICE;

    public ResponseEntity<?> getProducts(int page, int limit, String name, String category, Float price_min,
                                         Float price_max, String creation_date, String sort, String order) {
        if (name != null && !name.isEmpty()) {
            name = URLDecoder.decode(name, StandardCharsets.UTF_8);
        }

        List<ProductEntity> products = productService.getProducts(name, category, price_min, price_max,
                creation_date, page, limit, sort, order);

        products.forEach(value -> {
            for (int i = 0; i < value.getImageUrls().length; i++) {
                value.getImageUrls()[i] = FILE_SERVICE + "?uid=" + value.getImageUrls()[i];
            }
        });

        if (name == null || name.isEmpty() || creation_date == null || creation_date.isEmpty()){
            List<SimpleProductDTO> simpleProductDTOS = new ArrayList<>();
            long totalCount  = productService.countActiveProducts( name, category, price_min, price_max);
            products.forEach(value -> {
                simpleProductDTOS.add(productEntityToSimpleProductMapper.mapToSimpleProduct(value));
            });
            return ResponseEntity.ok().header("X-Total-Count", String.valueOf(totalCount)).body(simpleProductDTOS);
        }
        ProductDTO productDTO = productEntityToProductDTOMapper.mapToProductDTO(products.get(0));
        return ResponseEntity.ok().body(productDTO);
    }

    public ResponseEntity<Response> saveProduct(ProductFormDTO productFormDTO) {
        try {
            ProductEntity productEntity = productFormToProductEntityMapper.mapToProductEntity(productFormDTO);
            categoryService.findCategoryByShortId(productEntity.getCategory().getShortId()).ifPresentOrElse(productEntity::setCategory, () -> {
                throw new CategoryDontExistException();
            });
            productService.createProduct(productEntity);
            return ResponseEntity.ok(new Response("Product created successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new Response("Something went wrong. Product not created, this category don't exist"));
        }
    }

    public ResponseEntity<Response> deleteProduct(String uid) {
        try {
            productService.deleteProduct(uid);
            return ResponseEntity.ok(new Response("Product deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new Response("Something went wrong. Product not deleted"));
        }
    }
}
