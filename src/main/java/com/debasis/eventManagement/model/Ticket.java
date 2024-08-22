package com.debasis.eventManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    private  Integer  ticketType;
    private  Double price;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;

}
