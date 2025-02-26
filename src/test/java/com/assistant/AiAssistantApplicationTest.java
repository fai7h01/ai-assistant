package com.assistant;

import com.assistant.service.impl.DataLoadingServiceImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AiAssistantApplicationTest {

    private static final Logger logger = LoggerFactory.getLogger(AiAssistantApplicationTest.class);

    @Autowired
    private ChatModel chatModel;

    @Autowired
    private DataLoadingServiceImpl dataLoadingServiceImpl;

    @Autowired
    private VectorStore vectorStore;

    @Test
    void tellMeJoke() {
        ChatClient chatClient = ChatClient.create(chatModel);

        var response = chatClient.prompt()
                .user("Tell me a joke")
                .call()
                .content();

        logger.info("\n\n>> Response: {} \n\n", response);

    }

    @Test
    void tellMeJokeSystemPrompt() {

        ChatClient chatClient = ChatClient.builder(chatModel)
                .defaultSystem("You are a friendly assistant that answers question in the voice of {voice}.")
                .build();

        for (String voice : List.of("Pirate", "Yoda", "Shakespeare", "Robot")) {

            var response = chatClient.prompt()
                    .user("Tell me a joke")
                    .system(sp -> sp.param("voice", voice))
                    .call()
                    .content();

            logger.info("\n\n>>  {} joke: {} \n\n", voice, response);
        }
    }


    /////////////////////
    // Structured output
    /////////////////////
    record ActorFilms(
            String actor,
            List<String> films) {
    }

    @Test
    void structuredOutput() {

        ChatClient chatClient = ChatClient.create(chatModel);

        ActorFilms response = chatClient.prompt()
                .user("Get the filmography for Tom Hanks")
                .call()
                .entity(ActorFilms.class);

        logger.info("\n\n>>  Response: {} \n\n", response);
    }

    /////////////////////
    // Prompt stuffing
    /////////////////////
    @Test
    void noContext() {
        var response = ChatClient.builder(chatModel).build()
                .prompt()
                .user("What is Carina?")
                .call()
                .content();

        logger.info("\n\n>> Response: {} \n\n", response);
    }

    @Test
    void stuffPrompt() {
        String context = """
				What is Carina?
				Founded in 2016, Carina is a technology nonprofit that provides a safe, easy-to-use, online
				location-based care matching service. We serve individuals and families searching for home
				care or child care and care professionals who are looking for good jobs. Carina is committed to
				building community and prioritizing people over profit. Through our partnerships with unions
				and social service agencies, we build online tools to bring good jobs to care workers, so they
				can focus on their passion — caring for others.
				Our vision is a care economy that strengthens our communities by respecting and supporting
				workers, individuals and families. We offer a care matching platform where verified care
				providers can connect with individuals and families who need care.
				""";

        var response = ChatClient.builder(chatModel).build()
                .prompt()
                .user("What is Carina? Please consider the following context when answering the question: "
                        + context)
                .call()
                .content();

        logger.info("\n\n>> Response: {} \n\n", response);
    }

    /////////////////////
    // RAG
    /////////////////////
    @Test
    void preLoadData() {
        dataLoadingServiceImpl.load();
    }

    @Test
    void purposeQuestion() {

        String userText = "What is features of InvoiceHub?";

        var response = ChatClient.builder(chatModel)
                .build().prompt()
                .advisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.builder().build()))
                .user(userText)
                .call()
                .chatResponse();

       // logger.info("\n\n>> Response: {} \n\n", response.getResult().getOutput().getContent());

    }


    /////////////////////
    // Chat Memory
    /////////////////////
    @Test
    void conversationMemory() {

        var chatClient = ChatClient.builder(chatModel)
                //.defaultAdvisors(new PromptChatMemoryAdvisor(new InMemoryChatMemory()))
                // .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                 //.defaultAdvisors(new VectorStoreChatMemoryAdvisor(vectorStore))
                .build();

        var response = chatClient.prompt()
                .user("My name is John")
                .call()
                .content();

        logger.info("\n\n>> Response 1: {} \n\n", response);

        response = chatClient.prompt()
                .user("Please tell me, what is my name?")
                .call()
                .content();

        logger.info("\n\n>> Response 2: {} \n\n", response);
    }

    /////////////////////
    // Multi-modality
    /////////////////////
    @Test
    void multiModality() {

        String response = ChatClient.create(chatModel).prompt()
                .user(u -> u.text("Explain what do you see on this picture?")
                        .media(MimeTypeUtils.IMAGE_JPEG, new ClassPathResource("/data/test.jpeg")))
                .call()
                .content();

        logger.info("\n\n>> Response: {} \n\n", response);
    }
}
