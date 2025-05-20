package com.example.tseytwa.tinder.service;

import com.example.tseytwa.tinder.model.Chat;
import com.example.tseytwa.tinder.model.ChatMessage;
import com.example.tseytwa.tinder.model.Profile;
import com.example.tseytwa.tinder.repository.ChatMessageRepository;
import com.example.tseytwa.tinder.repository.ChatRepository;
import com.example.tseytwa.tinder.repository.ProfileRepository;
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


    public List<ChatMessage> getChatMessagesBetweenUsers(int currentUserId, int otherUserId) {

        System.out.println(currentUserId);
        System.out.println(otherUserId);
        Chat chat = chatRepository.findChatBetweenUsers(currentUserId, otherUserId)
                .get();
        System.out.println("chatid "+chat.getId());

        return chatMessageRepository.findAllByChatIdOrderByTimestampAsc(chat.getId());
    }

    public List<Chat> getChatsByProfileId(Integer id) {
        System.out.println(id);
        return chatRepository.findChatsByProfileId(id);
    }

    public Chat findChatById(int id) {
        return chatRepository.findById(id).get();
    }

    public void postMessage(ChatMessage message) {
        chatMessageRepository.save(message);
    }
}
