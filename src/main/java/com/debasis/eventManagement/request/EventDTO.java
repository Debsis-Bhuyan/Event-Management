package com.debasis.eventManagement.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {

    private String title;
    private String description;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer capacity;

    private UserDTO organizer;
    private TicketPricingDTO ticketPricing;
}
