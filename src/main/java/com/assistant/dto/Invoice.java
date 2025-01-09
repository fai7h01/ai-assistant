package com.assistant.dto;

import com.assistant.enums.InvoiceStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Invoice {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String invoiceNo;

    private InvoiceStatus invoiceStatus = InvoiceStatus.AWAITING_APPROVAL;

    private LocalDateTime dateOfIssue;

    private LocalDateTime dueDate;

    private LocalDateTime acceptDate;

    private String paymentTerms;

    private String notes;

    private String signature;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String company;

    private ClientVendor clientVendor;

    private BigDecimal price;

    private BigDecimal tax;

    private BigDecimal total;

}
