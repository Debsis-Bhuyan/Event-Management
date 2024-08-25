package com.debasis.eventManagement.serviceImpl;

import com.debasis.eventManagement.model.Feedback;
import com.debasis.eventManagement.repository.FeedbackRepository;
import com.debasis.eventManagement.request.FeedbackDTO;
import com.debasis.eventManagement.request.FeedbackMessageDTO;
import com.debasis.eventManagement.response.FeedbackResponse;
import com.debasis.eventManagement.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public FeedbackResponse submitFeedback(Long userId, Long eventId, FeedbackMessageDTO feedbackDTO) {
        FeedbackResponse feedbackResponse = new FeedbackResponse();
        System.out.println(feedbackDTO.getMessage());
        try {

            Feedback feedback = new Feedback();
            feedback.setUserId(userId);
            feedback.setEventId(eventId);
            feedback.setMessage(feedbackDTO.getMessage());

            feedback = feedbackRepository.save(feedback);

            FeedbackDTO responseDTO = new FeedbackDTO(
                    feedback.getUserId(),
                    feedback.getEventId(),
                    feedback.getMessage(),
                    feedback.getCreatedAt()
            );
            feedbackResponse.setFeedback(responseDTO);
            feedbackResponse.setResponseStatus(200);
            feedbackResponse.setResponseMessage("Message sent Successfully");

        } catch (IllegalArgumentException e) {
            feedbackResponse.setResponseStatus(400);
            feedbackResponse.setResponseMessage( "FAILUREInvalid input: " + e.getMessage());
        } catch (Exception e) {
            feedbackResponse.setResponseStatus(500);
            feedbackResponse.setResponseMessage("Server Error "+ e.getMessage());
        }
        return  feedbackResponse;
    }
    @Override
    public FeedbackResponse getFeedbacksByEventId(Long eventId) {
        FeedbackResponse response = new FeedbackResponse();
        try {
            List<FeedbackDTO> feedbackDTOList = feedbackRepository.findByEventId(eventId).stream()
                    .map(feedback -> new FeedbackDTO(
                            feedback.getUserId(),
                            feedback.getEventId(),
                            feedback.getMessage(),
                            feedback.getCreatedAt()

                    )).collect(Collectors.toList());

            response.setResponseStatus(HttpStatus.OK.value());
            response.setResponseMessage("Feedback retrieved successfully.");
            response.setFeedbackDTOList(feedbackDTOList);

        } catch (Exception e) {
            response.setResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setResponseMessage("Failed to retrieve feedback: " + e.getMessage());
        }
        return response;
    }

    @Override
    public FeedbackResponse getFeedbacksByUserId(Long userId) {
        FeedbackResponse response = new FeedbackResponse();
        try {
            List<FeedbackDTO> feedbackDTOList = feedbackRepository.findByUserId(userId).stream()
                    .map(feedback -> new FeedbackDTO(
                            feedback.getUserId(),
                            feedback.getEventId(),
                            feedback.getMessage(),
                            feedback.getCreatedAt()
                    )).collect(Collectors.toList());

            response.setResponseStatus(HttpStatus.OK.value());
            response.setResponseMessage("Feedback retrieved successfully.");
            response.setFeedbackDTOList(feedbackDTOList);

        } catch (Exception e) {
            response.setResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setResponseMessage("Failed to retrieve feedback: " + e.getMessage());
        }
        return response;
    }

}