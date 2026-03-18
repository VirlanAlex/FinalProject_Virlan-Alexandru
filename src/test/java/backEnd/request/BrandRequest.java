package backEnd.request;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import backEnd.models.RequestBrandModel;
import backEnd.utils.LogUtility;

/**
 * BrandRequest - construieste si configureaza request-urile pentru Brand API
 * Responsabil NUMAI pentru construirea request-urilor
 */
public class BrandRequest {
    
    private RequestSpecification requestSpec;
    
    public BrandRequest() {
        this.requestSpec = RestAssured.given();
        this.requestSpec.baseUri("https://api.practicesoftwaretesting.com");
        this.requestSpec.header("Content-Type", "application/json");
        this.requestSpec.header("Accept", "application/json");
    }
    
    /**
     * Construieste request pentru CREATE BRAND
     */
    public RequestSpecification createBrand(RequestBrandModel requestBody) {
        LogUtility.infoLog("[BRAND REQUEST] Constructing CREATE request");
        requestSpec.body(requestBody);
        LogUtility.infoLog("[BRAND REQUEST] Body set: name=" + requestBody.getName() + ", slug=" + requestBody.getSlug());
        return requestSpec;
    }
    
    /**
     * Construieste request pentru GET (READ) BRAND
     */
    public RequestSpecification getBrand() {
        LogUtility.infoLog("[BRAND REQUEST] Constructing GET request");
        return requestSpec;
    }
    
    /**
     * Construieste request pentru UPDATE BRAND
     */
    public RequestSpecification updateBrand(RequestBrandModel requestBody) {
        LogUtility.infoLog("[BRAND REQUEST] Constructing UPDATE request");
        requestSpec.body(requestBody);
        LogUtility.infoLog("[BRAND REQUEST] Body set: name=" + requestBody.getName() + ", slug=" + requestBody.getSlug());
        return requestSpec;
    }
    
    /**
     * Construieste request pentru DELETE BRAND (necesita autentificare)
     */
    public RequestSpecification deleteBrand(String token) {
        LogUtility.infoLog("[BRAND REQUEST] Constructing DELETE request");
        requestSpec.header("Authorization", "Bearer " + token);
        LogUtility.infoLog("[BRAND REQUEST] Authorization header added with token");
        return requestSpec;
    }
}
