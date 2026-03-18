package frontEnd.tests;

import frontEnd.modelObject.UserModel;
import org.testng.annotations.Test;
import frontEnd.pages.HeaderComponent;
import frontEnd.pages.ProfilePage;
import frontEnd.pages.SignInPage;
import frontEnd.sharedData.SharedData;
import frontEnd.utils.LogUtility;

public class ChangePasswordFETest extends SharedData {

    @Test
    public void changePassword() {
        LogUtility.infoLog("Test flow: Change password");

        HeaderComponent header = new HeaderComponent(driver);
        header.clickSignIn();

        UserModel loginUser = new UserModel(getData().getValidEmail(), getData().getValidPassword());
        new SignInPage(driver).loginAndAssert(loginUser, getData().getAccountUrlPart());

        UserModel cpUser = new UserModel(getData().getValidEmail(), getData().getValidPassword(), getData().getValidPassword(), getData().getNewPassword());

        ProfilePage profilePage = new ProfilePage(driver);
        header.clickProfile();
        profilePage.changePassword(cpUser);
    }
}
