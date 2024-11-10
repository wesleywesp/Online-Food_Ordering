package com.wesley.request;

import com.wesley.model.Address;

public record OrderRequest(
        Long restaurantId,
        Address  deliveryAddress
) {
}
