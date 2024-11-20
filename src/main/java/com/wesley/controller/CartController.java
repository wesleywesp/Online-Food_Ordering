package com.wesley.controller;

import com.wesley.model.Cart;
import com.wesley.model.CartItem;
import com.wesley.model.User;
import com.wesley.request.AddCartitemRequest;
import com.wesley.request.UpdateCartItemRequest;
import com.wesley.service.CartService;
import com.wesley.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearer-key")

public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @PutMapping("/cart/add")
    @Transactional
    public ResponseEntity<?> addItemToCart(@RequestBody AddCartitemRequest request,
                                       @RequestHeader("Authorization") String token) throws Exception {
        System.out.println(request.foodId());
        System.out.println(request.quantity());
        System.out.println(request.ingredients());
      CartItem cartItem =cartService.addCartItem(request, token);

        return new ResponseEntity<>(cartItem,HttpStatus.OK);
    }
    @PutMapping("/cart-item/update")
    @Transactional
    public ResponseEntity<?> updateCartItemQuantity(@RequestBody UpdateCartItemRequest request,
                                                    @RequestHeader("Authorization") String jwtToken) throws Exception {
        System.out.println(request.cartItemId());
        System.out.println(request.quantity());
        User user = userService.findUserByJwtToken(jwtToken);
        CartItem cartItem = cartService.updateCartItem(request.cartItemId(), request.quantity());
        return new ResponseEntity<>(cartItem,HttpStatus.OK);
    }
    @DeleteMapping("/cart-item/{cartItemId}/remove")
    @Transactional
    public ResponseEntity<?> removeItemFromCart(@PathVariable Long cartItemId,
                                                @RequestHeader("Authorization") String jwtToken) throws Exception {
        Cart cart = cartService.removeItemFromCart(cartItemId, jwtToken);
        return new ResponseEntity<>(cart,HttpStatus.OK);
    }

    @PutMapping("/cart/clear")
    @Transactional
    public ResponseEntity<?> clearCart(@RequestHeader("Authorization")String jwtToken) throws Exception {
        User user = userService.findUserByJwtToken(jwtToken);
        Cart cart =cartService.clearCart(user.getId());
        return new ResponseEntity<>(cart,HttpStatus.OK);
    }
    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findCartByUserId(user.getId());
        return new ResponseEntity<>(cart,HttpStatus.OK);
    }


}
