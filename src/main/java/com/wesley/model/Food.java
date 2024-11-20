package com.wesley.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wesley.request.CreateFoodRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "foods")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private BigDecimal price;

    @ElementCollection
    @CollectionTable(name = "food_images", joinColumns = @JoinColumn(name = "food_id"))
    @Column(name = "image_url")
    private List<String> image = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category foodCategory;

    private Boolean available;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;


    private Boolean vegetarian;
    private Boolean seasonal;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "food_ingredients",
            joinColumns = @JoinColumn(name = "food_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<IngredientsItem> ingredients = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    public Food(CreateFoodRequest foodRequest, Category category, Restaurant restaurant) {
        this.name = foodRequest.name();
        this.description = foodRequest.description();
        this.price = foodRequest.price();
        this.image = foodRequest.imagem();
        this.foodCategory = category;
        this.restaurant = restaurant;
        this.vegetarian = foodRequest.vegetarian();
        this.seasonal = foodRequest.seasional();
        this.ingredients = foodRequest.ingredients();
        this.available = true;
    }
}
