package com.wesley.service;

import com.wesley.model.Category;

import java.util.List;

public interface CategoriaService {

    public Category createCategory(String name,String description,Long userId) throws Exception;

    public List<Category> findCategoryByRestaurantId(Long restaurantId)throws Exception;

    public Category findCategoryById(Long categoryId) throws Exception;

}
