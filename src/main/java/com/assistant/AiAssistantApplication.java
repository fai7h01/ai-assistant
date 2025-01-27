package com.assistant;

import com.assistant.service.DataLoadingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@Slf4j
@EnableFeignClients
@SpringBootApplication
@Profile("prod")
public class AiAssistantApplication {

    public AiAssistantApplication(DataLoadingService dataLoadingService) {
        this.dataLoadingService = dataLoadingService;
    }

    public static void main(String[] args) {
        SpringApplication.run(AiAssistantApplication.class, args);
    }

    private final DataLoadingService dataLoadingService;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> dataLoadingService.load();
    }

}
