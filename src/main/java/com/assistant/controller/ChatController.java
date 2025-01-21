package com.assistant.controller;

import com.assistant.dto.response.ResponseWrapper;
import com.assistant.service.CompanyService;
import com.assistant.service.UserService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/assistant")
public class ChatController {

    private final ChatClient chatClient;

    private final UserService userService;
    private final CompanyService companyService;

    public ChatController(ChatClient chatClient, UserService userService, CompanyService companyService) {
        this.chatClient = chatClient;
        this.userService = userService;
        this.companyService = companyService;
    }


    @GetMapping
    public ResponseEntity<ResponseWrapper> chat(@RequestParam(value = "q") String userMessage) {
        String content = chatClient.prompt()
                .system(s -> s.params(Map.of(
                        "current_date", LocalDate.now().toString())))
                .user(userMessage)
                .advisors(a -> a
                        .param(CHAT_MEMORY_CONVERSATION_ID_KEY, 1)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
                .call().content();
        return ResponseEntity.ok(ResponseWrapper.builder()
                .code(HttpStatus.OK.value())
                .success(true)
                .message("Assistant response retrieved successfully.")
                .data(content)
                .build());
    }
}
