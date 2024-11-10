package com.wesley.service;

import com.wesley.model.Cart;
import com.wesley.model.CartItem;
import com.wesley.request.AddCartitemRequest;

import java.math.BigDecimal;

public interface CartService {

    public CartItem addCartItem(AddCartitemRequest request, String jwt)throws Exception;

    public CartItem updateCartItem(Long cartitemId, Integer quantity)throws Exception;

    public Cart removeItemFromCart(Long cartitemId, String jwt)throws Exception;

    public BigDecimal getCartTotal(Cart cart)throws Exception;

    public Cart  findCartById(Long cartId)throws Exception;
    public Cart findCartByUserId(Long userId)throws Exception;
    public Cart clearCart(Long userId)throws Exception;



}
