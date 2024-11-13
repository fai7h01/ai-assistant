package com.assistant.service;

import com.assistant.enums.InvoiceStatus;
import com.assistant.enums.InvoiceType;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.core.NestedExceptionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.function.Function;

@Configuration
public class InvoicingTools {

    private static final Logger logger = LoggerFactory.getLogger(InvoicingTools.class);

    @Autowired
    private InvoicingService invoicingService;

    public record InvoiceDetailsRequest(String invoiceNo){
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record InvoiceDetails(String invoiceNo,
                                 InvoiceStatus invoiceStatus,
                                 InvoiceType invoiceType,
                                 LocalDateTime dateOfIssue,
                                 LocalDateTime dueDate,
                                 LocalDateTime acceptDate,
                                 String paymentTerms,
                                 String notes,
                                 BigDecimal price,
                                 BigDecimal tax,
                                 BigDecimal total) {

    }

    @Bean
    @Description("Get invoice details")
    public Function<InvoiceDetailsRequest, InvoiceDetails> getInvoiceDetails() {
        return request -> {
            try {
                return invoicingService.getInvoiceDetails(request.invoiceNo);
            }
            catch (Exception e) {
                logger.warn("Invoice details: {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
                return new InvoiceDetails(request.invoiceNo, null, null, null, null,
                        null, null, null, null, null, null);
            }
        };
    }

}
