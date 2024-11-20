package com.wesley.service;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.wesley.Response.PaymentResponse;
import com.wesley.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class PaymentServiceImplements implements Paymentservice {
    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    public PaymentResponse createPaymentLink(Order order) throws Exception {
        Stripe.apiKey = stripeSecretKey;

        // Calcular tempo de expiração (30 minutos)
        long expiresAt = Instant.now().plus(30, ChronoUnit.MINUTES).getEpochSecond();

        // Calcular valor em centavos
        long unitAmount = order.getTotalAmount().multiply(BigDecimal.valueOf(100)).longValue();

        // Criar parâmetros para a sessão
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment/success/" + order.getId())
                .setCancelUrl("http://localhost:3000/payment/fail/" + order.getId())
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("eur") // Código correto para euros
                                .setUnitAmount(unitAmount) // Valor em centavos
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Wesp Food") // Nome do produto
                                        .build())
                                .build())
                        .build())
                .setExpiresAt(expiresAt)
                .build();

        // Criar sessão de pagamento no Stripe
        Session session = Session.create(params);

        // Retornar a URL de pagamento
        return new PaymentResponse(session.getUrl());

    }

    }

