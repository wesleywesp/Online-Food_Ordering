package com.wesley.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wesley.dto.RestauranteDTO;
import com.wesley.request.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    @JsonIgnore
    private String password;

    private USER_ROLE role= USER_ROLE.ROLE_CUSTOMER;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();
    @ElementCollection
    private List<RestauranteDTO>favoriteRestaurants = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    public User(@Valid UserDTO user) {
    }

    public User(@Valid UserDTO user, PasswordEncoder passwordEncoder) {
        this.fullName = user.fullName();
        this.email = user.email();
        this.password = passwordEncoder.encode(user.password());
        this.role = user.role();
    }
}
