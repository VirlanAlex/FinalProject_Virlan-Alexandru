package backEnd.client;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import backEnd.types.RequestMethodType;

public class RestClient {

    public Response performRequest(String requestType, RequestSpecification request, String endpoint) {
        switch (requestType) {
            case RequestMethodType.REQUEST_POST:
                return prepareClient(request).post(endpoint);
            case RequestMethodType.REQUEST_GET:
                return prepareClient(request).get(endpoint);
            case RequestMethodType.REQUEST_PUT:
                return prepareClient(request).put(endpoint);
            case RequestMethodType.REQUEST_DELETE:
                return prepareClient(request).delete(endpoint);

        }

        return null;
    }

    private RequestSpecification prepareClient(RequestSpecification request){
        request.baseUri("https://api.practicesoftwaretesting.com");
        request.header("Content-Type", "application/json");
        request.header("Accept", "application/json");
        return request;
    }
}