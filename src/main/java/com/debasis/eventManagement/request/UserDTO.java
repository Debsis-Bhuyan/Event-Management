package com.debasis.eventManagement.request;

import com.debasis.eventManagement.model.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String fullName;
    private String email;
    private Date createdAt;
    private String authProvider;
    private String password;
}
