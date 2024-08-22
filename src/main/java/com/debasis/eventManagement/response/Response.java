package com.debasis.eventManagement.response;

import com.debasis.eventManagement.model.Event;
import com.debasis.eventManagement.request.UserDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Response {
    private Long id;
    private int statusCode;

    private String message;

    private String token;
    private UserDTO user;
    private List<Event> eventlist;

}
