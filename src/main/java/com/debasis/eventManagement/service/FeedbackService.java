package com.debasis.eventManagement.service;

import com.debasis.eventManagement.request.FeedbackDTO;
import com.debasis.eventManagement.request.FeedbackMessageDTO;
import com.debasis.eventManagement.response.FeedbackResponse;
import com.debasis.eventManagement.response.UserFeedbackResponse;

import java.util.List;

public interface FeedbackService {

    FeedbackResponse submitFeedback(Long userId, Long eventId, FeedbackMessageDTO feedbackMessageDTO);
    FeedbackResponse getFeedbacksByEventId(Long eventId);
    FeedbackResponse getFeedbacksByUserId(Long userId);


    }
