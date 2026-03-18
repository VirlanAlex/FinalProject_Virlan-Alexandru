package frontEnd.tests;

import frontEnd.modelObject.UserModel;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import frontEnd.pages.HeaderComponent;
import frontEnd.pages.ProfilePage;
import frontEnd.pages.SignInPage;
import frontEnd.sharedData.SharedData;
import frontEnd.utils.LogUtility;

@Feature("@FEATURE - PASSWORD")
@Story("@STORY - CHANGE PASSWORD OPERATIONS")
public class ChangePasswordFETest extends SharedData {

    @Test(description = "Test flow: Change password")
    public void changePassword() {

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
