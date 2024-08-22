package com.debasis.eventManagement.service;

import com.debasis.eventManagement.model.Event;
import com.debasis.eventManagement.request.EventDTO;
import com.debasis.eventManagement.response.EventResponse;

import java.util.List;

public interface EventService {

    EventResponse createEvent(EventDTO event, Long userId);

//    List<Event> findEventsByOrganizer(Long userId);

    EventResponse findEventById(Long eventId);

    EventResponse deleteEventById(Long userId, Long eventId);

    EventResponse updateById(Long userId,Long eventId, EventDTO eventDetails);

    List<Event> findAllEvents();

    EventResponse getAllUserEvents(Long userId);
}

