package com.debasis.eventManagement.service;

import com.debasis.eventManagement.request.AttendeeDTO;
import com.debasis.eventManagement.request.EventDTO;
import com.debasis.eventManagement.request.TicketDTO;
import com.debasis.eventManagement.request.TicketSalesDTO;
import com.debasis.eventManagement.response.EventResponse;
import com.debasis.eventManagement.response.TicketResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TicketService {

    TicketResponse registerAttendee(Long eventId, Long userId, TicketDTO ticketDTO);

    List<AttendeeDTO> getAttendeeList(Long eventId);

    TicketSalesDTO getTicketSales(Long eventId);

    EventResponse getEventsByUserAsAttendee(Long userId);

}
