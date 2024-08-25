package com.debasis.eventManagement.repository;

import com.debasis.eventManagement.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByEventId(Long eventId);
    List<Feedback> findByUserId(Long userId);
}
