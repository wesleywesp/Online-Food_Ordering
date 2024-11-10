package com.wesley.service;

import com.wesley.model.Order;
import com.wesley.model.User;
import com.wesley.request.OrderRequest;

import java.util.List;

public interface OrderService {
    public Order createOrder(OrderRequest orderRequest, User user) throws Exception;

    public Order updateOrder( Long orderId, String orderStatus)throws Exception;

    public void cancelOrder(Long orderId)throws Exception;

    public List<Order> getUserOrder(Long userId)throws Exception;

    public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus)throws Exception;

    public Order findOrderById(Long orderId)throws Exception;

}
