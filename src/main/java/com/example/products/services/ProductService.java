package com.example.products.services;

import com.example.products.entity.ProductEntity;
import com.example.products.repository.CategoryRepository;
import com.example.products.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    @PersistenceContext
    EntityManager entityManager;

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public long countActiveProducts(String name,
                                    String category,
                                    Float price_min,
                                    Float price_max) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<ProductEntity> root = query.from(ProductEntity.class);

        List<Predicate> predicates = prepareQuery(name, category, price_min, price_max, criteriaBuilder, root);

        query.select(criteriaBuilder.count(root)).where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getSingleResult();
    }

    public List<ProductEntity> getProducts(String name,
                                           String category,
                                           Float price_min,
                                           Float price_max,
                                           String creation_date,
                                           int page,
                                           int limit,
                                           String sort,
                                           String order) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductEntity> query = criteriaBuilder.createQuery(ProductEntity.class);
        Root<ProductEntity> root = query.from(ProductEntity.class);

        if (creation_date != null && !creation_date.isEmpty() && name != null && !name.trim().isEmpty()){
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate date = LocalDate.parse(creation_date, inputFormatter);
            return productRepository.findByNameAndCreatedAt(name,date);
        }

        if (page <= 0) page = 1;

        List<Predicate> predicates = prepareQuery(name, category, price_min, price_max, criteriaBuilder, root);

        if (!order.isEmpty() && !sort.isEmpty()) {
            String column = switch (sort) {
                case "name" -> "name";
                case "category" -> "category";
                case "creation_date" -> "created_at";
                default -> "price";
            };
            Order orderQuery;
            if (order.equals("desc")) {
                orderQuery = criteriaBuilder.desc(root.get(column));
            } else {
                orderQuery = criteriaBuilder.asc(root.get(column));
            }
            query.orderBy(orderQuery);
        }
        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
    }

    private List<Predicate> prepareQuery(String name,
                                         String category,
                                         Float price_min,
                                         Float price_max,
                                         CriteriaBuilder criteriaBuilder,
                                         Root<ProductEntity> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (name != null && !name.trim().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (category != null && !category.isEmpty()) {
            categoryRepository.findByShortId(category).
                    ifPresent(value-> predicates.add(criteriaBuilder.equal(root.get("category"), value)));
        }
        if (price_min != null) {
            predicates.add(criteriaBuilder.greaterThan(root.get("price"), price_min-0.01));
        }
        if (price_max != null) {
            predicates.add(criteriaBuilder.lessThan(root.get("price"), price_max+0.01));
        }
        return predicates;
    }

}
