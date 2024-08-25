package com.debasis.eventManagement.controller;

import com.debasis.eventManagement.model.User;
import com.debasis.eventManagement.request.CommunicationDTO;
import com.debasis.eventManagement.request.FeedbackMessageDTO;
import com.debasis.eventManagement.response.CommunicationResponse;
import com.debasis.eventManagement.service.CommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class CommunicationController {

    @Autowired
    private CommunicationService communicationService;

    @PostMapping("/{eventId}/communicate")
    public ResponseEntity<CommunicationResponse> sendCommunication(@PathVariable Long eventId,
                                                                   @RequestParam Long recipientId,
                                                                   @RequestBody FeedbackMessageDTO feedbackMessageDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();
        CommunicationResponse response = communicationService.sendCommunication(eventId, Long.valueOf(currentUser.getId()), recipientId, feedbackMessageDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{eventId}/communications/{senderId}")
    public ResponseEntity<List<CommunicationDTO>> getCommunications(@PathVariable Long eventId,
                                                                    @PathVariable Long senderId) {
        List<CommunicationDTO> communications = communicationService.getCommunications(eventId, senderId);
        return ResponseEntity.ok(communications);
    }

    @GetMapping("/recipient")
    public ResponseEntity<List<CommunicationDTO>> getCommunicationsByRecipientId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();
        try {
            List<CommunicationDTO> communications = communicationService.getCommunicationsByRecipientId(Long.valueOf(currentUser.getId()));
            return ResponseEntity.ok(communications);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
