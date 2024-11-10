package com.wesley.controller;

import com.wesley.Response.FoodResponse;
import com.wesley.model.Food;
import com.wesley.model.Restaurant;
import com.wesley.model.User;
import com.wesley.request.CreateFoodRequest;
import com.wesley.service.FoodService;
import com.wesley.service.RestauranteService;
import com.wesley.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/food")
@SecurityRequirement(name = "bearer-key")

public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestauranteService restauranteService;

    @GetMapping("/search")
    public ResponseEntity<List<FoodResponse>> searchFood(@RequestParam String foodName,
                                                   @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        List<Food> food =  foodService.searchFood(foodName);
        // Convertendo a lista de alimentos para FoodResponse
        List<FoodResponse> foodResponses = food.stream()
                .map(f -> new FoodResponse(f.getId(), f.getName(), f.getDescription(), f.getFoodCategory(), f.getImage(),
                        f.isIsvegetarian(),f.isSeasonal(),f.getAvailable(), f.getPrice()))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED).body(foodResponses);
    }
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<FoodResponse>> getRestaurantFood(@PathVariable Long restaurantId,
                                                               @RequestParam boolean veegetarian,
                                                                @RequestParam boolean nonoveg,
                                                                @RequestParam boolean seasonal,
                                                               @RequestParam (required = false) String foodCategory,
                                                               @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);

        List<Food> food =  foodService.getRestaurantsFood(restaurantId, veegetarian, nonoveg, seasonal, foodCategory);

        // Convertendo a lista de alimentos para FoodResponse
        List<FoodResponse> foodResponses = food.stream()
                .map(f -> new FoodResponse(f.getId(), f.getName(), f.getDescription(), f.getFoodCategory(), f.getImage(),
                        f.isIsvegetarian(),f.isSeasonal(),f.getAvailable(), f.getPrice()))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(foodResponses);
    }
}
