package com.assistant.service;

import com.assistant.dto.analysis.SalesAnalysis;

public interface ReportingService {

    SalesAnalysis getLastMonthSalesAnalysis();

    SalesAnalysis getSalesAnalysis(String year, String startMonth, String endMonth);

}
