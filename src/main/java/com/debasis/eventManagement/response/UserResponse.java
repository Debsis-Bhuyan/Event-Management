package com.debasis.eventManagement.response;

import com.debasis.eventManagement.model.AuthProvider;
import com.debasis.eventManagement.model.Event;
import com.debasis.eventManagement.request.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private int statusCode;

    private String message;

    private String token;
    private UserDTO user;
}
