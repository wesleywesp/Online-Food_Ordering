package com.wesley.request;

import java.util.List;

public record AddCartitemRequest(Long foodId, Integer quantity,
                                 List<String> ingredients) {
}
