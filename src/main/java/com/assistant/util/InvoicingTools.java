package com.assistant.util;

import com.assistant.dto.analysis.ClientAnalysis;
import com.assistant.dto.analysis.InvoiceAnalysis;
import com.assistant.dto.analysis.SalesAnalysis;
import com.assistant.dto.records.*;
import com.assistant.dto.records.InvoiceDetailsRequest;
import com.assistant.service.InvoiceService;
import com.assistant.service.ReportingService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.function.TriFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.core.NestedExceptionUtils;

import java.util.function.Function;
import java.util.function.Supplier;


@Configuration
@RequiredArgsConstructor
public class InvoicingTools {

    private static final Logger logger = LoggerFactory.getLogger(InvoicingTools.class);

    private final InvoiceService invoiceService;
    private final ReportingService reportingService;


    @Bean
    @Description("Approve invoice and get details")
    public Function<InvoiceDetailsRequest, InvoiceDetails> approveInvoice() {
        return createInvoiceFunction(invoiceService::approveInvoice);
    }

    @Bean
    @Description("Send invoice via mail to client")
    public Function<InvoiceDetailsRequest, InvoiceDetails> sendInvoiceViaMail() {
        return createInvoiceFunction(invoiceService::sendInvoiceToEmail);
    }

    @Bean
    @Description("Analyze sales data in specific date range")
    public Function<SalesAnalysisRequest, SalesAnalysis> analyzeSalesDateRange() {
        return createSalesAnalysisFunction(reportingService::getSalesAnalysis);
    }

    @Bean
    @Description("Analyze invoices data in specific date range")
    public Function<InvoiceAnalysisRequest, InvoiceAnalysis> analyzeInvoicesDateRange() {
        return createInvoiceAnalysisFunction(reportingService::getInvoiceAnalysis);
    }

    @Bean
    @Description("Analyze clients data")
    public Function<Void, ClientAnalysis> analyzeClientsData() {
        return createClientSupplier(reportingService::getClientAnalysis);
    }

    private Function<Void, ClientAnalysis> createClientSupplier(Supplier<ClientAnalysis> processor) {
        return request -> {
            try {
                return processor.get();
            } catch (Exception e) {
                logger.warn("Client details: {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
                return new ClientAnalysis();
            }
        };
    }

    private Function<InvoiceDetailsRequest, InvoiceDetails> createInvoiceFunction(Function<String, InvoiceDetails> processor) {
        return request -> {
            try {
                return processor.apply(request.invoiceNo());
            } catch (Exception e) {
                logger.warn("Invoice details: {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
                return new InvoiceDetails(request.invoiceNo(), null, null, null, null,
                        null, null, null, null, null, null);
            }
        };
    }

    private Function<SalesAnalysisRequest, SalesAnalysis> createSalesAnalysisFunction(TriFunction<String, String, String, SalesAnalysis> processor) {
        return request -> {
            try {
                return processor.apply(request.year(), request.startMonth(), request.endMonth());
            } catch (Exception e) {
                logger.warn("Sales Analysis: {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
                return new SalesAnalysis();
            }
        };
    }

    private Function<InvoiceAnalysisRequest, InvoiceAnalysis> createInvoiceAnalysisFunction(TriFunction<String, String, String, InvoiceAnalysis> processor) {
        return request -> {
            try {
                return processor.apply(request.year(), request.startMonth(), request.endMonth());
            } catch (Exception e) {
                logger.warn("Invoice Analysis: {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
                return new InvoiceAnalysis();
            }
        };
    }

}
