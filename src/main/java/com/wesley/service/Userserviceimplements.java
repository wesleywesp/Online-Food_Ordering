package com.wesley.service;

import com.wesley.infra.security.JwtTokenProvider;
import com.wesley.model.User;
import com.wesley.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Userserviceimplements implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public User findUserByJwtToken(String token) throws Exception {
        String email = jwtTokenProvider.getEmailFromJwtToken(token);
        User user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new Exception("User not found with email: " + email);
        }
        return user;
    }
}
