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
        
        LogUtility.infoLog("\n\n╔════════════════════════════════════════════════════════════╗");
        LogUtility.infoLog("║          IMAGE TEST STARTED                                  ║");
        LogUtility.infoLog("╚════════════════════════════════════════════════════════════╝\n");
        
        ImageService imageService = new ImageService();
        
        // ========== STEP 1: GET ALL IMAGES ==========
        imageService.obtainAllImages();
        LogUtility.infoLog("✓ All images retrieved successfully");
        
        
        LogUtility.infoLog("\n\n╔════════════════════════════════════════════════════════════╗");
        LogUtility.infoLog("║          ✓✓✓ IMAGE TEST PASSED ✓✓✓                            ║");
        LogUtility.infoLog("╚════════════════════════════════════════════════════════════╝\n\n");
    }
}
