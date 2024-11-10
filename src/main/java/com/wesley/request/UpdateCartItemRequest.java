package com.wesley.request;

public record UpdateCartItemRequest (Long cartItemId, Integer quantity) {
}
