package com.debasis.eventManagement.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class AttendeeDTO {
        private  int userId;
        private String fullName;
        private String email;
        private Integer ticketType;
        private String eventName;

}
