package com.example.tseytwa.tinder.controller;

import com.example.tseytwa.tinder.model.Chat;
import com.example.tseytwa.tinder.model.ChatMessage;
import com.example.tseytwa.tinder.model.Profile;
import com.example.tseytwa.tinder.model.User;
import com.example.tseytwa.tinder.repository.UserRepository;
import com.example.tseytwa.tinder.service.ChatService;
import com.example.tseytwa.tinder.service.ProfileService;
import com.example.tseytwa.tinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    private final UserService userService;

    @Autowired
    public ChatController(ChatService chatService,
                          ProfileService profileService, UserService userService) {
        this.chatService = chatService;
        this.profileService = profileService;
        this.userService = userService;
    }

    @GetMapping
    public String chat(Model model, Authentication auth) {

        User user = userService.findByUsername(auth.getName()).get();

        Profile profile = profileService.findByUser(user);

        List<Chat> chats = chatService.getChatsByProfileId(profile.getId());

        model.addAttribute("profile", profile);
        model.addAttribute("chats", chats);
        //todo взяли профиль, добавляем в модель все переписки этого чела,
        // имя типа с кем общается
        // последнее сообщение и его дата
        return "chatsList";
    }

    @GetMapping("/{id}")
    public String chat(Model model, Authentication auth, @PathVariable int id) {
        Chat chat = chatService.findChatById(id);
        List<ChatMessage> messages = chatService.getChatMessagesBetweenUsers(chat.getUserId().getId(), chat.getUserWith().getId());

        User user = userService.findByUsername(auth.getName()).get();
        Profile myProfile = profileService.findByUser(user);

        model.addAttribute("chat", chat);
        model.addAttribute("myProfile", myProfile);
        model.addAttribute("messages", messages);
        return "chat";
    }
}
