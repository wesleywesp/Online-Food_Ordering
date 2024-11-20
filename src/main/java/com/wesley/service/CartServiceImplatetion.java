package com.wesley.service;

import com.wesley.model.Cart;
import com.wesley.model.CartItem;
import com.wesley.model.Food;
import com.wesley.model.User;
import com.wesley.repository.CarItemRepository;
import com.wesley.repository.CartRepository;
import com.wesley.request.AddCartitemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class CartServiceImplatetion implements CartService {
    @Autowired
    private CarItemRepository carItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private FoodService foodService;
    @Override
    public CartItem addCartItem(AddCartitemRequest request, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.findFoodById(request.foodId());

        // Encontrar o carrinho do usuário
        Cart cart = cartRepository.findByCustomerId(user.getId());

        if (cart == null) {
            // Se o carrinho não existir, crie um novo carrinho
            cart = new Cart();
            cart.setCustomer(user);
            cart.setTotal(BigDecimal.ZERO);
            cartRepository.save(cart);  // Salve o novo carrinho no banco de dados
        }

        for(CartItem cartItem : cart.getItem()){
            if(cartItem.getFood().equals(food)){
                int updatedQuantity = cartItem.getQuantity() + request.quantity();
                return updateCartItem(cartItem.getId(), updatedQuantity);
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setFood(food);
        cartItem.setCart(cart);
        cartItem.setQuantity(request.quantity());
        cartItem.setTotalprice(BigDecimal.valueOf(request.quantity()).multiply(food.getPrice()));
        cartItem.setIngredients(request.ingredients());

        CartItem savedCartItem = carItemRepository.save(cartItem);

        cart.getItem().add(savedCartItem);  // Adiciona o item no carrinho

        return savedCartItem;
    }


    @Override
    public CartItem updateCartItem(Long cartitemId, Integer quantity) throws Exception {
        CartItem cartItem = carItemRepository.findById(cartitemId).orElseThrow(() -> new Exception("Cart item not found"));

        cartItem.setQuantity(quantity);
        cartItem.setTotalprice(BigDecimal.valueOf(quantity).multiply(cartItem.getFood().getPrice()));

        return carItemRepository.save(cartItem);
    }

    @Override
    public Cart removeItemFromCart(Long cartitemId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Cart cart = cartRepository.findByCustomerId(user.getId());

        CartItem cartItem = carItemRepository.findById(cartitemId).orElseThrow(() -> new Exception("Cart item not found"));

        cart.getItem().remove(cartItem);
        return cartRepository.save(cart);
    }

    @Override
    public BigDecimal getCartTotal(Cart cart) throws Exception {
        BigDecimal total = BigDecimal.ZERO;
        for(CartItem cartItem : cart.getItem()){
            total = cartItem.getFood().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
        }
        return total;
    }

    @Override
    public Cart findCartById(Long cartId) throws Exception {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new Exception("Cart not found"));
        return cart;
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {

        Cart cart = cartRepository.findByCustomerId(userId);
        cart.setTotal(getCartTotal(cart));

        return cart;
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {
        Cart cart = cartRepository.findByCustomerId(userId);
        cart.getItem().clear();
        return cartRepository.save(cart);
    }
}
