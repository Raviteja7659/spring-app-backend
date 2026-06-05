package com.example.demo;

import com.example.demo.service.ChatService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public Map<String, String> handleChat(@RequestBody Map<String, String> payload) {
        String response = chatService.getAIResponse(payload.get("message"));
        return Map.of("reply", response);
    }
}
