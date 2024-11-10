package com.wesley.Response;

import com.wesley.model.USER_ROLE;

public record AuthResponse(String tokenJwt, String message, USER_ROLE role) {

}
