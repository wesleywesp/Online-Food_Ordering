package com.wesley.controller;

import com.wesley.model.Category;
import com.wesley.model.User;
import com.wesley.service.CategoriaService;
import com.wesley.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@SecurityRequirement(name = "bearer-key")

public class CategoryController {

    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private UserService userService;
    @PostMapping("/admin/category")
    @Transactional
    public ResponseEntity<?> createCategory(@RequestBody Category category, @RequestHeader
                                            ("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        Category creatCategory = categoriaService.createCategory(category.getName(), user.getId());
        return new ResponseEntity<>(creatCategory, HttpStatus.CREATED);

    }
    @GetMapping("/category/restaurants")
    public ResponseEntity<?> getRestaurantCategory(@RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        List<Category> creatCategory = categoriaService.findCategoryByRestaurantId(user.getId());
        return ResponseEntity.ok().body(creatCategory);

    }
}
