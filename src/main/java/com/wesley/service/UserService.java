package com.wesley.service;

import com.wesley.model.User;

public interface UserService {

    public User findUserByJwtToken(String token)throws Exception;

    public User findUserByEmail(String email) throws Exception;
}
