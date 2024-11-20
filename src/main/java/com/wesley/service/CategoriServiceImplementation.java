package com.wesley.service;

import com.wesley.model.Category;
import com.wesley.model.Restaurant;
import com.wesley.repository.CategoryReposetory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CategoriServiceImplementation implements CategoriaService {
    @Autowired
    private CategoryReposetory categoryReposetory;
    @Autowired
    private RestauranteService restauranteService;
    @Override
    public Category createCategory(String name,String descripton, Long userId) throws Exception {
        Restaurant restaurant = restauranteService.getRestaurantById(userId);
        Category category = new Category(name, descripton, restaurant);
        return categoryReposetory.save(category);
    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long restaurantId) throws Exception {
        Restaurant restaurant = restauranteService.getRestaurantById(restaurantId);
        return categoryReposetory.findByRestaurantId(restaurantId);
    }

    @Override
    public Category findCategoryById(Long categoryId) throws Exception {
        Optional<Category> category = categoryReposetory.findById(categoryId);
        if(category.isEmpty()) {
            throw new Exception("Category not found");
        } else {
            return category.get();
        }
    }
}
