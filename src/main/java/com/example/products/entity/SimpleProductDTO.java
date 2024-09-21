package com.example.products.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleProductDTO {
    private String name;
    private String mainDescription;
    private float price;
    private String imageUrls;
    private LocalDate createdAt;
}
