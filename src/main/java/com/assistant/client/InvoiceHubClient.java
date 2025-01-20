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

    @GetMapping("/assistant/invoice/list/{companyTitle}")
    ResponseEntity<InvoiceHubResponse<List<Invoice>>> getInvoices(@PathVariable("companyTitle") String company);

    @PostMapping("/assistant/invoice/approve/{invNo}/{companyTitle}")
    ResponseEntity<InvoiceHubResponse<Invoice>> approveInvoice(@PathVariable("invNo") String invNo,
                                                               @PathVariable("companyTitle") String company);

    @GetMapping("/assistant/invoice/send/{invNo}/{companyTitle}")
    ResponseEntity<InvoiceHubResponse<Invoice>> sendInvoiceToEmail(@PathVariable("invNo") String invNo,
                                                                   @PathVariable("companyTitle") String company);

    @GetMapping("/company")
    ResponseEntity<CompanyResponse> getLoggedInCompany();

    @GetMapping("/user/loggedInUser")
    ResponseEntity<InvoiceHubResponse<User>> getLoggedInUser();


}
