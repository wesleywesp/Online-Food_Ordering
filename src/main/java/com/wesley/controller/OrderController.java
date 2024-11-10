package com.wesley.controller;


import com.wesley.model.Order;
import com.wesley.model.OrderItem;
import com.wesley.request.OrderRequest;
import com.wesley.service.OrderService;
import com.wesley.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearer-key")

public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;
    private OrderItem cartItem;

    @PostMapping("/order")
    @Transactional
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request,
                                             @RequestHeader("Authorization") String token) throws Exception {

        Order order = orderService.createOrder(request, userService.findUserByJwtToken(token));
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(@RequestBody OrderRequest request,
                                             @RequestHeader("Authorization") String token) throws Exception {
        List<Order> orders = orderService.getUserOrder(userService.findUserByJwtToken(token).getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
