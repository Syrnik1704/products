package com.example.products.services;

import com.example.products.entity.ProductDTO;
import com.example.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public ProductDTO getProductDTO() {
        return null;
    }
}
