package com.example.tseytwa.tinder.controller;

import com.example.tseytwa.tinder.model.Chat;
import com.example.tseytwa.tinder.service.ChatService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String chat(Model model){
        model.addAttribute("chat", chatService.findAllChats());
        return "chat";
    }
}
