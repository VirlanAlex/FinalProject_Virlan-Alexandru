package backEnd.services;

import backEnd.client.RestClient;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CommonService {

    protected Response performRequest(String requestType, RequestSpecification request, String endpoint) {
        return new RestClient().performRequest(requestType, request, endpoint);
    }
}
