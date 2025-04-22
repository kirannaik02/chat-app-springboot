package com.chat.controller;

import com.chat.dto.MessageRequestDTO;
import com.chat.dto.MessageResponseDTO;
import com.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageService messageService;

    // Endpoint for private message
    @MessageMapping("/private-message")
    public void sendPrivateMessage(MessageRequestDTO message) {
        MessageResponseDTO response = new MessageResponseDTO();
        response.setSenderId(message.getSenderId());
        response.setContent(message.getContent());
        response.setTimestamp(message.getTimestamp());

        // Send to a specific user for private message
        messagingTemplate.convertAndSend(
                "/user/" + message.getRecipientId() + "/queue/messages",
                response
        );

    }
    // Endpoint for group message
    @MessageMapping("/group-message")
    public void sendGroupMessage(MessageRequestDTO message) {
        MessageResponseDTO response = new MessageResponseDTO();
        response.setSenderId(message.getSenderId());
        response.setContent(message.getContent());
        response.setGroupId(message.getGroupId());
        response.setTimestamp(message.getTimestamp());

        // Send message to the group topic
        messagingTemplate.convertAndSend("/group/" + message.getGroupId(), response);
    }
}
