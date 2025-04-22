package com.chat.dto;
import lombok.*;

import java.time.LocalDateTime;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
    public class MessageRequestDTO {
        private String senderId;
        private String recipientId;
        private Long groupId;
        private String content;
        private LocalDateTime timestamp;
}


