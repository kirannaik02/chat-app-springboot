package com.chat.service.impl;

import com.chat.dto.MessageRequestDTO;
import com.chat.dto.MessageResponseDTO;
import com.chat.entity.Message;
import com.chat.repository.MessageRepository;
import com.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger logger = Logger.getLogger(MessageServiceImpl.class.getName());

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendMessage(MessageRequestDTO messageDTO) {
        if (messageDTO.getSenderId() == null || messageDTO.getContent() == null) {
            logger.warning("Sender ID or message content is missing.");
            return;
        }

        // Convert DTO to Entity
        Message message = new Message();
        message.setSenderId(messageDTO.getSenderId());
        message.setRecipientId(messageDTO.getRecipientId());
        message.setGroupId(messageDTO.getGroupId());
        message.setContent(messageDTO.getContent());
        message.setTimestamp(LocalDateTime.now());

        // Save message to DB
        messageRepository.save(message);

        // Prepare response DTO
        MessageResponseDTO response = new MessageResponseDTO();
        response.setSenderId(messageDTO.getSenderId());
        response.setContent(messageDTO.getContent());
        response.setGroupId(messageDTO.getGroupId());
        response.setTimestamp(message.getTimestamp());

        if (messageDTO.getGroupId() != null && messageDTO.getGroupId() > 0) {
            messagingTemplate.convertAndSend("/group/" + messageDTO.getGroupId(), response);
            logger.info("Group message sent to groupId: " + messageDTO.getGroupId());
        } else if (messageDTO.getRecipientId() != null) {
            // Use convertAndSend with manual path instead of convertAndSendToUser
            messagingTemplate.convertAndSend("/user/" + messageDTO.getRecipientId() + "/queue/messages", response);
            logger.info("Private message sent to userId: " + messageDTO.getRecipientId());
        } else {
            logger.warning("No recipientId or groupId provided. Message not delivered.");
        }
    }
}
