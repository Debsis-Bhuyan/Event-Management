package com.debasis.eventManagement.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {
    private String paymentType;
    private Double amount;
    private String paymentStatus;
}
