package com.example.tseytwa.tinder.controller;

import com.example.tseytwa.tinder.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public String chat(Model model) {
        model.addAttribute("chats", chatService.findAll());
        return "chatsList";
    }

    @GetMapping("/{id}")
    public String chat(Model model,@PathVariable int id) {
        model.addAttribute("messages", chatService.findAllMessagesByUserId(id));
        return "chat";
    }
}
