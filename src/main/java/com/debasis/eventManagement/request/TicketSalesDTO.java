package com.debasis.eventManagement.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketSalesDTO {
    private int totalTicketsSold;
    private double totalPayment;
    private long basicTicketsSold;
    private long standardTicketsSold;
    private long premiumTicketsSold;
}
