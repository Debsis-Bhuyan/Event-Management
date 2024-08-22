package com.debasis.eventManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long eventId;

    private String title;
    private  String description;
    private String location;
    private LocalDateTime startTime;
    private  LocalDateTime endTime;
    private Integer capacity;

    @ManyToOne(targetEntity = User.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "organizer", nullable = false)
    private User organizer;

    @OneToOne(targetEntity = TicketPricing.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "pricing")
    private TicketPricing ticketPricing ;

}
