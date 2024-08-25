package com.debasis.eventManagement.repository;

import com.debasis.eventManagement.model.CommunicationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunicationRepository extends JpaRepository<CommunicationModel, Long> {
    List<CommunicationModel> findByEventEventIdAndSenderId(Long eventId, Long senderId);
    List<CommunicationModel> findByRecipientId(Long recipientId);

}
