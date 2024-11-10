package com.wesley.model;

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

    @Column(name = "image", length = 1000)
    @ElementCollection
    private List<String> image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category foodCategory;


    private Boolean available;


    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;


    private boolean isvegetarian;
    private boolean isSeasonal;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<IngredientsItem> ingredients = new ArrayList<>();

    private Date createdAt;

    public Food(CreateFoodRequest foodRequest, Category category, Restaurant restaurant) {
        this.name = foodRequest.name();
        this.description = foodRequest.description();
        this.price = foodRequest.price();
        this.image = foodRequest.imagem();
        this.foodCategory = category;
        this.restaurant = restaurant;
        this.isvegetarian = foodRequest.vegetarian();
        this.isSeasonal = foodRequest.seasional();
        this.ingredients = foodRequest.ingredients();
        this.available = true;
    }

}
