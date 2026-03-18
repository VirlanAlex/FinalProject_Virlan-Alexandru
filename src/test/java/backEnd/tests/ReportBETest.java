package backEnd.tests;

import backEnd.models.RequestUserLoginModel;
import backEnd.models.ResponseUserLoginModel;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import backEnd.services.ReportService;
import backEnd.services.UserService;
import backEnd.utils.LogUtility;

@Feature("@FEATURE - REPORT")
@Story("@STORY - REPORT OPERATIONS")
public class ReportBETest {
    
    @Test(description = "Generate average sales per month report with admin authentication")
    public void reportTest() {
        
        LogUtility.infoLog("\n\n╔════════════════════════════════════════════════════════════╗");
        LogUtility.infoLog("║          REPORT TEST STARTED                                 ║");
        LogUtility.infoLog("╚════════════════════════════════════════════════════════════╝\n");
        
        UserService userService = new UserService();
        ReportService reportService = new ReportService();
        
        // ========== STEP 1: LOGIN ADMIN USER ==========
        LogUtility.infoLog("========== STEP 1: LOGIN ADMIN USER ==========");
        RequestUserLoginModel adminLoginRequest = new RequestUserLoginModel(
            "admin@practicesoftwaretesting.com", 
            "welcome01"
        );
        ResponseUserLoginModel adminLoginResponse = userService.loginUser(adminLoginRequest);
        String adminToken = adminLoginResponse.getAccess_token();
        LogUtility.infoLog("✓ Admin logged in successfully");
        
        
        // ========== STEP 2: GENERATE REPORT ==========
        LogUtility.infoLog("\n========== STEP 2: GENERATE REPORT ==========");
        reportService.generateAverageSalesPerMonthReport(adminToken);
        LogUtility.infoLog("✓ Report generated successfully");
        
        
        LogUtility.infoLog("\n\n╔════════════════════════════════════════════════════════════╗");
        LogUtility.infoLog("║          ✓✓✓ REPORT TEST PASSED ✓✓✓                            ║");
        LogUtility.infoLog("╚════════════════════════════════════════════════════════════╝\n\n");
    }
}
