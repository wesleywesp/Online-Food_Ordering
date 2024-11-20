package com.wesley.Response;

import com.wesley.model.Category;
import com.wesley.model.Food;
import com.wesley.model.IngredientsItem;

import java.math.BigDecimal;
import java.util.List;

public record FoodResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        List<String> image,
        Boolean vegetarian,
        Boolean seasonal,
        Boolean isAvailable,
        List<IngredientsItem> ingredients
) {
    // Construtor alternativo que aceita um objeto do tipo Food
    public FoodResponse(Food food) {
        this(
                food.getId(),
                food.getName(),
                food.getDescription(),
                food.getPrice(),
                food.getImage(),
                food.getVegetarian(),
                food.getSeasonal(),
                food.getAvailable(),
                food.getIngredients()
        );
    }
}
