package com.example.products.mediator;

import com.example.products.entity.ProductEntity;
import com.example.products.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMediator {
    private final ProductService productService;

    public ResponseEntity<?> getProducts(int page, int limit, String name, String category, Float price_min,
                                         Float price_max, String creation_date) {
        long totalCount = productService.countActiveProducts(name, category, price_min, price_max);
        List<ProductEntity> product = productService.getProducts(name, category, price_min, price_max, creation_date, page, limit);
        return ResponseEntity.ok().header("X-Total-Count",String.valueOf(totalCount)).body(product);
    }
}
