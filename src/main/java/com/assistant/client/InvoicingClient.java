package com.assistant.client;

import com.assistant.dto.Invoice;
import com.assistant.dto.response.InvoiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(url = "http://localhost:9090/api/v1", name = "e-invoices")
public interface InvoicingClient {

    @GetMapping("/assistant/invoice/list/{companyTitle}")
    ResponseEntity<InvoiceResponse<List<Invoice>>> getInvoices(@PathVariable("companyTitle") String company);

    @PostMapping("/assistant/invoice/approve/{invNo}/{companyTitle}")
    ResponseEntity<InvoiceResponse<Invoice>> approveInvoice(@PathVariable("invNo") String invNo,
                                                            @PathVariable("companyTitle") String company);

}
