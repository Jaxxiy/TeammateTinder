package com.example.tseytwa.tinder.repository;

import com.example.tseytwa.tinder.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
    List<Chat> findAllByUserId(Integer userId);
}
