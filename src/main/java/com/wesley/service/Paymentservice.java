package com.wesley.service;

import com.wesley.Response.PaymentResponse;
import com.wesley.model.Order;
import org.springframework.stereotype.Service;

public interface Paymentservice {
public PaymentResponse createPaymentLink(Order order) throws Exception;
}
