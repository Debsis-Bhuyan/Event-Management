package com.debasis.eventManagement.response;

import com.debasis.eventManagement.model.*;
import com.debasis.eventManagement.request.EventDTO;
import com.debasis.eventManagement.request.UserDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventResponse {

    private int statusCode;
    private String message;

    private EventDTO event;
    private List<Event> events;
}
