package com.debasis.eventManagement.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {

    private Integer ticketType;
    private Double price;

    private EventDTO event;
    private UserDTO user;
    private PaymentDTO payment;
}
