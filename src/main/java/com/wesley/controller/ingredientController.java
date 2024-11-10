package com.wesley.controller;

import com.wesley.model.IngredientCategory;
import com.wesley.model.IngredientsItem;
import com.wesley.model.User;
import com.wesley.request.IngredientCategoryRequest;
import com.wesley.request.IngredientItemRequest;
import com.wesley.service.IngredientsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/ingredients")
@SecurityRequirement(name = "bearer-key")

public class ingredientController {
    @Autowired
    private IngredientsService ingredientsService;


    @PostMapping("/category")
    @Transactional
    public ResponseEntity<IngredientCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest request) throws Exception {

        IngredientCategory ingredientCategory = ingredientsService.createIngredientCategory(request.name(), request.restaurantId());

        return new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);
    }
    @PostMapping
    public ResponseEntity<IngredientsItem> createIngredientItem(@RequestBody IngredientItemRequest request) throws Exception {
        IngredientsItem item = ingredientsService.createIngredientItem(request.name(), request.restaurantId(), request.categoryId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }
    @PutMapping("/{id}/stoke")
    public ResponseEntity<IngredientsItem> updateStoke(@PathVariable Long id) throws Exception {

        IngredientsItem item = ingredientsService.UpdateStock(id);
        return ResponseEntity.ok(item);
    }
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredients(@PathVariable Long id) throws Exception {

        List<IngredientsItem> items = ingredientsService.findRestaurantIngredients(id);

        return ResponseEntity.ok().body(items);
    }
    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategory(@PathVariable Long id) throws Exception {
        List<IngredientCategory> category = ingredientsService.findIngredientCategoryByRestaurantId(id);

        return ResponseEntity.ok().body(category);
    }
}
