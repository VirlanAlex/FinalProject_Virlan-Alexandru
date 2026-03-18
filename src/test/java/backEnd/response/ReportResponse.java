package backEnd.response;

import io.restassured.response.Response;
import backEnd.types.ResponseStatusType;
import backEnd.utils.LogUtility;
import org.testng.Assert;

/**
 * ReportResponse - parseaza si valideaza response-urile de la Report API
 * Responsabil NUMAI pentru procesarea response-urilor
 */
public class ReportResponse {
    
    private Response response;
    private int statusCode;
    private String responseBody;
    
    public ReportResponse(Response response) {
        this.response = response;
        this.statusCode = response.getStatusCode();
        this.responseBody = response.getBody().asPrettyString();
        
        LogUtility.infoLog("[REPORT RESPONSE] Response received");
        LogUtility.infoLog("[REPORT RESPONSE] Status Code: " + this.statusCode);
        LogUtility.infoLog("[REPORT RESPONSE] Response Body: " + this.responseBody);
    }
    
    /**
     * Valideaza GENERATE REPORT response (200 OK)
     */
    public ReportResponse validateGenerateReport() {
        LogUtility.infoLog("[REPORT RESPONSE] Validating GENERATE REPORT response");
        Assert.assertEquals(this.statusCode, ResponseStatusType.RESPONSE_OK, 
            "Expected 200 but got " + this.statusCode);
        LogUtility.infoLog("[REPORT RESPONSE] ✓ GENERATE REPORT validation passed");
        return this;
    }
    
    /**
     * Valideaza dupa custom status code
     */
    public ReportResponse validateStatusCode(int expectedCode) {
        LogUtility.infoLog("[REPORT RESPONSE] Validating status code: " + expectedCode);
        Assert.assertEquals(this.statusCode, expectedCode, 
            "Expected " + expectedCode + " but got " + this.statusCode);
        LogUtility.infoLog("[REPORT RESPONSE] ✓ Status code validation passed");
        return this;
    }
    
    /**
     * Returneaza status code
     */
    public int getStatusCode() {
        return this.statusCode;
    }
    
    /**
     * Returneaza response body
     */
    public String getResponseBody() {
        return this.responseBody;
    }
}
