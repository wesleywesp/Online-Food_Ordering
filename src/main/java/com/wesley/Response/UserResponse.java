package com.wesley.Response;

import com.wesley.dto.RestauranteDTO;
import com.wesley.model.Address;
import com.wesley.model.USER_ROLE;
import com.wesley.model.User;

import java.util.List;

public record UserResponse(Long id,String fullname, String email, USER_ROLE role,
                           List<RestauranteDTO> favoriteRestaurants,
                           List<Address> addresses) {

    public UserResponse(User user) {
        this(user.getId(), user.getFullName(), user.getEmail(), user.getRole(), user.getFavorites(), user.getAddresses());
    }
}

