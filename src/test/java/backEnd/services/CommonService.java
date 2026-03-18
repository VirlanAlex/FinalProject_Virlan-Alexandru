package backEnd.services;

import backEnd.client.RestClient;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * COMMON SERVICE - UNCHANGED ✗
 * 
 * Base class for all services.
 * Provides performRequest() method that wraps RestClient
 */
public class CommonService {

    /**
     * Protected method used by all service subclasses to execute HTTP requests
     * 
     * @param requestType - HTTP method (POST, GET, PUT, DELETE)
     * @param request - RestAssured RequestSpecification
     * @param endpoint - API endpoint URL
     * @return Response object from RestAssured
     */
    protected Response performRequest(String requestType, RequestSpecification request, String endpoint) {
        return new RestClient().performRequest(requestType, request, endpoint);
    }
}
