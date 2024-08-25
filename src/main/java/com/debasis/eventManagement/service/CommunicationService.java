package com.debasis.eventManagement.service;

import com.debasis.eventManagement.request.CommunicationDTO;
import com.debasis.eventManagement.request.FeedbackMessageDTO;
import com.debasis.eventManagement.response.CommunicationResponse;

import java.util.List;

public interface CommunicationService {
        CommunicationResponse sendCommunication(Long eventId, Long senderId, Long recipientId, FeedbackMessageDTO feedbackMessageDTO);
        List<CommunicationDTO> getCommunications(Long eventId, Long senderId);
        List<CommunicationDTO> getCommunicationsByRecipientId(Long recipientId);

}
