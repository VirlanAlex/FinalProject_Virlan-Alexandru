package backEnd.services;

import io.restassured.response.Response;
import backEnd.request.ImageRequest;
import backEnd.response.ImageResponse;
import backEnd.types.EndPointType;
import backEnd.types.RequestMethodType;
import backEnd.utils.LogUtility;


public class ImageService extends CommonService {

    public void obtainAllImages() {
        LogUtility.infoLog("STEP 1: GET ALL IMAGES");

        ImageRequest imageRequest = new ImageRequest();

        Response response = performRequest(RequestMethodType.REQUEST_GET, imageRequest.getAllImages(), EndPointType.IMAGE_GET_ALL_ENDPOINT);

        ImageResponse imageResponse = new ImageResponse(response);
        imageResponse.validateGetAllImages();
        
        LogUtility.infoLog("Get all images completed successfully");
    }
}
