package com.wesley.Response;


import com.wesley.model.IngredientCategory;
import com.wesley.model.Restaurant;

import lombok.Data;

@Data
public class IngredientICategoryResponse {
    private Long id;

    private String name;


    private String description;

    public IngredientICategoryResponse(IngredientCategory category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
    }

}

