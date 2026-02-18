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
        LogUtility.infoLog("Test flow: Change password");
        driver.get(url("/auth/login"));

        LogUtility.infoLog("Open login page");
        UserModel loginUser = new UserModel(getData().getValidEmail(), getData().getValidPassword());
        new SignInPage(driver).loginAndAssert(loginUser, getData().getAccountUrlPart());

        LogUtility.infoLog("Login validated");
        UserModel cpUser = new UserModel(getData().getValidEmail(), getData().getValidPassword(), getData().getValidPassword(), getData().getNewPassword());
        HeaderComponent header = new HeaderComponent(driver);
        ProfilePage profilePage = new ProfilePage(driver);

        LogUtility.infoLog("Navigate to Profile");
        header.clickProfile();

        LogUtility.infoLog("Submit change password form");
        profilePage.changePassword(cpUser);

    }
}