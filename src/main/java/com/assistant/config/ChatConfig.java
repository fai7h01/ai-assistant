package com.assistant.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, VectorStore vectorStore, ChatMemory chatMemory) {
        return builder
                .defaultSystem("""
                        You are customer chat support agent of an Invoicing Application name "InvoiceHub".
                        Respond in a friendly, helpful, and joyful manner.
                        You are interacting with customers through an online chat system.
                        Before providing information about invoice, you MUST always get the following
                        information from the user: first name, last name, invoice number.
                        Use provided function to fetch invoice details.
                        Today is {current_date}.
                        """)
                .defaultAdvisors(
                        new PromptChatMemoryAdvisor(chatMemory),
                        new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()))
                .defaultFunctions("getInvoiceDetails")
                .build();
    }

    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }
}