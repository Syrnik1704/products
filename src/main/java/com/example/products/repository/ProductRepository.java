package com.example.products.repository;

import com.example.products.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT count(*) FROM products WHERE active is true")
    long countActiveProducts();

    List<ProductEntity> findByNameAndCreatedAt(String name, LocalDate createdAt);
}
