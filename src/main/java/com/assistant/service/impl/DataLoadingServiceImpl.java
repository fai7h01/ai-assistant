package com.assistant.service.impl;

import com.assistant.service.DataLoadingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.sun.imageio.plugins.jpeg.JPEG.version;

@Service
public class DataLoadingServiceImpl implements DataLoadingService {

    private static final Logger logger = LoggerFactory.getLogger(DataLoadingServiceImpl.class);

    @Value("classpath:/data/InvoiceHub Documentation.pdf")
    private Resource resource;

    private final VectorStore vectorStore;

    public DataLoadingServiceImpl(VectorStore vectorStore) {
        Assert.notNull(vectorStore, "VectorStore must not be null.");
        this.vectorStore = vectorStore;
    }

    public void load() {
        // Extract
        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(this.resource,
                PdfDocumentReaderConfig.builder()
                        .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                                .withNumberOfBottomTextLinesToDelete(3)
                                .withNumberOfTopPagesToSkipBeforeDelete(1)
                                .build())
                        .withPagesPerDocument(1)
                        .build());

        var tokenTextSplitter = new TokenTextSplitter();

        logger.info(
                "Parsing document, splitting, creating embeddings and storing in vector store...");

        List<Document> splitDocuments = tokenTextSplitter.split(pdfReader.read());

        String searchQuery = "filename:" + resource.getFilename() + " version:" + 1;

        List<Document> result = vectorStore.similaritySearch(searchQuery);

        if (!result.isEmpty()) {
            logger.info("Data for '{}' version {} is already loaded. Exiting load process.", resource.getFilename(), 1);
            return;
        }

        for (Document splitDocument : splitDocuments) {
            splitDocument.getMetadata().put("filename", resource.getFilename());
            splitDocument.getMetadata().put("version", 1);
        }

        this.vectorStore.write(splitDocuments);

        logger.info("Done parsing document, splitting, creating embeddings and storing in vector store");
    }
}
