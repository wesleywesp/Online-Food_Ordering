package com.wesley.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @JsonIgnore
    @ManyToOne
    private Restaurant restaurant;

    public Category(String name,  String descripton, Restaurant restaurant) {
        this.name = name;
        this.restaurant = restaurant;
        this.description = descripton;

    }

}
