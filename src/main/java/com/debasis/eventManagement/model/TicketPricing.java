package com.debasis.eventManagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketPricing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  Double basicPrice;
    private Double standardPrice;
    private Double premiumPrice;

}
