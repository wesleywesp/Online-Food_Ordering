package com.wesley.service;

import com.wesley.model.*;
import com.wesley.repository.AddressResponsitory;
import com.wesley.repository.OrderItemRepository;
import com.wesley.repository.OrderRepository;
import com.wesley.repository.UserRepository;
import com.wesley.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImplements implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private AddressResponsitory addressResponsitory;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestauranteService restauranteService;
    @Autowired
    private CartService cartService;

    @Override
    public Order createOrder(OrderRequest orderRequest, User user) throws Exception {
        Address shippAddress = orderRequest.deliveryAddress();

        Address savedAddress = addressResponsitory.save(shippAddress);
        if(!user.getAddresses().contains(savedAddress)){
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }
        Restaurant restaurant = restauranteService.getRestaurantById(orderRequest.restaurantId());
        Order createdOrder = new Order();
        createdOrder.setDeliveryAddress(savedAddress);
        createdOrder.setRestaurant(restaurant);
        createdOrder.setCustomer(user);
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setCreatedAt(new Date());

        Cart cart = cartService.findCartByUserId(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem cartItem : cart.getItem()){
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(createdOrder);
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalprice(cartItem.getTotalprice());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);

        }
        createdOrder.setItems(orderItems);
        createdOrder.setTotalPrice(cart.getTotal());

        Order savedOrder = orderRepository.save(createdOrder);

        restaurant.getOrders().add(savedOrder);

        return createdOrder;



    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = findOrderById(orderId);
        if (orderStatus.equals("OUT_FOR_DELIVERY")
                ||orderStatus.equals("DELIVERED")
                ||orderStatus.equals("CANCELLED")
                ||orderStatus.equals("COMPLETED")
                ||orderStatus.equals("PENDING"))
        {
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);

        }
        throw new Exception("Invalid Order Status");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Order order = findOrderById(orderId);
        orderRepository.delete(order);
        order.setOrderStatus("CANCELLED");

    }

    @Override
    public List<Order> getUserOrder(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);

        if(orderStatus != null){
           orders = orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
            }
        return orders;

    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isPresent()){
            return optionalOrder.get();
        }else {
            throw new Exception("Order not found");
        }
    }
}
