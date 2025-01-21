package com.assistant.client;

import com.assistant.dto.Invoice;
import com.assistant.dto.User;
import com.assistant.dto.response.CompanyResponse;
import com.assistant.dto.response.InvoiceHubResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(url = "http://localhost:9090/api/v1", name = "InvoiceHub")
public interface InvoiceHubClient {

    @GetMapping("/assistant/invoice/list")
    ResponseEntity<InvoiceHubResponse<List<Invoice>>> getInvoices();

    @PostMapping("/assistant/invoice/approve/{invNo}")
    ResponseEntity<InvoiceHubResponse<Invoice>> approveInvoice(@PathVariable("invNo") String invNo);

    @GetMapping("/assistant/invoice/send/{invNo}")
    ResponseEntity<InvoiceHubResponse<Invoice>> sendInvoiceToEmail(@PathVariable("invNo") String invNo);

    @GetMapping("/company")
    ResponseEntity<CompanyResponse> getLoggedInCompany();

    @GetMapping("/user/loggedInUser")
    ResponseEntity<InvoiceHubResponse<User>> getLoggedInUser();


}
