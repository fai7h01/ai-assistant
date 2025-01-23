package com.assistant.service;

import com.assistant.dto.analysis.InvoiceAnalysis;
import com.assistant.dto.analysis.SalesAnalysis;

public interface ReportingService {

    SalesAnalysis getSalesAnalysis(String year, String startMonth, String endMonth);

    InvoiceAnalysis getInvoiceAnalysis(String year, String startMonth, String endMonth);

}
