package com.example.tseytwa.tinder.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "chat")
@Data
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Profile user_id;
    @ManyToOne
    @JoinColumn(name = "user_with")
    private Profile user_with;
    @OneToMany(mappedBy = "chat",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ChatMessage> messages;
}
