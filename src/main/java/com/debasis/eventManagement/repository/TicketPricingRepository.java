package com.debasis.eventManagement.repository;

import com.debasis.eventManagement.model.TicketPricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketPricingRepository extends JpaRepository<TicketPricing, Long> {
}
