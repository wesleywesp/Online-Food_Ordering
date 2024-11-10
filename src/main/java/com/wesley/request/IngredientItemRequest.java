package com.wesley.request;

public record IngredientItemRequest(
    String name,
    Long categoryId,
    Long restaurantId
    ) {
}
