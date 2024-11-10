package com.wesley.request;

import com.wesley.model.Restaurant;

public record IngredientCategoryRequest(
        String name,
        Long restaurantId

) {
}
