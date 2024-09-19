package com.example.products.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
@Entity
public class Category {
    @Id
    @GeneratedValue(generator = "categories_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "categories_id_seq", sequenceName = "categories_id_seq", allocationSize = 1)
    private long id;
    @Column(name = "category_name")
    private String name;
    private String shortId;
}
