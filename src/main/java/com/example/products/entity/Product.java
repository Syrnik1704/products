package com.example.products.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Product {
    @Id
    @GeneratedValue(generator = "products_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "products_id_seq", sequenceName = "products_id_seq", allocationSize = 1)
    private long id;
    private String uid;
    private boolean active;
    @Column(name = "product_name")
    private String name;
    private String mainDescription;
    private String htmlDescription;
    private float price;
    private String[] imageUrls;
    private String parameters;
    private LocalDate createdAt;
}
