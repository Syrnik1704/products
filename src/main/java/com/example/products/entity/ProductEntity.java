package com.example.products.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "products")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class ProductEntity extends Product {
    @Id
    @GeneratedValue(generator = "products_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "products_id_seq", sequenceName = "products_id_seq", allocationSize = 1)
    private long id;
    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    public ProductEntity(long id, String uid, boolean active, String name, String mainDescription, String htmlDescription, float price, String[] imageUrls, String parameters, LocalDate createdAt, Category category) {
        super(uid, active, name, mainDescription, htmlDescription, price, imageUrls, parameters, createdAt);
        this.category = category;
        this.id = id;
    }
}
