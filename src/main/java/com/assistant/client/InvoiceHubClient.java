package com.assistant.client;

import com.assistant.dto.Invoice;
import com.assistant.dto.analysis.ClientAnalysis;
import com.assistant.dto.analysis.InvoiceAnalysis;
import com.assistant.dto.analysis.SalesAnalysis;
import com.assistant.dto.response.InvoiceHubResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(url = "http://localhost:9090/api/v1", name = "InvoiceHub")
public interface InvoiceHubClient {

    @GetMapping("/assistant/client-analysis")
    ResponseEntity<InvoiceHubResponse<ClientAnalysis>> getClientAnalysis();

    @GetMapping("/assistant/invoice-analysis/{year}/{startMonth}/{endMonth}")
    ResponseEntity<InvoiceHubResponse<InvoiceAnalysis>> getInvoiceAnalysis(@PathVariable("year") String year,
                                                                           @PathVariable("startMonth") String startMonth,
                                                                           @PathVariable("endMonth") String endMonth);

    @GetMapping("/assistant/sales-analysis/{year}/{startMonth}/{endMonth}")
    ResponseEntity<InvoiceHubResponse<SalesAnalysis>> getSalesAnalysis(@PathVariable("year") String year,
                                                                       @PathVariable("startMonth") String startMonth,
                                                                       @PathVariable("endMonth") String endMonth);

    @PostMapping("/assistant/invoice/approve/{invNo}")
    ResponseEntity<InvoiceHubResponse<Invoice>> approveInvoice(@PathVariable("invNo") String invNo);

    @GetMapping("/assistant/invoice/send/{invNo}")
    ResponseEntity<InvoiceHubResponse<Invoice>> sendInvoiceToEmail(@PathVariable("invNo") String invNo);


}
