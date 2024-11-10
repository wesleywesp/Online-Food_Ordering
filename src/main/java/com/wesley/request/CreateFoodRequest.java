package com.wesley.request;

import com.wesley.model.Category;
import com.wesley.model.IngredientsItem;

import java.math.BigDecimal;
import java.util.List;

public record CreateFoodRequest(
        String name,
        String description,
        BigDecimal price,

        Category category,

        List<String> imagem,

        Long restaurantId,

        boolean vegetarian,

        boolean seasional,

        List<IngredientsItem> ingredients) {
}
