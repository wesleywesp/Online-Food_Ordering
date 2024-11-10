package com.wesley.service;

import com.wesley.dto.RestauranteDTO;
import com.wesley.model.Address;
import com.wesley.model.Restaurant;
import com.wesley.model.User;
import com.wesley.repository.AddressResponsitory;
import com.wesley.repository.RestaurantRepository;
import com.wesley.repository.UserRepository;
import com.wesley.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestarurantServiseImplements implements RestauranteService{
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressResponsitory addressResponsitory;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest request, User user) {
        Address address = addressResponsitory.save(request.address());
        Restaurant restaurant = new Restaurant(request, user, address);
        return restaurantRepository.save(restaurant);

    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurante) throws Exception {
        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId);
        if(restaurant.getCusinetypes() != null) {
            restaurant.setCusinetypes(updatedRestaurante.cusineTypes());
        }
        if(restaurant.getDescription() != null) {
            restaurant.setDescription(updatedRestaurante.description());
        }
        if(restaurant.getOpningHours() != null) {
            restaurant.setOpningHours(updatedRestaurante.openingHours());
        }
        if(restaurant.getImage() != null) {
            restaurant.setImage(updatedRestaurante.images());
        }
        if(restaurant.getName() != null) {
            restaurant.setName(updatedRestaurante.name());
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);

    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.finBySearchQuery(keyword);
    }

    @Override
    public Restaurant getRestaurantById(Long restaurantId) throws Exception {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isPresent()) {
            return restaurant.get();
        } else {
            throw new Exception("Restaurant not found with id: " + restaurantId);
        }
    }


    @Override
    public List<Restaurant> getRestaurantByUserId(Long userId) throws Exception {
        List<Restaurant> restaurants = restaurantRepository.findAllByOwnerId(userId);
        if (!restaurants.isEmpty()) {
            return restaurants;
        } else {
            throw new Exception("No restaurants found with owner id: " + userId);
        }
    }


    @Override
    public RestauranteDTO addFavoriteRestaurant(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId);
        if (restaurant == null) {
            throw new Exception("Restaurant not found with id: " + restaurantId);
        }

        RestauranteDTO restauranteDTO = new RestauranteDTO();
        restauranteDTO.setId(restaurant.getId());
        restauranteDTO.setDescription(restaurant.getDescription());
        restauranteDTO.setTitle(restaurant.getName());
        restauranteDTO.setImages(restaurant.getImage());

        boolean isFavorite = false;
        List<RestauranteDTO> favoriteRestaurants = user.getFavoriteRestaurants();

        // Verifica se o restaurante já está nos favoritos
        for (RestauranteDTO favoriteRestaurant : favoriteRestaurants) {
            if (favoriteRestaurant.getId().equals(restaurantId)) {
                isFavorite = true;
                break;
            }
        }

        // Se já é favorito, remove-o; caso contrário, adiciona-o
        if (isFavorite) {
            favoriteRestaurants.removeIf(favorite -> favorite.getId().equals(restaurantId));
        } else {
            favoriteRestaurants.add(restauranteDTO);
        }

        userRepository.save(user);
        return restauranteDTO;
    }


    @Override
    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);

    }
}
