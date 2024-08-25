package com.debasis.eventManagement.service;

import com.debasis.eventManagement.request.FeedbackMessageDTO;
import com.debasis.eventManagement.request.UserFeedbackDTO;
import com.debasis.eventManagement.response.UserFeedbackResponse;

import java.util.List;

public interface UserFeedbackService {
    UserFeedbackResponse createFeedback(Long userId, Long eventId ,FeedbackMessageDTO feedbackDTO);
    UserFeedbackResponse getFeedbackByEventId(Long eventId);
}
