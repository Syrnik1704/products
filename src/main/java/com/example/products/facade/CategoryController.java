package com.example.products.facade;

import com.example.products.entity.CategoryDTO;
import com.example.products.entity.Response;
import com.example.products.exceptions.ObjectExistInDBException;
import com.example.products.mediator.CategoryMediator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryMediator categoryMediator;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        return categoryMediator.getCategories();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createCategory(@RequestBody CategoryDTO categoryDTO){
        try {
            categoryMediator.createCategory(categoryDTO);
        } catch (ObjectExistInDBException e) {
            return ResponseEntity.status(400).body(new Response("Object exist in DB"));
        }
        return ResponseEntity.ok(new Response("Operation end Success"));
    }

}
