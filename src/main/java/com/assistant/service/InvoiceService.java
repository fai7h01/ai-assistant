package com.assistant.service;

import com.assistant.dto.records.*;

public interface InvoiceService {

    InvoiceDetails approveInvoice(String invNo);

    InvoiceDetails sendInvoiceToEmail(String invNo);

}
