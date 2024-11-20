package com.wesley.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "ingredients_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientsItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    private IngredientCategory category;
    @JsonIgnore
    @ManyToOne
    private Restaurant restaurant;

    private Boolean inStock ;

    public IngredientsItem(String ingredientName, Restaurant restaurant, IngredientCategory ingredientCategory) {
        this.name = ingredientName;
        this.restaurant = restaurant;
        this.category = ingredientCategory;
    }



}

