package com.example.products.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private long id;
    private String uid;
    private boolean active;
    private String name;
    private String mainDescription;
    private String htmlDescription;
    private long categoryId;
    private float price;
    private String[] imageUrls;
    private Map<String, String> parameters;
    private LocalDate createdAt;
}
