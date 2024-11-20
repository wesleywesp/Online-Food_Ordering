package com.wesley.controller;

import com.wesley.Response.AuthResponse;
import com.wesley.model.USER_ROLE;
import com.wesley.request.UserDTO;
import com.wesley.infra.security.JwtTokenProvider;

import com.wesley.model.Cart;
import com.wesley.model.User;
import com.wesley.repository.CartRepository;
import com.wesley.repository.UserRepository;
import com.wesley.request.LoginRequest;
import com.wesley.service.CustomerUserDetailService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class Authcontroller {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CustomerUserDetailService customerUserDetailService;
    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<AuthResponse>createUserHandler(@RequestBody @Valid UserDTO user ) throws Exception {
        User isEmailExists = userRepository.findByEmail(user.email());
        if(isEmailExists != null){
            throw new Exception("Email is already used by another account");
        }
        User createUser = new User(user, passwordEncoder);
        userRepository.save(createUser);
        Cart cart = new Cart();
        cart.setCustomer(createUser);

        // Criação do Authentication após validar a senha do usuário
        Authentication authentication = new UsernamePasswordAuthenticationToken(createUser.getEmail(), user.password());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Gerar o token JWT para autenticação
        String token = jwtTokenProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(token, "User created successfully", createUser.getRole());
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);

    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody @Valid LoginRequest loginRequest) {

        String username = loginRequest.email();
        String password = loginRequest.password();

        Authentication authentication = authenticate(username, password);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();


        String token = jwtTokenProvider.generateToken(authentication);


        AuthResponse authResponse = new AuthResponse(token, "Login Success", USER_ROLE.valueOf(roles));
        return ResponseEntity.ok().body(authResponse);
    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails = customerUserDetailService.loadUserByUsername(userName);
        if (userDetails == null) {
            throw new BadCredentialsException("invalid username");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("invalid password");
        }
       return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
