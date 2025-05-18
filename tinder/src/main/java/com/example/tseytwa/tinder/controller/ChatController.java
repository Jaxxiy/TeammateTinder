package com.example.tseytwa.tinder.controller;

import com.example.tseytwa.tinder.model.ChatMessage;
import com.example.tseytwa.tinder.model.Profile;
import com.example.tseytwa.tinder.service.ChatService;
import com.example.tseytwa.tinder.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final ProfileService profileService;

    @Autowired
    public ChatController(ChatService chatService,
                          ProfileService profileService) {
        this.chatService = chatService;
        this.profileService = profileService;
    }

    @GetMapping
    public String chat(Model model) {
        model.addAttribute("chats", chatService.findAll());
        return "chatsList";
    }

    @GetMapping("/messages")
    public String chat(Model model,
                       @RequestParam int currentUserId,
                       @RequestParam int otherUserId) {
        List<ChatMessage> messages = chatService.getChatMessagesBetweenUsers(currentUserId, otherUserId);
        Profile otherUser = profileService.findById(otherUserId);

        System.out.println(currentUserId);
        System.out.println(otherUser.getId());
        System.out.println(otherUser);
        System.out.println(messages);
        for (ChatMessage message : messages) {
            System.out.println(message+" "+currentUserId+ " "+message.getAuthor().getId());
        }
        model.addAttribute("currentUserId", currentUserId);
        model.addAttribute("messages", messages);
        model.addAttribute("otherUser", otherUser);
        return "chat";
    }
}
