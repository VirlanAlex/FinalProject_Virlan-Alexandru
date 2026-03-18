package backEnd.services;

import io.restassured.response.Response;
import backEnd.request.ReportRequest;
import backEnd.response.ReportResponse;
import backEnd.types.EndPointType;
import backEnd.types.RequestMethodType;
import backEnd.utils.LogUtility;

public class ReportService extends CommonService {

    public void generateAverageSalesPerMonthReport(String token) {
        LogUtility.infoLog("========== STEP 2: GENERATE REPORT ==========");
        
        // 1. BUILD REQUEST
        ReportRequest reportRequest = new ReportRequest();
        
        // 2. EXECUTE
        Response response = performRequest(
            RequestMethodType.REQUEST_GET,
            reportRequest.generateAverageSalesPerMonthReport(token),
            EndPointType.REPORT_AVERAGE_SALES_ENDPOINT
        );
        
        // 3. HANDLE RESPONSE
        ReportResponse reportResponse = new ReportResponse(response);
        reportResponse.validateGenerateReport();
        
        LogUtility.infoLog("========== Report generated successfully ==========\n");
    }
}
