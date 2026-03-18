package backEnd.request;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import backEnd.utils.LogUtility;

/**
 * ImageRequest - construieste si configureaza request-urile pentru Image API
 * Responsabil NUMAI pentru construirea request-urilor
 */
public class ImageRequest {
    
    private RequestSpecification requestSpec;
    
    public ImageRequest() {
        this.requestSpec = RestAssured.given();
        this.requestSpec.baseUri("https://api.practicesoftwaretesting.com");
        this.requestSpec.header("Content-Type", "application/json");
        this.requestSpec.header("Accept", "application/json");
    }
    
    /**
     * Construieste request pentru GET ALL IMAGES
     */
    public RequestSpecification getAllImages() {
        LogUtility.infoLog("[IMAGE REQUEST] Constructing GET ALL IMAGES request");
        return requestSpec;
    }
}
