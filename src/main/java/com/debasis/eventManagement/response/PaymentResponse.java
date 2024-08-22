package com.debasis.eventManagement.response;

import com.debasis.eventManagement.request.PaymentDTO;
import lombok.Data;

@Data
public class PaymentResponse {
    private int statusCode;
    private String message;
    private PaymentDTO paymentDTO;
}
