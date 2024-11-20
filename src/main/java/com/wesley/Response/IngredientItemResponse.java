package com.wesley.Response;

import com.wesley.model.Category;
import com.wesley.model.IngredientCategory;
import com.wesley.model.IngredientsItem;
import lombok.Data;
@Data
public class IngredientItemResponse {
    private Long id;
    private String name;
    private IngredientCategory category;
    private Boolean inStock;

    public IngredientItemResponse(IngredientsItem item) {
        this.id = item.getId();
        this.name = item.getName();
        this.category = item.getCategory() ;
        this.inStock = item.getInStock();
    }
}

