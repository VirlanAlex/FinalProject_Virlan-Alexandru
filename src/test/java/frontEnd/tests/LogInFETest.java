package frontEnd.tests;

import frontEnd.modelObject.UserModel;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import frontEnd.pages.HeaderComponent;
import frontEnd.pages.SignInPage;
import frontEnd.sharedData.SharedData;
import frontEnd.utils.LogUtility;

@Feature("@FEATURE - LOGIN")
@Story("@STORY - LOGIN OPERATIONS")
public class LogInFETest extends SharedData {

    @Test(description = "Test flow: Login")
    public void signInAccount() {
        new HeaderComponent(driver).clickSignIn();

        UserModel user = new UserModel(getData().getValidEmail(), getData().getValidPassword());
        new SignInPage(driver).loginAndAssert(user, getData().getAccountUrlPart());
    }
}
