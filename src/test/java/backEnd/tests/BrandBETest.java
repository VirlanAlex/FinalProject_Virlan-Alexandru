package backEnd.tests;

import backEnd.models.RequestBrandModel;
import backEnd.models.RequestUserLoginModel;
import backEnd.models.ResponseBrandModel;
import backEnd.models.ResponseUserLoginModel;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import backEnd.services.BrandService;
import backEnd.services.UserService;
import backEnd.types.ResponseStatusType;
import backEnd.utils.LogUtility;

@Feature("@FEATURE - BRAND")
@Story("@STORY - BRAND CRUD OPERATIONS")
public class BrandBETest {
    
    @Test(description = "Complete CRUD workflow for Brand operations")
    public void brandTest() {
        
        LogUtility.infoLog("\n\n╔════════════════════════════════════════════════════════════╗");
        LogUtility.infoLog("║          BRAND CRUD TEST STARTED                             ║");
        LogUtility.infoLog("╚════════════════════════════════════════════════════════════╝\n");
        
        BrandService brandService = new BrandService();
        UserService userService = new UserService();
        
        // ========== STEP 1: CREATE BRAND ==========
        RequestBrandModel createBrandRequest = new RequestBrandModel("BrandTest", "Testing1");
        ResponseBrandModel createdBrand = brandService.createBrand(createBrandRequest);
        String brandId = createdBrand.getId();
        LogUtility.infoLog("✓ Brand created with ID: " + brandId);
        
        
        // ========== STEP 2: VERIFY BRAND WAS CREATED ==========
        brandService.checkSpecificBrand(brandId, ResponseStatusType.RESPONSE_OK);
        LogUtility.infoLog("✓ Brand exists and is accessible");
        
        
        // ========== STEP 3: UPDATE BRAND ==========
        RequestBrandModel updateBrandRequest = new RequestBrandModel("UpdatedBrand", "UpdatedSlug");
        brandService.modifySpecificBrand(updateBrandRequest, brandId);
        LogUtility.infoLog("✓ Brand updated");
        
        
        // ========== STEP 4: VERIFY BRAND WAS UPDATED ==========
        brandService.checkSpecificBrand(brandId, ResponseStatusType.RESPONSE_OK);
        LogUtility.infoLog("✓ Updated brand exists and is accessible");
        
        
        // ========== STEP 5: LOGIN ADMIN USER ==========
        RequestUserLoginModel adminLoginRequest = new RequestUserLoginModel(
            "admin@practicesoftwaretesting.com", 
            "welcome01"
        );
        ResponseUserLoginModel adminLoginResponse = userService.loginUser(adminLoginRequest);
        String adminToken = adminLoginResponse.getAccess_token();
        LogUtility.infoLog("✓ Admin logged in successfully");
        
        
        // ========== STEP 6: DELETE BRAND ==========
        brandService.deleteSpecificBrand(adminToken, brandId);
        LogUtility.infoLog("✓ Brand deleted successfully");
        
        
        // ========== STEP 7: VERIFY DELETION (expect 404) ==========
        brandService.checkSpecificBrand(brandId, ResponseStatusType.RESPONSE_NOT_FOUND);
        LogUtility.infoLog("✓ Brand is not found (correctly deleted)");
        
        
        LogUtility.infoLog("\n\n╔════════════════════════════════════════════════════════════╗");
        LogUtility.infoLog("║          ✓✓✓ ALL BRAND CRUD TESTS PASSED ✓✓✓                 ║");
        LogUtility.infoLog("╚════════════════════════════════════════════════════════════╝\n\n");
    }
}
