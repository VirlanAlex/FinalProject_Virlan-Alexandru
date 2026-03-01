package tests;

import modelObject.UserModel;
import org.testng.annotations.Test;
import pages.HeaderComponent;
import pages.ProfilePage;
import pages.SignInPage;
import sharedData.SharedData;
import utils.LogUtility;

public class ChangePassword extends SharedData {

    @Test
    public void changePassword() {
        driver.get(url("/auth/login"));

        UserModel loginUser = new UserModel(getData().getValidEmail(), getData().getValidPassword());
        new SignInPage(driver).loginAndAssert(loginUser, getData().getAccountUrlPart());

        UserModel cpUser = new UserModel(getData().getValidEmail(), getData().getValidPassword(), getData().getValidPassword(), getData().getNewPassword());

        HeaderComponent header = new HeaderComponent(driver);
        ProfilePage profilePage = new ProfilePage(driver);

        header.clickProfile();
        profilePage.changePassword(cpUser);
        LogUtility.infoLog("Test flow: Change password");
    }
}
