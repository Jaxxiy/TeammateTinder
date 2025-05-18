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
    private final ProfileRepository profileRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository,
                       ChatMessageRepository chatMessageRepository,
                       ProfileRepository profileRepository) {
        this.chatRepository = chatRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.profileRepository = profileRepository;
    }

    //public List<Chat> findAllByUserId(Integer userId) {
      //  return chatRepository.findAllByUserId(userId);
    //}

    public List<Chat> findAll() {
        return chatRepository.findAll();
    }

    public List<ChatMessage> getChatMessagesBetweenUsers(int currentUserId, int otherUserId) {

        System.out.println(currentUserId);
        System.out.println(otherUserId);
        Chat chat = chatRepository.findChatBetweenUsers(currentUserId, otherUserId)
                .get();
        System.out.println("chatid "+chat.getId());

        return chatMessageRepository.findAllByChatIdOrderByTimestampAsc(chat.getId());
    }
}
