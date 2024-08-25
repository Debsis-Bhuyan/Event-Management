package com.debasis.eventManagement.serviceImpl;


import com.debasis.eventManagement.exception.EventException;
import com.debasis.eventManagement.exception.UserException;
import com.debasis.eventManagement.model.Event;
import com.debasis.eventManagement.model.Payment;
import com.debasis.eventManagement.model.Ticket;
import com.debasis.eventManagement.model.User;
import com.debasis.eventManagement.repository.EventRepository;
import com.debasis.eventManagement.repository.PaymentRepository;
import com.debasis.eventManagement.repository.TicketRepository;
import com.debasis.eventManagement.repository.UserRepository;
import com.debasis.eventManagement.request.AttendeeDTO;
import com.debasis.eventManagement.request.EventDTO;
import com.debasis.eventManagement.request.TicketDTO;
import com.debasis.eventManagement.request.TicketSalesDTO;
import com.debasis.eventManagement.response.EventResponse;
import com.debasis.eventManagement.response.TicketResponse;
import com.debasis.eventManagement.service.TicketService;
import com.debasis.eventManagement.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {


    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    @Transactional
    public TicketResponse registerAttendee(Long eventId, Long userId, TicketDTO ticketDTO) {
        TicketResponse ticketResponse = new TicketResponse();
        try {
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new EventException("Event not found"));
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserException("User not found"));
            Ticket ticket = getTicket(ticketDTO, event, user);
            ticket=ticketRepository.save(ticket);

            ticketResponse.setStatusCode(201);
            ticketResponse.setMessage("Attendee registered successfully");
            ticketResponse.setTicketDTO(Utils.mapTicketEntityToTicketDTO(ticket));

        } catch (EventException | UserException e) {
            ticketResponse.setStatusCode(400);
            ticketResponse.setMessage("Registration failed: " + e.getMessage());
            ticketResponse.setTicketDTO(null);
        } catch (DataAccessException e) {
            ticketResponse.setStatusCode(500);
            ticketResponse.setMessage("Registration failed due to a database error: " + e.getMessage());
            ticketResponse.setTicketDTO(null);
        } catch (Exception e) {
            ticketResponse.setStatusCode(5);
            ticketResponse.setMessage("Registration failed due to an Server error: " + e.getMessage());
            ticketResponse.setTicketDTO(null);
        }

        return ticketResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendeeDTO> getAttendeeList(Long eventId) {
        List<Ticket> tickets = ticketRepository.findByEventEventId(eventId);

        return tickets.stream()
                .map(ticket -> new AttendeeDTO(
                        ticket.getUser().getId(),
                        ticket.getUser().getFullName(),
                        ticket.getUser().getEmail(),
                        ticket.getTicketType(),
                        ticket.getEvent().getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public TicketSalesDTO getTicketSales(Long eventId) {

        List<Ticket> tickets = ticketRepository.findByEventEventId(eventId);

        int totalTicketsSold = tickets.size();
        double totalPayment = tickets.stream()
                .mapToDouble(Ticket::getPrice)
                .sum();

        long basicTicketsSold = tickets.stream().filter(t -> t.getTicketType() == 1).count();
        long standardTicketsSold = tickets.stream().filter(t -> t.getTicketType() == 2).count();
        long premiumTicketsSold = tickets.stream().filter(t -> t.getTicketType() == 3).count();

        TicketSalesDTO ticketSalesDTO = new TicketSalesDTO();
        ticketSalesDTO.setTotalTicketsSold(totalTicketsSold);
        ticketSalesDTO.setTotalPayment(totalPayment);
        ticketSalesDTO.setBasicTicketsSold(basicTicketsSold);
        ticketSalesDTO.setStandardTicketsSold(standardTicketsSold);
        ticketSalesDTO.setPremiumTicketsSold(premiumTicketsSold);

        return ticketSalesDTO;
    }


    @Override
    public EventResponse getEventsByUserAsAttendee(Long userId) {
        EventResponse eventResponse = new EventResponse();
        List<Ticket> tickets = ticketRepository.findByUserId(userId);

        List<Event> eventDTOList = tickets.stream()
                .map(Ticket::getEvent).distinct().toList();
        eventResponse.setEvents(eventDTOList);
        eventResponse.setStatusCode(200);
        eventResponse.setMessage("Event data retrived Successfully");
        return eventResponse;
    }




    private static Ticket getTicket(TicketDTO ticketDTO, Event event, User user) {
        Payment payment = new Payment();
        payment.setPaymentType(ticketDTO.getPayment().getPaymentType());
        payment.setAmount(ticketDTO.getPayment().getAmount());
        payment.setPaymentStatus(ticketDTO.getPayment().getPaymentStatus());

        Ticket ticket = new Ticket();
        ticket.setEvent(event);
        ticket.setUser(user);

        ticket.setTicketType(ticketDTO.getTicketType());
        ticket.setPrice(ticketDTO.getPrice());
        ticket.setPayment(payment);
        return ticket;
    }


}
