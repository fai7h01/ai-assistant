package com.assistant.dto.response;

import com.assistant.dto.Invoice;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceResponse {

    private boolean success;
    private String message;
    private Integer code;
    private List<Invoice> data;

}
