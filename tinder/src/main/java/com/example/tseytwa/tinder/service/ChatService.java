package com.example.tseytwa.tinder.service;

import com.example.tseytwa.tinder.model.Chat;
import com.example.tseytwa.tinder.model.ChatMessage;
import com.example.tseytwa.tinder.repository.ChatMessageRepository;
import com.example.tseytwa.tinder.repository.ChatRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository,
                       ChatMessageRepository chatMessageRepository) {
        this.chatRepository = chatRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    public List<Chat> findAllByUserId(Integer userId) {
        return chatRepository.findAllByUserId(userId);
    }

    public List<Chat> findAll() {
        return chatRepository.findAll();
    }

    public List<ChatMessage> findAllMessagesByUserId(int id) {
        chatMessageRepository.findAllBy()
    }
}
