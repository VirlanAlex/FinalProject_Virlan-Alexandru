package backEnd.response;

import io.restassured.response.Response;
import backEnd.types.ResponseStatusType;
import backEnd.utils.LogUtility;
import org.testng.Assert;

/**
 * ImageResponse - parseaza si valideaza response-urile de la Image API
 * Responsabil NUMAI pentru procesarea response-urilor
 */
public class ImageResponse {
    
    private Response response;
    private int statusCode;
    private String responseBody;
    
    public ImageResponse(Response response) {
        this.response = response;
        this.statusCode = response.getStatusCode();
        this.responseBody = response.getBody().asPrettyString();
        
        LogUtility.infoLog("[IMAGE RESPONSE] Response received");
        LogUtility.infoLog("[IMAGE RESPONSE] Status Code: " + this.statusCode);
        LogUtility.infoLog("[IMAGE RESPONSE] Response Body: " + this.responseBody);
    }
    
    /**
     * Valideaza GET ALL IMAGES response (200 OK)
     */
    public ImageResponse validateGetAllImages() {
        LogUtility.infoLog("[IMAGE RESPONSE] Validating GET ALL IMAGES response");
        Assert.assertEquals(this.statusCode, ResponseStatusType.RESPONSE_OK, 
            "Expected 200 but got " + this.statusCode);
        LogUtility.infoLog("[IMAGE RESPONSE] ✓ GET ALL IMAGES validation passed");
        return this;
    }
    
    /**
     * Valideaza dupa custom status code
     */
    public ImageResponse validateStatusCode(int expectedCode) {
        LogUtility.infoLog("[IMAGE RESPONSE] Validating status code: " + expectedCode);
        Assert.assertEquals(this.statusCode, expectedCode, 
            "Expected " + expectedCode + " but got " + this.statusCode);
        LogUtility.infoLog("[IMAGE RESPONSE] ✓ Status code validation passed");
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
