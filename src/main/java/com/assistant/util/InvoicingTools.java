package com.assistant.util;

import com.assistant.dto.records.*;
import com.assistant.dto.records.InvoiceDetailsRequest;
import com.assistant.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.core.NestedExceptionUtils;

import java.util.function.BiFunction;
import java.util.function.Function;


@Configuration
@RequiredArgsConstructor
public class InvoicingTools {

    private static final Logger logger = LoggerFactory.getLogger(InvoicingTools.class);

    private final InvoiceService invoiceService;


    private Function<InvoiceDetailsRequest, InvoiceDetails> createFunction(BiFunction<String, String, InvoiceDetails> processor) {
        return request -> {
            try {
                return processor.apply(request.invoiceNo(), request.companyTitle());
            } catch (Exception e) {
                logger.warn("Invoice details: {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
                return new InvoiceDetails(request.invoiceNo(), null, null, null, null,
                        null, null, null, null, null, null);
            }
        };
    }

    @Bean
    @Description("Get invoice details")
    public Function<InvoiceDetailsRequest, InvoiceDetails> getInvoiceDetails() {
        return createFunction(invoiceService::getInvoiceDetails);
    }

    @Bean
    @Description("Approve invoice and get details")
    public Function<InvoiceDetailsRequest, InvoiceDetails> approveInvoice() {
        return createFunction(invoiceService::approveInvoice);
    }

    @Bean
    @Description("Send invoice via mail to client")
    public Function<InvoiceDetailsRequest, InvoiceDetails> sendInvoiceViaMail() {
        return createFunction(invoiceService::sendInvoiceToEmail);
    }

    //data analysis

    //invoice crud



}
