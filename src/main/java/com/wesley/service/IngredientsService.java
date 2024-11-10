package com.wesley.service;

import com.wesley.model.IngredientCategory;
import com.wesley.model.IngredientsItem;

import java.util.List;

public interface IngredientsService {

    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception;

    public IngredientCategory findIngredientCategoryById(Long categoryId) throws Exception;

    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long restaurantId) throws Exception;

    public IngredientsItem createIngredientItem(String ingredientName, Long restaurantId, Long categoryId ) throws Exception;

    public List<IngredientsItem>findRestaurantIngredients(Long restaurantId) throws Exception;

    public  IngredientsItem UpdateStock(Long ingredientId) throws Exception;


}
