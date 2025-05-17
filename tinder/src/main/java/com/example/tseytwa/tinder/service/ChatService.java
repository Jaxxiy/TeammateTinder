package com.example.tseytwa.tinder.service;

import com.example.tseytwa.tinder.model.Chat;
import com.example.tseytwa.tinder.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public List<Chat> findAllChats() {
        return chatRepository.findAll();
    }
}
