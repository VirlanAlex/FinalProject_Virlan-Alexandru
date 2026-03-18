package backEnd.tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import backEnd.services.ImageService;
import backEnd.utils.LogUtility;

@Feature("@FEATURE - IMAGE")
@Story("@STORY - IMAGE OPERATIONS")
public class ImageBETest {
    
    @Test(description = "Get all images and verify response")
    public void imageTest() {
        
        LogUtility.infoLog("IMAGE TEST STARTED");
        
        ImageService imageService = new ImageService();

        imageService.obtainAllImages();
        LogUtility.infoLog("Retrive all images");

        LogUtility.infoLog("IMAGE TEST ENDED");
    }
}
