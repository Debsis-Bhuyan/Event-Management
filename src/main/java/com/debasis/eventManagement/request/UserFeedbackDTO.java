package com.debasis.eventManagement.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFeedbackDTO {
    private Integer userId;
    private Long eventId;
    private String message;
    private String userName;
    private LocalDateTime createdAt;
}
