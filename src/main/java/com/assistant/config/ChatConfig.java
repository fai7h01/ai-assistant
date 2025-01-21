package com.assistant.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
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
                        You can get specific invoice details, approve invoice, send invoices via mail to client.
                        Before providing information about invoice, you MUST always get the following
                        information from the user: invoice number.
                        Use provided function to fetch invoice details.
                        Before approving invoices, you MUST confirm the following information from the user, ONLY if user did not confirm before::
                        invoice number.
                        Use provided function to approve invoice.
                        Before sending invoice pdf via email to client, you MUST confirm the following information from the user, ONLY if user did not confirm before:
                        invoice number.
                        Use provided function to send invoice pdf via email to client.
                        You can get approved invoices, which is considered as a SALES.
                        If user asks to analyze sales, you MUST use provided function to get all the approved invoices.
                        If user asks to analyze sales, you SHOULD NOT ASK invoice number, because are getting all the approved invoices at one shot.
                        Based on approved invoices you should analyze following data:
                        Which client is more profitable for company, you must determine, frequency of clients in invoices and based on that give proper analyze to users.
                        Use provided function to analyze data from invoices.
                        You can also take user input as a different format like pdf, word file.
                        Today is {current_date}.
                        """)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory),
                        new PromptChatMemoryAdvisor(chatMemory),
                        new QuestionAnswerAdvisor(vectorStore, SearchRequest.builder().topK(3).similarityThreshold(0.75).build()))
                .defaultFunctions("getInvoiceDetails", "approveInvoice", "sendInvoiceViaMail", "analyzeSales")
                .build();
    }

    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }
}
