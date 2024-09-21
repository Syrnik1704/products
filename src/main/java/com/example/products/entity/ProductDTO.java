package com.example.products.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO extends Product {
    private CategoryDTO categoryDTO;

    public ProductDTO(String uid, boolean active, String name, String mainDescription, String htmlDescription, float price, String[] imageUrls, String parameters, LocalDate created_at, CategoryDTO categoryDTO) {
        super(uid, active, name, mainDescription, htmlDescription, price, imageUrls, parameters, created_at);
        this.categoryDTO = categoryDTO;
    }

}
