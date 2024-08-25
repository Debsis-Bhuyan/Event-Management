package com.debasis.eventManagement.controller;



import com.debasis.eventManagement.model.User;
import com.debasis.eventManagement.request.FeedbackMessageDTO;
import com.debasis.eventManagement.response.FeedbackResponse;
import com.debasis.eventManagement.response.UserFeedbackResponse;
import com.debasis.eventManagement.service.FeedbackService;
import com.debasis.eventManagement.service.UserFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/userfeedback")
public class UserFeedbackController {

    @Autowired
    private UserFeedbackService feedbackService;

    @PostMapping("/create/{eventId}")
    public ResponseEntity<UserFeedbackResponse> createFeedback(@RequestBody FeedbackMessageDTO feedbackDTO, @PathVariable Long eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        UserFeedbackResponse response = feedbackService.createFeedback(Long.valueOf(currentUser.getId()) ,eventId,feedbackDTO);
        return ResponseEntity.status(response.getResponseStatus()).body(response);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<UserFeedbackResponse> getFeedbackByEventId(@PathVariable Long eventId) {
        UserFeedbackResponse response = feedbackService.getFeedbackByEventId(eventId);
        return ResponseEntity.status(response.getResponseStatus()).body(response);
    }
}
