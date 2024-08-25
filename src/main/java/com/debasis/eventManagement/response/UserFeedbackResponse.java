package com.debasis.eventManagement.response;

import com.debasis.eventManagement.request.FeedbackDTO;
import com.debasis.eventManagement.request.UserFeedbackDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserFeedbackResponse {
    private int responseStatus;
    private String responseMessage;
    private UserFeedbackDTO feedback;
    private List<UserFeedbackDTO> feedbackDTOList;
}
