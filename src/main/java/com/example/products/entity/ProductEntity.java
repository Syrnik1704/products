package com.example.products.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "products")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEntity extends Product {
    @ManyToOne
    @JoinColumn(name = "categories")
    private Category category;

    public ProductEntity(long id, String uid, boolean active, String name, String mainDescription, String htmlDescription, long categoryId, float price, String[] imageUrls, String parameters, LocalDate createdAt, Category category) {
        super(id, uid, active, name, mainDescription, htmlDescription, categoryId, price, imageUrls, parameters, createdAt);
        this.category = category;
    }
}
