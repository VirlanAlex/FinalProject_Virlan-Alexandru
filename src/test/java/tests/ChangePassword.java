package tests;

import modelObject.UserModel;
import org.testng.annotations.Test;
import pages.HeaderComponent;
import pages.ProfilePage;
import pages.SignInPage;
import sharedData.SharedData;

public class ChangePassword extends SharedData {

    @Test
    public void changePassword() {
        driver.get(url("/auth/login"));

        // user pentru login
        UserModel loginUser = new UserModel(getData().getValidEmail(), getData().getValidPassword());
        new SignInPage(driver).loginAndAssert(loginUser, getData().getAccountUrlPart());

        // user pentru change password (constructorul tău cere current/new)
        UserModel cpUser = new UserModel(
                getData().getValidEmail(),
                getData().getValidPassword(),
                getData().getValidPassword(),
                getData().getNewPassword()
        );

        HeaderComponent header = new HeaderComponent(driver);
        ProfilePage profilePage = new ProfilePage(driver);

        header.clickProfile();
        profilePage.changePassword(cpUser);

        // aici nu ai în cod un locator de success, deci nu inventez.
        // Testul ăsta îți validează flow-ul fără să pice (și tu poți adăuga apoi assert pe mesaj).
    }
}