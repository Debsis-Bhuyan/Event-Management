package com.debasis.eventManagement.serviceImpl;



import com.debasis.eventManagement.model.Feedback;
import com.debasis.eventManagement.model.Event;
import com.debasis.eventManagement.model.User;
import com.debasis.eventManagement.model.UserFeedback;
import com.debasis.eventManagement.repository.FeedbackRepository;
import com.debasis.eventManagement.repository.EventRepository;
import com.debasis.eventManagement.repository.UserFeedbackRepository;
import com.debasis.eventManagement.repository.UserRepository;
import com.debasis.eventManagement.request.FeedbackDTO;
import com.debasis.eventManagement.request.FeedbackMessageDTO;
import com.debasis.eventManagement.request.UserFeedbackDTO;
import com.debasis.eventManagement.response.FeedbackResponse;
import com.debasis.eventManagement.response.UserFeedbackResponse;
import com.debasis.eventManagement.service.FeedbackService;
import com.debasis.eventManagement.service.UserFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserFeedBackServiceImpl implements UserFeedbackService {

    @Autowired
    private UserFeedbackRepository feedbackRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserFeedbackResponse createFeedback(Long userId, Long eventId, FeedbackMessageDTO feedbackMessageDTO) {
        UserFeedbackResponse feedbackResponse = new UserFeedbackResponse();

        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new RuntimeException("Event not found"));

            UserFeedback feedback = new UserFeedback();

            feedback.setUser(user);
            feedback.setEvent(event);
            feedback.setMessage(feedbackMessageDTO.getMessage());
            feedback.setCreatedAt(LocalDateTime.now());

            feedback = feedbackRepository.save(feedback);
            UserFeedbackDTO userFeedbackDTO = new UserFeedbackDTO(feedback.getUser().getId(), feedback.getEvent().getEventId(), feedback.getMessage(), feedback.getUser().getFullName(), feedback.getCreatedAt());

            feedbackResponse.setResponseStatus(201);
            feedbackResponse.setResponseMessage("Feedback submitted successfully");
            feedbackResponse.setFeedback(userFeedbackDTO);

        } catch (Exception e) {
            feedbackResponse.setResponseStatus(500);
            feedbackResponse.setResponseMessage("Failed to submit feedback: " + e.getMessage());
            feedbackResponse.setFeedback(null);
        }

        return feedbackResponse;
    }

    @Override
    public UserFeedbackResponse getFeedbackByEventId(Long eventId) {
        UserFeedbackResponse feedbackResponse = new UserFeedbackResponse();
        List<UserFeedback> feedbackList = feedbackRepository.findByEventEventId(eventId);

        List<UserFeedbackDTO>  feedbackDTOList= feedbackList.stream()
                .map(feedback -> new UserFeedbackDTO(
                        feedback.getUser().getId(),
                        feedback.getEvent().getEventId(),
                        feedback.getMessage(),
                        feedback.getUser().getFullName(),
                        feedback.getCreatedAt()
                ))
                .toList();

        feedbackResponse.setFeedbackDTOList(feedbackDTOList);
        feedbackResponse.setResponseStatus(200);
        feedbackResponse.setResponseMessage("Feedback retrieved successfully ");
        return  feedbackResponse;

    }
}
