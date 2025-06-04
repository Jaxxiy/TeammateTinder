package com.example.tseytwa.tinder.repository;

import com.example.tseytwa.tinder.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
    @Query("SELECT c FROM chat c WHERE (c.userId.id = :user1Id AND c.userWith.id = :user2Id) OR (c.userWith.id = :user1Id AND c.userId.id = :user2Id)")
    Optional<Chat> findChatBetweenUsers(@Param("user1Id") int user1Id, @Param("user2Id") int user2Id);

    @Query("SELECT c FROM chat c WHERE (c.userId.id = :id1 OR c.userWith.id = :id1)")
    List<Chat> findChatsByProfileId(@Param("id1") Integer id1);
}
