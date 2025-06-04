package com.example.tseytwa.tinder.repository;

import com.example.tseytwa.tinder.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    List<ChatMessage> findAllByChatIdOrderByTimestampAsc(int chatId);
}
