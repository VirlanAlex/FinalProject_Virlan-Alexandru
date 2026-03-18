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
        
        LogUtility.infoLog("BRAND CRUD TEST STARTED");
        
        BrandService brandService = new BrandService();
        UserService userService = new UserService();

        RequestBrandModel createBrandRequest = new RequestBrandModel("BrandTest", "Testing1");
        ResponseBrandModel createdBrand = brandService.createBrand(createBrandRequest);
        String brandId = createdBrand.getId();
        LogUtility.infoLog("Brand created with ID: " + brandId);

        brandService.checkSpecificBrand(brandId, ResponseStatusType.RESPONSE_OK);
        LogUtility.infoLog("Brand exists and is accessible");

        RequestBrandModel updateBrandRequest = new RequestBrandModel("UpdatedBrand", "UpdatedSlug");
        brandService.modifySpecificBrand(updateBrandRequest, brandId);
        LogUtility.infoLog("Brand updated");

        brandService.checkSpecificBrand(brandId, ResponseStatusType.RESPONSE_OK);
        LogUtility.infoLog("Updated brand exists and is accessible");

        RequestUserLoginModel adminLoginRequest = new RequestUserLoginModel("admin@practicesoftwaretesting.com", "welcome01");
        ResponseUserLoginModel adminLoginResponse = userService.loginUser(adminLoginRequest);
        String adminToken = adminLoginResponse.getAccess_token();
        LogUtility.infoLog("Admin logged in successfully");

        brandService.deleteSpecificBrand(adminToken, brandId);
        LogUtility.infoLog("Brand deleted successfully");

        brandService.checkSpecificBrand(brandId, ResponseStatusType.RESPONSE_NOT_FOUND);
        LogUtility.infoLog("Brand is not found (correctly deleted)");

        LogUtility.infoLog("ALL BRAND CRUD TESTS ENDED");
    }
}
