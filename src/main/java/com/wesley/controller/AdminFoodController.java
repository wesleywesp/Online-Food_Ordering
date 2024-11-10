package com.wesley.controller;

import com.wesley.Response.FoodResponse;
import com.wesley.Response.MessageResponse;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("api/admin/food")
@SecurityRequirement(name = "bearer-key")

public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestauranteService restauranteService;

    @PostMapping
    @Transactional
    public ResponseEntity<FoodResponse>createFood(@RequestBody @Valid CreateFoodRequest request,
                                                  @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        Restaurant restaurant = restauranteService.getRestaurantById(request.restaurantId());
        Food food = foodService.createFood(request, request.category() ,restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(new FoodResponse(food));
    }
    @DeleteMapping("/{foodId}")
    @Transactional
    public ResponseEntity<MessageResponse>deletedFood(@PathVariable Long foodId,
                                                      @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        foodService.deleteFood(foodId);
        MessageResponse messageResponse = new MessageResponse("Food deleted successfully");
        return ResponseEntity.ok(messageResponse);    
    }
    @PutMapping("/{foodId}")
    @Transactional
    public ResponseEntity<FoodResponse>updateFoodAvaibilityStatus(@PathVariable Long foodId,
                                                      @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        Food foods = foodService.updateAvalibilityFoodStatus(foodId);

        return ResponseEntity.ok().body(new FoodResponse(foods));
    }

}
