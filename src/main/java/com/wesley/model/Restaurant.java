package com.wesley.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wesley.request.CreateRestaurantRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "restaurants")
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User owner;

    private String name;

    private String description;

    private String cusinetypes;

    @OneToOne
    private Address address;

    @Embedded
    private ContacInformation contactInformation;

    @Column(name = "image", length = 1000)
    @ElementCollection
    private List<String> image;

    private String opningHours;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();


    private LocalDateTime registrationDate;

    private boolean open;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Food>foods = new ArrayList<>();

    public Restaurant(CreateRestaurantRequest request, User user, Address address) {
        this.name = request.name();
        this.description = request.description();
        this.cusinetypes = request.cusineTypes();
        this.address = address;
        this.contactInformation = request.contactInformation();
        this.opningHours = request.openingHours();
        this.image = request.images();
        this.owner = user;
        this.registrationDate = LocalDateTime.now();
        this.open = true;
    }
}
