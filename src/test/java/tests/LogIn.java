package tests;

import modelObject.UserModel;
import org.testng.annotations.Test;
import pages.SignInPage;
import sharedData.SharedData;

public class LogIn extends SharedData {

    @Test
    public void signInAccount() {driver.get(url("/auth/login"));
        UserModel user = new UserModel(getData().getValidEmail(), getData().getValidPassword());
        SignInPage signInPage = new SignInPage(driver);
        signInPage.loginAndAssert(user, getData().getAccountUrlPart());
    }
}