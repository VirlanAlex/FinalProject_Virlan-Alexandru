package tests;

import modelObject.UserModel;
import org.testng.annotations.Test;
import pages.SignInPage;
import sharedData.SharedData;
import utils.LogUtility;

public class LogIn extends SharedData {

    @Test
    public void signInAccount() {
        driver.get(url("/auth/login"));
        UserModel user = new UserModel(getData().getValidEmail(), getData().getValidPassword());
        new SignInPage(driver).loginAndAssert(user, getData().getAccountUrlPart());
        LogUtility.infoLog("Test flow: Login");
    }
}
