package com.assistant.service;

import com.assistant.dto.records.*;
import com.assistant.dto.Invoice;

import java.util.List;

public interface InvoiceService {

    List<Invoice> getInvoices(String companyTitle);

    InvoiceDetails getInvoiceDetails(String invNo, String companyTitle);

    InvoiceDetails approveInvoice(String invNo, String companyTitle);

    InvoiceDetails sendInvoiceToEmail(String invNo, String companyTitle);

    InvoiceDetails createInvoice(String companyTitle);


}
