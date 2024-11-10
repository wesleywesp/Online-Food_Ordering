package com.wesley.request;

import com.wesley.model.Address;
import com.wesley.model.ContacInformation;
import lombok.Data;


import java.util.List;

public record CreateRestaurantRequest(
        Long id,
        String name,
        String description,
        String cusineTypes,
        Address address,
        ContacInformation contactInformation,
        String openingHours,
        List<String> images
) {
}
