package com.wesley.controller;

import com.wesley.Response.MessageResponse;
import com.wesley.model.Restaurant;
import com.wesley.model.User;
import com.wesley.request.CreateRestaurantRequest;
import com.wesley.service.RestauranteService;
import com.wesley.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/restaurant")
@SecurityRequirement(name = "bearer-key")

public class AdminRestaurantController {
    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private UserService userService;

    @PostMapping()
    @Transactional
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest request,
                                                       @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        Restaurant restaurant = restauranteService.createRestaurant(request, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);

    }

    @PutMapping("/{restaurantId}")
    @Transactional
    public ResponseEntity<Restaurant> updatRestaurant(@RequestBody CreateRestaurantRequest request,
                                                       @RequestHeader("Authorization") String token,
                                                      @PathVariable Long restaurantId) throws Exception {

        User user = userService.findUserByJwtToken(token);
        Restaurant restaurant = restauranteService.updateRestaurant(restaurantId, request);
        return ResponseEntity.ok().body(restaurant);
    }

    @DeleteMapping("/{restaurantId}")
    @Transactional
    public ResponseEntity<MessageResponse>deletedRestaurant(@RequestHeader("Authorization") String token,
                                                            @PathVariable Long restaurantId) throws Exception {

        User user = userService.findUserByJwtToken(token);
        restauranteService.deleteRestaurant(restaurantId);
        MessageResponse messageResponse = new MessageResponse("Restaurant deleted successfully");
        return ResponseEntity.ok(messageResponse);
    }
    @PutMapping("/{id}/status")
    @Transactional
    public ResponseEntity<Restaurant> updatRestaurantStatus(@RequestHeader("Authorization") String token,
                                                      @PathVariable Long id) throws Exception {

        User user = userService.findUserByJwtToken(token);
        Restaurant restaurant = restauranteService.updateRestaurantStatus(id);
        return ResponseEntity.ok().body(restaurant);
    }
    @GetMapping("/user")
    public ResponseEntity<?> findRestaurantsByUserId(@RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        List<Restaurant> restaurants = restauranteService.getRestaurantByUserId(user.getId());

        if (restaurants.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No restaurants found for this user.");
        }

        return ResponseEntity.ok().body(restaurants);
    }


}
