package backEnd.response;

import io.restassured.response.Response;
import backEnd.models.ResponseBrandModel;
import backEnd.types.ResponseStatusType;
import backEnd.utils.LogUtility;
import org.testng.Assert;

/**
 * BrandResponse - parseaza si valideaza response-urile de la Brand API
 * Responsabil NUMAI pentru procesarea response-urilor
 */
public class BrandResponse {
    
    private Response response;
    private int statusCode;
    private String responseBody;
    private ResponseBrandModel brandModel;
    
    public BrandResponse(Response response) {
        this.response = response;
        this.statusCode = response.getStatusCode();
        this.responseBody = response.getBody().asPrettyString();
        
        LogUtility.infoLog("[BRAND RESPONSE] Response received");
        LogUtility.infoLog("[BRAND RESPONSE] Status Code: " + this.statusCode);
        LogUtility.infoLog("[BRAND RESPONSE] Response Body: " + this.responseBody);
    }
    
    /**
     * Parseaza response in ResponseBrandModel
     */
    public BrandResponse parseBrand() {
        LogUtility.infoLog("[BRAND RESPONSE] Parsing response to ResponseBrandModel");
        try {
            this.brandModel = response.getBody().as(ResponseBrandModel.class);
            LogUtility.infoLog("[BRAND RESPONSE] ✓ Successfully parsed");
            LogUtility.infoLog("[BRAND RESPONSE] Brand ID: " + this.brandModel.getId() + 
                ", Name: " + this.brandModel.getName());
            return this;
        } catch (Exception e) {
            LogUtility.errorLog("[BRAND RESPONSE] Failed to parse: " + e.getMessage());
            throw new RuntimeException("Failed to parse brand response", e);
        }
    }
    
    /**
     * Valideaza CREATE BRAND response (201 CREATED)
     */
    public BrandResponse validateCreate() {
        LogUtility.infoLog("[BRAND RESPONSE] Validating CREATE response");
        Assert.assertEquals(this.statusCode, ResponseStatusType.RESPONSE_CREATED, 
            "Expected 201 but got " + this.statusCode);
        parseBrand();
        LogUtility.infoLog("[BRAND RESPONSE] ✓ CREATE validation passed");
        return this;
    }
    
    /**
     * Valideaza GET BRAND response (200 OK)
     */
    public BrandResponse validateGet() {
        LogUtility.infoLog("[BRAND RESPONSE] Validating GET response");
        Assert.assertEquals(this.statusCode, ResponseStatusType.RESPONSE_OK, 
            "Expected 200 but got " + this.statusCode);
        parseBrand();
        LogUtility.infoLog("[BRAND RESPONSE] ✓ GET validation passed");
        return this;
    }
    
    /**
     * Valideaza UPDATE BRAND response (200 OK)
     */
    public BrandResponse validateUpdate() {
        LogUtility.infoLog("[BRAND RESPONSE] Validating UPDATE response");
        Assert.assertEquals(this.statusCode, ResponseStatusType.RESPONSE_OK, 
            "Expected 200 but got " + this.statusCode);
        LogUtility.infoLog("[BRAND RESPONSE] ✓ UPDATE validation passed");
        return this;
    }
    
    /**
     * Valideaza DELETE BRAND response (204 NO CONTENT)
     */
    public BrandResponse validateDelete() {
        LogUtility.infoLog("[BRAND RESPONSE] Validating DELETE response");
        Assert.assertEquals(this.statusCode, ResponseStatusType.RESPONSE_NO_CONTENT, 
            "Expected 204 but got " + this.statusCode);
        LogUtility.infoLog("[BRAND RESPONSE] ✓ DELETE validation passed");
        return this;
    }
    
    /**
     * Valideaza 404 NOT FOUND response
     */
    public BrandResponse validateNotFound() {
        LogUtility.infoLog("[BRAND RESPONSE] Validating NOT FOUND response");
        Assert.assertEquals(this.statusCode, ResponseStatusType.RESPONSE_NOT_FOUND, 
            "Expected 404 but got " + this.statusCode);
        LogUtility.infoLog("[BRAND RESPONSE] ✓ NOT FOUND validation passed");
        return this;
    }
    
    /**
     * Valideaza dupa custom status code
     */
    public BrandResponse validateStatusCode(int expectedCode) {
        LogUtility.infoLog("[BRAND RESPONSE] Validating status code: " + expectedCode);
        Assert.assertEquals(this.statusCode, expectedCode, 
            "Expected " + expectedCode + " but got " + this.statusCode);
        LogUtility.infoLog("[BRAND RESPONSE] ✓ Status code validation passed");
        return this;
    }
    
    /**
     * Returneaza parsed brand model
     */
    public ResponseBrandModel getBrandModel() {
        if (this.brandModel == null) {
            throw new RuntimeException("Brand model is null. Did you call parseBrand() first?");
        }
        return this.brandModel;
    }
    
    /**
     * Returneaza brand ID
     */
    public String getBrandId() {
        return this.getBrandModel().getId();
    }
    
    /**
     * Returneaza status code
     */
    public int getStatusCode() {
        return this.statusCode;
    }
}
