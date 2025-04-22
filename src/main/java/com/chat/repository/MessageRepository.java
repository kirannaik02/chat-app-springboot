package com.chat.repository;

import com.chat.entity.Message;  // Import the Message entity
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {  // Don't use generic type <Message> here
    List<Message> findBySenderIdAndRecipientId(String senderId, String recipientId);
}
