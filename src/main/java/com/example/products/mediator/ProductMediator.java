package com.example.products.mediator;

import com.example.products.entity.ProductDTO;
import com.example.products.entity.ProductEntity;
import com.example.products.entity.SimpleProductDTO;
import com.example.products.mappers.ProductEntityToProductDTOMapper;
import com.example.products.mappers.ProductEntityToSimpleProductMapper;
import com.example.products.services.ProductService;
import lombok.RequiredArgsConstructor;
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
    private final ProductEntityToSimpleProductMapper productEntityToSimpleProductMapper;
    private final ProductEntityToProductDTOMapper productEntityToProductDTOMapper;

    public ResponseEntity<?> getProducts(int page, int limit, String name, String category, Float price_min,
                                         Float price_max, String creation_date, String sort, String order) {

        List<ProductEntity> product = productService.getProducts(name, category, price_min, price_max,
                creation_date, page, limit, sort, order);

        if (name != null && !name.isEmpty()) {
            name = URLDecoder.decode(name, StandardCharsets.UTF_8);
        }

        if (name == null || name.isEmpty() || creation_date == null || creation_date.isEmpty()){
            List<SimpleProductDTO> simpleProductDTOS = new ArrayList<>();
            long totalCount  = productService.countActiveProducts( name, category, price_min, price_max);
            product.forEach(value -> {
                simpleProductDTOS.add(productEntityToSimpleProductMapper.mapToSimpleProduct(value));
            });
            return ResponseEntity.ok().header("X-Total-Count", String.valueOf(totalCount)).body(simpleProductDTOS);
        }
        ProductDTO productDTO = productEntityToProductDTOMapper.mapToProductDTO(product.get(0));
        return ResponseEntity.ok().body(productDTO);
    }
}
