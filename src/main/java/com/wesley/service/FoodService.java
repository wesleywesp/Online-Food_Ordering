package com.wesley.service;

import com.wesley.Response.FoodResponse;
import com.wesley.model.Category;
import com.wesley.model.Food;
import com.wesley.model.Restaurant;
import com.wesley.request.CreateFoodRequest;

import java.util.List;


public interface FoodService {
    public Food createFood(CreateFoodRequest foodRequest, Category category, Restaurant restaurant);

    void deleteFood(Long foodId)throws Exception;

    public List<Food> getRestaurantsFood(Long restaurantId,
                                                 Boolean isVegetarian,
                                                 Boolean isNoveg,
                                                 Boolean isSeasonal,
                                                 String foodCategory);

    public List<Food> searchFood(String keyword);

    public Food findFoodById(Long foodId)throws Exception;

    public Food updateAvalibilityFoodStatus(Long foodId) throws Exception;

}
