package backEnd.services;

import io.restassured.response.Response;
import backEnd.request.ImageRequest;
import backEnd.response.ImageResponse;
import backEnd.types.EndPointType;
import backEnd.types.RequestMethodType;
import backEnd.utils.LogUtility;


public class ImageService extends CommonService {

    public void obtainAllImages() {
        LogUtility.infoLog("========== STEP 1: GET ALL IMAGES ==========");
        
        // 1. BUILD REQUEST
        ImageRequest imageRequest = new ImageRequest();
        
        // 2. EXECUTE
        Response response = performRequest(
            RequestMethodType.REQUEST_GET,
            imageRequest.getAllImages(),
            EndPointType.IMAGE_GET_ALL_ENDPOINT
        );
        
        // 3. HANDLE RESPONSE
        ImageResponse imageResponse = new ImageResponse(response);
        imageResponse.validateGetAllImages();
        
        LogUtility.infoLog("========== Get all images completed successfully ==========\n");
    }
}
