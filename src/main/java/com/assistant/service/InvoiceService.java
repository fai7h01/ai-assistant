package com.assistant.service;

import com.assistant.dto.records.*;
import com.assistant.dto.Invoice;

import java.util.List;

public interface InvoiceService {

    List<Invoice> getInvoices();

    InvoiceDetails getInvoiceDetails(String invNo);

    InvoiceDetails approveInvoice(String invNo);

    InvoiceDetails sendInvoiceToEmail(String invNo);

    InvoiceDetails createInvoice(String companyTitle);


}
