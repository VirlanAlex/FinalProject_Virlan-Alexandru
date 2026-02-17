package tests;

import modelObject.UserModel;
import org.testng.annotations.Test;
import pages.SignInPage;
import sharedData.SharedData;
import utils.LogUtility;

public class LogIn extends SharedData {

    @Test
    public void signInAccount() {
        LogUtility.infoLog("Test flow: Login");
        driver.get(url("/auth/login"));
        LogUtility.infoLog("Open login page");
        UserModel user = new UserModel(getData().getValidEmail(), getData().getValidPassword());
        SignInPage signInPage = new SignInPage(driver);
        signInPage.loginAndAssert(user, getData().getAccountUrlPart());
        LogUtility.infoLog("Login validated");
    }
}