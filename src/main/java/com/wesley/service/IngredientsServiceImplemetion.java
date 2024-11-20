package com.wesley.service;

import com.wesley.Response.IngredientICategoryResponse;
import com.wesley.Response.IngredientItemResponse;
import com.wesley.model.IngredientCategory;
import com.wesley.model.IngredientsItem;
import com.wesley.model.Restaurant;
import com.wesley.repository.IngredientCategoryRepository;
import com.wesley.repository.IngredientItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class IngredientsServiceImplemetion implements IngredientsService {
    @Autowired
    private IngredientItemRepository ingredientItemRepository;
    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;
    @Autowired
    private RestauranteService restauranteService;

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restauranteService.getRestaurantById(restaurantId);
        IngredientCategory ingredientCategory = new IngredientCategory(name, restaurant);

        return ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long categoryId) throws Exception {
        Optional<IngredientCategory> ingredientCategory = ingredientCategoryRepository.findById(categoryId);
        if(ingredientCategory.isEmpty()) {
            throw new Exception("ingredient Category not found");
        }
        return ingredientCategory.get();
    }

    @Override
    public List<IngredientICategoryResponse> findIngredientCategoryByRestaurantId(Long restaurantId) throws Exception {
        Restaurant restaurant = restauranteService.getRestaurantById(restaurantId);
        List<IngredientCategory> category= ingredientCategoryRepository.findByRestaurantId(restaurantId);
        return category.stream().map(IngredientICategoryResponse::new).toList();
    }

    @Override
    public IngredientsItem createIngredientItem(String ingredientName, Long restaurantId, Long categoryId) throws Exception {
        Restaurant restaurant = restauranteService.getRestaurantById(restaurantId);
        IngredientCategory category = findIngredientCategoryById(categoryId);
        IngredientsItem ingredientsItem = new IngredientsItem(ingredientName, restaurant, category);
        ingredientsItem = ingredientItemRepository.save(ingredientsItem);

        category.getIngredients().add(ingredientsItem);

        return ingredientsItem;
    }

    @Override
    public List<IngredientItemResponse> findRestaurantIngredients(Long restaurantId) throws Exception {
        Restaurant restaurant = restauranteService.getRestaurantById(restaurantId);
       List<IngredientsItem>item =ingredientItemRepository.findByRestaurantId(restaurantId);
       return item.stream().map(IngredientItemResponse::new).toList();
    }

    @Override
    public IngredientsItem UpdateStock(Long ingredientId) throws Exception {
        Optional<IngredientsItem> ingredientsItem = ingredientItemRepository.findById(ingredientId);
        if(ingredientsItem.isEmpty()) {
            throw new Exception("Ingredient not found");
        }
        IngredientsItem item = ingredientsItem.get();
        item.setInStock(!item.getInStock());
        return ingredientItemRepository.save(item);
    }
}
