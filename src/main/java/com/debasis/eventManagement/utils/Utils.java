package com.debasis.eventManagement.utils;

import com.debasis.eventManagement.model.Event;
import com.debasis.eventManagement.model.TicketPricing;
import com.debasis.eventManagement.model.User;
import com.debasis.eventManagement.request.EventDTO;
import com.debasis.eventManagement.request.TicketPricingDTO;
import com.debasis.eventManagement.request.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Utils {

    private static PasswordEncoder passwordEncoder;

    @Autowired
    void getPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        passwordEncoder = bCryptPasswordEncoder;
    }
    public final static String DEFAULT_PASSWORD = "deba120";

    public static UserDTO mapUserEntityToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setEmail(user.getEmail());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setAuthProvider(user.getAuthProvider());
        userDTO.setFullName(user.getFullName());

        return  userDTO;
    }

    public static User mapUserDTOToUserEntity(UserDTO user) {
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setAuthProvider(user.getAuthProvider());
        newUser.setFullName(user.getFullName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return  newUser;
    }

    public static List<UserDTO> mapUserListEntityToUserListDTO(List<User> userList) {
        return userList.stream().map(Utils::mapUserEntityToUserDTO).collect(Collectors.toList());
    }
    public static EventDTO mapEventEntityToEventDTO(Event event){
        EventDTO eventDTO = new EventDTO();
        UserDTO userDTO =mapUserEntityToUserDTO(event.getOrganizer());
        eventDTO.setTitle(event.getTitle());
        eventDTO.setDescription(event.getDescription());
        eventDTO.setLocation(event.getLocation());
        eventDTO.setStartTime(event.getStartTime());
        eventDTO.setEndTime(event.getEndTime());
        eventDTO.setCapacity(event.getCapacity());
        eventDTO.setTicketPricing( mapTicketPricingEntityToTicketPricingDTO(event.getTicketPricing()));
eventDTO.setOrganizer(userDTO);
        return eventDTO;
    }
    public static Event mapEventDTOToEvent(EventDTO eventDTO){
        Event event = new Event();
        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setLocation(eventDTO.getLocation());
        event.setStartTime(eventDTO.getStartTime());
        event.setEndTime(eventDTO.getEndTime());
        event.setCapacity(eventDTO.getCapacity());
        event.setTicketPricing(mapTicketPricingDTOToTicketPricingEntity(eventDTO.getTicketPricing()));

        return event;
    }

    public static TicketPricingDTO mapTicketPricingEntityToTicketPricingDTO(TicketPricing ticketPricing){
        TicketPricingDTO ticketPricingDTO = new TicketPricingDTO();
        ticketPricingDTO.setBasicPrice(ticketPricing.getBasicPrice());
        ticketPricingDTO.setPremiumPrice(ticketPricing.getPremiumPrice());
        ticketPricingDTO.setStandardPrice(ticketPricing.getStandardPrice());

        return ticketPricingDTO;
    }
    public static TicketPricing mapTicketPricingDTOToTicketPricingEntity(TicketPricingDTO ticketPricingDTO){
        TicketPricing ticketPricing = new TicketPricing();
        ticketPricing.setBasicPrice(ticketPricingDTO.getBasicPrice());
        ticketPricing.setPremiumPrice(ticketPricingDTO.getPremiumPrice());
        ticketPricing.setStandardPrice(ticketPricingDTO.getStandardPrice());

        return ticketPricing;
    }


}
