package com.assistant;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AiAssistantApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiAssistantApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ChatClient.Builder builder) {
        return (args) -> {
            ChatClient chatClient = builder.build();
        };
    }

}
