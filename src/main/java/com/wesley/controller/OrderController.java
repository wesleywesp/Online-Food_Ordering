package com.wesley.controller;


import com.wesley.Response.PaymentResponse;
import com.wesley.model.Order;
import com.wesley.model.OrderItem;
import com.wesley.request.OrderRequest;
import com.wesley.service.OrderService;
import com.wesley.service.Paymentservice;
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
    private Paymentservice paymentservice;
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;
    private OrderItem cartItem;

    @PostMapping("/order")
    @Transactional
    public ResponseEntity<PaymentResponse> createOrder(@RequestBody OrderRequest request,
                                                       @RequestHeader("Authorization") String token) throws Exception {

        Order order = orderService.createOrder(request, userService.findUserByJwtToken(token));
        PaymentResponse paymentResponse = paymentservice.createPaymentLink(order);
        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(
                                             @RequestHeader("Authorization") String token) throws Exception {
        List<Order> orders = orderService.getUserOrder(userService.findUserByJwtToken(token).getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
