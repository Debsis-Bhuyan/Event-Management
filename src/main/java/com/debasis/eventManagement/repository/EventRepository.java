package com.debasis.eventManagement.repository;

import com.debasis.eventManagement.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event , Long> {
    public List<Event> findByEventIdAndOrganizer_id(Long eventId, Long Organizer_id);

    List<Event> findByOrganizerId(Long userId);
}
