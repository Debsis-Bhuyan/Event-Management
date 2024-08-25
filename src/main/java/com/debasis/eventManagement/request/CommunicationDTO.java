package com.debasis.eventManagement.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommunicationDTO {
    private String message;
    private String recipientName;
    private LocalDateTime sentAt;
    private  String title;
}
