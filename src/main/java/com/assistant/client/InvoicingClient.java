package com.assistant.client;

import com.assistant.dto.response.InvoiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "http://localhost:9090/api/v1", name = "e-invoices")
public interface InvoicingClient {

    @GetMapping("/invoice/list")
    ResponseEntity<InvoiceResponse> getInvoices();
}
