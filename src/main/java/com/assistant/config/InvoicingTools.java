package com.assistant.config;

import com.assistant.enums.InvoiceStatus;
import com.assistant.enums.InvoiceType;
import com.assistant.service.InvoiceService;
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
    private InvoiceService invoiceService;

    public record InvoiceDetailsRequest(String invoiceNo, String companyTitle){
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
                                 String companyTitle,
                                 BigDecimal price,
                                 BigDecimal tax,
                                 BigDecimal total) {

    }

    @Bean
    @Description("Get invoice details")
    public Function<InvoiceDetailsRequest, InvoiceDetails> getInvoiceDetails() {
        return request -> {
            try {
                return invoiceService.getInvoiceDetails(request.invoiceNo, request.companyTitle);
            }
            catch (Exception e) {
                logger.warn("Invoice details: {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
                return new InvoiceDetails(request.invoiceNo, null, null, null, null,
                        null, null, null, null, null, null, null);
            }
        };
    }

    @Bean
    @Description("Approve invoice and get details")
    public Function<InvoiceDetailsRequest, InvoiceDetails> approveInvoice() {
        return request -> {
            try {
                return invoiceService.approveInvoice(request.invoiceNo, request.companyTitle);
            }
            catch (Exception e) {
                logger.warn("Invoice details: {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
                return new InvoiceDetails(request.invoiceNo, null, null, null, null,
                        null, null, null, null, null, null, null);
            }
        };
    }

    @Bean
    @Description("Send invoice via mail to client")
    public Function<InvoiceDetailsRequest, InvoiceDetails> sendInvoiceViaMail() {
        return request -> {
            try {
                return invoiceService.sendInvoiceToEmail(request.invoiceNo, request.companyTitle);
            }
            catch (Exception e) {
                logger.warn("Invoice details: {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
                return new InvoiceDetails(request.invoiceNo, null, null, null, null,
                        null, null, null, null, null, null, null);
            }
        };
    }

}
