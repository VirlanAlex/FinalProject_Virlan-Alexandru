package backEnd.request;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import backEnd.models.RequestBrandModel;
import backEnd.utils.LogUtility;

public class BrandRequest {
    
    private RequestSpecification requestSpec;
    
    public BrandRequest() {
        this.requestSpec = RestAssured.given();
        this.requestSpec.baseUri("https://api.practicesoftwaretesting.com");
        this.requestSpec.header("Content-Type", "application/json");
        this.requestSpec.header("Accept", "application/json");
    }

    public RequestSpecification createBrand(RequestBrandModel requestBody) {
        LogUtility.infoLog("[BRAND REQUEST] Constructing CREATE request");
        requestSpec.body(requestBody);
        LogUtility.infoLog("[BRAND REQUEST] Body set: name=" + requestBody.getName() + ", slug=" + requestBody.getSlug());
        return requestSpec;
    }

    public RequestSpecification getBrand() {
        LogUtility.infoLog("[BRAND REQUEST] Constructing GET request");
        return requestSpec;
    }

    public RequestSpecification updateBrand(RequestBrandModel requestBody) {
        LogUtility.infoLog("[BRAND REQUEST] Constructing UPDATE request");
        requestSpec.body(requestBody);
        LogUtility.infoLog("[BRAND REQUEST] Body set: name=" + requestBody.getName() + ", slug=" + requestBody.getSlug());
        return requestSpec;
    }

    public RequestSpecification deleteBrand(String token) {
        LogUtility.infoLog("[BRAND REQUEST] Constructing DELETE request");
        requestSpec.header("Authorization", "Bearer " + token);
        LogUtility.infoLog("[BRAND REQUEST] Authorization header added with token");
        return requestSpec;
    }
}
