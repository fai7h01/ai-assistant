package com.assistant.bootstrap;

import com.assistant.service.DataLoadingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataLoading implements CommandLineRunner {

    private final DataLoadingService dataLoadingService;

    public DataLoading(DataLoadingService dataLoadingService) {
        this.dataLoadingService = dataLoadingService;
    }

    @Override
    public void run(String... args) throws Exception {
        dataLoadingService.load();
        log.info("Data is loaded successfully");
    }
}
