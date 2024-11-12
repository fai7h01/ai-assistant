package com.assistant;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AiAssistantApplicationTest {

    private static final Logger logger = LoggerFactory.getLogger(AiAssistantApplicationTest.class);

    @Autowired
    ChatModel chatModel;

    @Autowired
    VectorStore vectorStore;

    @Test
    void tellMeJoke() {
        ChatClient chatClient = ChatClient.create(chatModel);

        var response = chatClient.prompt()
                .user("Tell me a joke")
                .call()
                .content();

        logger.info("\n\n>> Response: {} \n\n", response);

    }
}
