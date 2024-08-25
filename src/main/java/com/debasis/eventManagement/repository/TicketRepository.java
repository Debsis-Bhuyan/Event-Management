package com.debasis.eventManagement.repository;

import com.debasis.eventManagement.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByEventEventId(Long eventId);
    List<Ticket> findByUserId(Long userId);


}
