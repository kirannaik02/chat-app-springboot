package com.chat.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageResponseDTO {
    private String senderId;
    private String content;
    private Long groupId;
    private LocalDateTime timestamp;
}

