package backEnd.request;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import backEnd.utils.LogUtility;

/**
 * ReportRequest - construieste si configureaza request-urile pentru Report API
 * Responsabil NUMAI pentru construirea request-urilor
 */
public class ReportRequest {
    
    private RequestSpecification requestSpec;
    
    public ReportRequest() {
        this.requestSpec = RestAssured.given();
        this.requestSpec.baseUri("https://api.practicesoftwaretesting.com");
        this.requestSpec.header("Content-Type", "application/json");
        this.requestSpec.header("Accept", "application/json");
    }
    
    /**
     * Construieste request pentru GENERATE REPORT (necesita autentificare)
     */
    public RequestSpecification generateAverageSalesPerMonthReport(String token) {
        LogUtility.infoLog("[REPORT REQUEST] Constructing GENERATE REPORT request");
        requestSpec.header("Authorization", "Bearer " + token);
        LogUtility.infoLog("[REPORT REQUEST] Authorization header added with token");
        return requestSpec;
    }
}
