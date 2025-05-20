package com.example.tseytwa.tinder.model;

import jakarta.persistence.*;


import java.util.List;

@Entity(name = "chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Profile userId;
    @ManyToOne
    @JoinColumn(name = "user_with")
    private Profile userWith;
    @OneToMany(mappedBy = "chat",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ChatMessage> messages;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Profile getUserId() {
        return userId;
    }

    public void setUserId(Profile userId) {
        this.userId = userId;
    }

    public Profile getUserWith() {
        return userWith;
    }

    public void setUserWith(Profile userWith) {
        this.userWith = userWith;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", userId=" + userId +
                ", userWith=" + userWith +
                '}';
    }
}
