package com.chat.service;

import com.chat.dto.MessageRequestDTO;

public interface MessageService {
    void sendMessage(MessageRequestDTO messageDTO);
}
