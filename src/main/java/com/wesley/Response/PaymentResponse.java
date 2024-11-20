package com.wesley.Response;

import lombok.Data;

@Data
public class PaymentResponse {
    private String payment_url;

    public PaymentResponse(String url) {
        this.payment_url = url;
    }
}
