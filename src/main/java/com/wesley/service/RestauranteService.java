package com.wesley.service;

import com.wesley.dto.RestauranteDTO;
import com.wesley.model.Restaurant;
import com.wesley.model.User;
import com.wesley.request.CreateRestaurantRequest;

import java.util.List;

public interface RestauranteService {

    public Restaurant createRestaurant(CreateRestaurantRequest request, User user);

    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurante)throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant>getAllRestaurant() ;

    public List<Restaurant> searchRestaurant(String keyword);

    public Restaurant getRestaurantById(Long restaurantId) throws Exception;


    public List<Restaurant> getRestaurantByUserId(Long userId) throws Exception;

    public RestauranteDTO addFavoriteRestaurant(Long restaurantId, User user) throws Exception;


    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception;

}
