package frontEnd.tests;

import frontEnd.modelObject.UserModel;
import org.testng.annotations.Test;
import frontEnd.pages.HeaderComponent;
import frontEnd.pages.SignInPage;
import frontEnd.sharedData.SharedData;
import frontEnd.utils.LogUtility;

public class LogInFETest extends SharedData {

    @Test
    public void signInAccount() {
        LogUtility.infoLog("Test flow: Login");

        new HeaderComponent(driver).clickSignIn();

        UserModel user = new UserModel(getData().getValidEmail(), getData().getValidPassword());
        new SignInPage(driver).loginAndAssert(user, getData().getAccountUrlPart());
    }
}
