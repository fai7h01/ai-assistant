package com.assistant.dto.analysis;

import com.assistant.enums.Currency;
import com.assistant.enums.InvoiceStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceAnalysis {

    private int totalInvoicesNumber;
    private int totalApprovedInvoicesNumber;
    private int totalOverDueInvoicesNumber;
    private int totalPendingInvoicesNumber;
    List<InvoiceData> invoices;

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class InvoiceData {

        private String invoiceNo;
        private InvoiceStatus invoiceStatus;
        private LocalDateTime dateOfIssue;
        private LocalDateTime dueDate;
        private LocalDateTime acceptDate;
        private String paymentTerms;
        private String clientName;
        private BigDecimal price;
        private Currency currency;
        private BigDecimal tax;
        private BigDecimal total;
        private List<String> invoiceItems;
    }
}
