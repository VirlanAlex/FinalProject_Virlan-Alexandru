package backEnd.services;

import io.restassured.response.Response;
import backEnd.models.RequestBrandModel;
import backEnd.models.ResponseBrandModel;
import backEnd.request.BrandRequest;
import backEnd.response.BrandResponse;
import backEnd.types.EndPointType;
import backEnd.types.RequestMethodType;
import backEnd.utils.LogUtility;

public class BrandService extends CommonService {

    public ResponseBrandModel createBrand(RequestBrandModel requestBody) {
        LogUtility.infoLog("========== STEP 1: CREATE BRAND ==========");
        
        // 1. BUILD REQUEST
        BrandRequest brandRequest = new BrandRequest();
        
        // 2. EXECUTE
        Response response = performRequest(
            RequestMethodType.REQUEST_POST,
            brandRequest.createBrand(requestBody),
            EndPointType.BRAND_CREATE_ENDPOINT
        );
        
        // 3. HANDLE RESPONSE
        BrandResponse brandResponse = new BrandResponse(response);
        brandResponse.validateCreate();
        
        LogUtility.infoLog("========== Brand created successfully ==========\n");
        return brandResponse.getBrandModel();
    }

    public void checkSpecificBrand(String brandId, int expectedStatusCode) {
        LogUtility.infoLog("========== STEP 2: CHECK BRAND ==========");
        
        // 1. BUILD REQUEST
        BrandRequest brandRequest = new BrandRequest();
        
        // 2. EXECUTE
        Response response = performRequest(
            RequestMethodType.REQUEST_GET,
            brandRequest.getBrand(),
            EndPointType.BRAND_REQUEST_ENDPOINT + brandId
        );
        
        // 3. HANDLE RESPONSE
        BrandResponse brandResponse = new BrandResponse(response);
        
        if (expectedStatusCode == 200) {
            brandResponse.validateGet();
        } else if (expectedStatusCode == 404) {
            brandResponse.validateNotFound();
        } else {
            brandResponse.validateStatusCode(expectedStatusCode);
        }
        
        LogUtility.infoLog("========== Brand check completed ==========\n");
    }

    public void modifySpecificBrand(RequestBrandModel requestBody, String brandId) {
        LogUtility.infoLog("========== STEP 3: UPDATE BRAND ==========");
        
        // 1. BUILD REQUEST
        BrandRequest brandRequest = new BrandRequest();
        
        // 2. EXECUTE
        Response response = performRequest(
            RequestMethodType.REQUEST_PUT,
            brandRequest.updateBrand(requestBody),
            EndPointType.BRAND_REQUEST_ENDPOINT + brandId
        );
        
        // 3. HANDLE RESPONSE
        BrandResponse brandResponse = new BrandResponse(response);
        brandResponse.validateUpdate();
        
        LogUtility.infoLog("========== Brand updated successfully ==========\n");
    }

    public void deleteSpecificBrand(String token, String brandId) {
        LogUtility.infoLog("========== STEP 6: DELETE BRAND ==========");
        
        // 1. BUILD REQUEST
        BrandRequest brandRequest = new BrandRequest();
        
        // 2. EXECUTE
        Response response = performRequest(
            RequestMethodType.REQUEST_DELETE,
            brandRequest.deleteBrand(token),
            EndPointType.BRAND_REQUEST_ENDPOINT + brandId
        );
        
        // 3. HANDLE RESPONSE
        BrandResponse brandResponse = new BrandResponse(response);
        brandResponse.validateDelete();
        
        LogUtility.infoLog("========== Brand deleted successfully ==========\n");
    }
}
