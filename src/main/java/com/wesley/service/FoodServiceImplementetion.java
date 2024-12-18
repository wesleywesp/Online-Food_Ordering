package com.wesley.service;

import com.wesley.Response.FoodResponse;
import com.wesley.model.Category;
import com.wesley.model.Food;
import com.wesley.model.Restaurant;
import com.wesley.repository.FoodRepository;
import com.wesley.repository.RestaurantRepository;
import com.wesley.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

@Service
public class FoodServiceImplementetion  implements FoodService {
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public Food createFood(CreateFoodRequest foodRequest, Category category, Restaurant restaurant) {
        Food food = new Food(foodRequest, category, restaurant);
        Food savedFood= foodRepository.save(food);
        restaurant.getFoods().add(savedFood);
        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = foodRepository.findById(foodId).orElseThrow(() -> new Exception("Food not found"));
        Restaurant restaurant = food.getRestaurant();
        restaurant.getFoods().remove(food);
        foodRepository.delete(food);

    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId, Boolean isVegetarian, Boolean isNoveg, Boolean isSeasonal, String foodCategory) {
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);
        foods.stream().forEach(f -> System.out.println(f.getName()));
        System.out.println(restaurantId);
        System.out.println(isVegetarian);
        System.out.println(isNoveg);
        System.out.println(isSeasonal);
        System.out.println(foodCategory);
        if (isVegetarian) {
            foods = filterByVegetarian(foods, isVegetarian);
            System.out.println("After Vegetarian filter: " + foods.size());
        }

        if (isNoveg) {
            foods = filterByNovegar(foods, isNoveg);
            System.out.println("After Noveg filter: " + foods.size());
        }

        if (isSeasonal) {
            foods = filterBySeasonal(foods, isSeasonal);
            System.out.println("After Seasonal filter: " + foods.size());
        }

        if (foodCategory != null && !foodCategory.isEmpty() && !foodCategory.equals("")) {
            foods = filterByCategory(foods, foodCategory);
            System.out.println("After Category filter: " + foods.size());
        }

        foods.stream().forEach(f -> System.out.println(f.getName()));

        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if(food.getFoodCategory() != null) {
                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, Boolean isSeasonal) {
        return foods.stream().filter(food -> food.getSeasonal() == isSeasonal).collect(Collectors.toList());

    }

    private List<Food> filterByNovegar(List<Food> foods, Boolean isNoveg) {
        return foods.stream().filter(food -> food.getVegetarian() == false).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, Boolean isVegetarian) {
        return foods.stream().filter(food -> food.getVegetarian() == isVegetarian).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> foodOptional = foodRepository.findById(foodId);
        if(foodOptional.isEmpty()) {
            throw new Exception("Food not found");
        }
        return foodOptional.get();
    }

    @Override
    public Food updateAvalibilityFoodStatus(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.getAvailable());
        return foodRepository.save(food);
    }
}
