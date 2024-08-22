package com.debasis.eventManagement.controller;

import com.debasis.eventManagement.model.Event;
import com.debasis.eventManagement.model.User;
import com.debasis.eventManagement.request.EventDTO;
import com.debasis.eventManagement.response.EventResponse;
import com.debasis.eventManagement.service.EventService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping( "/create-event")
    public ResponseEntity<EventResponse> createEvent(@RequestBody EventDTO event) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();
        EventResponse newEvent = eventService.createEvent(event, Long.valueOf(currentUser.getId()));        return  ResponseEntity.status(newEvent.getStatusCode()).body(newEvent);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Event>> getAllEvents(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");

        List<Event> eventList = eventService.findAllEvents();
        return ResponseEntity.status(HttpStatus.OK).body(eventList);
    }

    @GetMapping( "/get-events")
    public ResponseEntity<EventResponse> getAllEvents(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();
        EventResponse response = eventService.getAllUserEvents(Long.valueOf(currentUser.getId()));
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-event/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable Long id) {
        EventResponse event = eventService.findEventById(id);
        return ResponseEntity.status(event.getStatusCode()).body(event);
    }

    @PutMapping("/update/{eventId}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable Long userId,@PathVariable Long eventId, @RequestBody EventDTO eventDetails) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();
        EventResponse updatedEventResponse = eventService.updateById(Long.valueOf( currentUser.getId()), eventId, eventDetails);
        return ResponseEntity.status(updatedEventResponse.getStatusCode()).body(updatedEventResponse);
    }
    @DeleteMapping("/delete/{userId}/{eventId}")
    public ResponseEntity<EventResponse> DeleteEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        EventResponse deleteResponse = eventService.deleteEventById(  Long.valueOf(currentUser.getId()),  eventId);
        return ResponseEntity.status(deleteResponse.getStatusCode()).body(deleteResponse);
    }

}
