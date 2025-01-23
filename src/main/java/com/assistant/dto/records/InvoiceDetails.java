package com.assistant.dto.records;

import com.assistant.enums.InvoiceStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record InvoiceDetails(String invoiceNo,
                             InvoiceStatus invoiceStatus,
                             String dateOfIssue,
                             String dueDate,
                             String acceptDate,
                             String paymentTerms,
                             String notes,
                             String companyTitle,
                             BigDecimal price,
                             BigDecimal tax,
                             BigDecimal total) {
}
