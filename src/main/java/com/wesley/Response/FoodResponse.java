package com.wesley.Response;

import com.wesley.model.Category;
import com.wesley.model.Food;
import com.wesley.model.IngredientsItem;

import java.math.BigDecimal;
import java.util.List;

public record FoodResponse(
        String name,
        String description,
        BigDecimal price,

        Category category,

        List<String> imagem,

        Long restaurantId,

        boolean vegetarian,

        boolean seasional,

        List<IngredientsItem> ingredients,
        boolean isAvailable
) {
    public FoodResponse(Food food) {
        this(food.getName(), food.getDescription(), food.getPrice(), food.getFoodCategory(), food.getImage(),
                food.getRestaurant().getId(), food.isIsvegetarian(), food.isSeasonal(), food.getIngredients()
                , food.getAvailable());
    }


    public FoodResponse(Long id, String name, String description, Category foodCategory, List<String> image,
                        boolean isvegetarian, boolean seasonal, Boolean available, BigDecimal price) {
        this(name, description, price, foodCategory, image, id, isvegetarian, seasonal, List.of(), available);
    }
}
