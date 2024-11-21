package com.assistant.service;

import com.assistant.config.InvoicingTools;
import com.assistant.dto.Invoice;

import java.util.List;

public interface InvoiceService {

    List<Invoice> getInvoices(String companyTitle);

    InvoicingTools.InvoiceDetails getInvoiceDetails(String invNo, String companyTitle);

    InvoicingTools.InvoiceDetails approveInvoice(String invNo, String companyTitle);

    InvoicingTools.InvoiceDetails sendInvoiceToEmail(String invNo, String companyTitle);
}
