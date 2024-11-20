package com.wesley.controller;

import com.wesley.dto.RestauranteDTO;
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
@RequestMapping("/api/restaurants")
@SecurityRequirement(name = "bearer-key")

public class RestaurantController {

    @Autowired
    private RestauranteService restaurantService;
    @Autowired
    private UserService userService;

    @GetMapping("/search")
    @Transactional
    public ResponseEntity<List<Restaurant>> searcRestarurant(@RequestParam String keyword,
                                                            @RequestHeader("Authorization") String token) throws Exception {

        User user = userService.findUserByJwtToken(token);
        List<Restaurant> restaurant = restaurantService.searchRestaurant(keyword);
        return ResponseEntity.ok().body(restaurant);
    }

    @GetMapping()
    @Transactional
    public ResponseEntity<List<Restaurant>> getAllRestarurant(@RequestHeader("Authorization") String token) throws Exception {

        User user = userService.findUserByJwtToken(token);
        List<Restaurant> restaurant = restaurantService.getAllRestaurant();
        return ResponseEntity.ok().body(restaurant);
    }
    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> findRestauranteById(@PathVariable("restaurantId") Long restaurantId,
                                                                @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        return ResponseEntity.ok().body(restaurant);
    }

    @PutMapping("/{restaurantId}/add-favorites")
    @Transactional
    public ResponseEntity<RestauranteDTO> addToFavarites(@PathVariable Long restaurantId,
                                                          @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        RestauranteDTO restaurant = restaurantService.addFavoriteRestaurant(restaurantId, user);

        return ResponseEntity.ok().body(restaurant);
    }

}
