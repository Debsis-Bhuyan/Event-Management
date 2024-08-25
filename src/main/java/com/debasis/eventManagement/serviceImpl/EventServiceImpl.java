package com.debasis.eventManagement.serviceImpl;

import com.debasis.eventManagement.exception.EventException;
import com.debasis.eventManagement.exception.UserException;
import com.debasis.eventManagement.model.Event;
import com.debasis.eventManagement.model.TicketPricing;
import com.debasis.eventManagement.model.User;
import com.debasis.eventManagement.repository.EventRepository;
import com.debasis.eventManagement.repository.TicketPricingRepository;
import com.debasis.eventManagement.repository.UserRepository;
import com.debasis.eventManagement.request.EventDTO;
import com.debasis.eventManagement.response.EventResponse;
import com.debasis.eventManagement.service.EventService;
import com.debasis.eventManagement.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  TicketPricingRepository ticketPricingRepository;

    @Override
    public EventResponse createEvent(EventDTO event, Long userId) {
        EventResponse response = new EventResponse();
        try {
            Optional<User> organizer = userRepository.findById(userId);
            if (organizer.isPresent()){
                Event eventData =Utils.mapEventDTOToEvent(event);
                System.out.println("Hello "+eventData);
                eventData.setOrganizer(organizer.get());
                 eventData = eventRepository.save(eventData);
                EventDTO eventDTO = Utils.mapEventEntityToEventDTO(eventData);
                response.setEvent(eventDTO);
                response.setMessage("Event created Successfully");
                response.setStatusCode(201);
            }
            else {
                throw  new EventException("User not found");
            }

        }
        catch (EventException e){
            response.setMessage(e.getMessage());
            response.setStatusCode(400);
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Event Creation failed "+e.getMessage());
        }
        return response;
    }


    @Override
    public EventResponse getAllUserEvents(Long userId) {
        EventResponse response = new EventResponse();
        try {
            List<Event> events= eventRepository.findByOrganizerId(userId);

            response.setEvents(events);
            response.setStatusCode(200);
            response.setMessage("User data get Successfully");

        } catch (UserException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error getting  users Details " + e.getMessage());
        }
        return response;
    }

    @Override
    public EventResponse findEventById(Long eventId) {
        EventResponse response = new EventResponse();
        try {
            Optional<Event> eventOptional = eventRepository.findById(eventId);
            if (eventOptional.isPresent()){
                Event event = eventOptional.get();
                EventDTO eventDTO = Utils.mapEventEntityToEventDTO(event);
                response.setEvent(eventDTO);
                response.setMessage("Event retrieved Successfully");
                response.setStatusCode(200);
            }
            else {
                throw new EventException("Event not found with tis eventId-> "+eventId);
            }

        }
        catch (EventException e){
            response.setMessage(e.getMessage());
            response.setStatusCode(400);
        }
        catch (Exception e){
            response.setMessage("Server error "+e.getMessage());
            response.setStatusCode(500);
        }
        return  response;
    }

    @Override
    public EventResponse deleteEventById(Long userId, Long eventId) {
        EventResponse response = new EventResponse();
        try {
            Optional<User> user = userRepository.findById(userId);
            if (!user.isPresent()) {
                throw new UserException("User not found with ID: " + userId);
            }

            Optional<Event> optionalEvent = eventRepository.findById(eventId);
            if (!optionalEvent.isPresent()) {
                throw new EventException("Event not found with ID: " + eventId);
            }

            Event event = optionalEvent.get();


            event.setOrganizer(null);

            eventRepository.deleteById(event.getEventId());

            response.setStatusCode(200);
            response.setMessage("Event deleted successfully");

        } catch (UserException e) {
            response.setMessage(e.getMessage());
            response.setStatusCode(404);
        } catch (EventException e) {
            response.setMessage(e.getMessage());
            response.setStatusCode(400);
        } catch (Exception e) {
            response.setMessage("Event deletion failed: " + e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public EventResponse updateById(Long userId, Long eventId, EventDTO eventDetails) {
        EventResponse response = new EventResponse();

        try {
            Optional<User> userOptional = userRepository.findById(userId);
            if (!userOptional.isPresent()) {
                throw new UserException("User not found with ID: " + userId);
            }

            Optional<Event> eventOptional = eventRepository.findById(eventId);
            if (!eventOptional.isPresent()) {
                throw new EventException("Event not found with ID: " + eventId);
            }

            Event event = eventOptional.get();

            event.setTitle(eventDetails.getTitle());
            event.setDescription(eventDetails.getDescription());
            event.setLocation(eventDetails.getLocation());
            event.setStartTime(eventDetails.getStartTime());
            event.setEndTime(eventDetails.getEndTime());
            event.setCapacity(eventDetails.getCapacity());

            if (eventDetails.getTicketPricing() != null) {
                TicketPricing newPricing = Utils.mapTicketPricingDTOToTicketPricingEntity(eventDetails.getTicketPricing());
                event.setTicketPricing(ticketPricingRepository.save(newPricing));
            }

            event = eventRepository.save(event);

            response.setEvent(Utils.mapEventEntityToEventDTO(event));
            response.setMessage("Event updated successfully");
            response.setStatusCode(200);

        } catch (UserException e) {
            response.setMessage(e.getMessage());
            response.setStatusCode(404);
        } catch (EventException e) {
            response.setMessage(e.getMessage());
            response.setStatusCode(400);
        } catch (Exception e) {
            response.setMessage("Event update failed: " + e.getMessage());
            response.setStatusCode(500);
        }

        return response;
    }

    @Override
    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }


}
