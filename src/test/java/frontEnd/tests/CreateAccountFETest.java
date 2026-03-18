package frontEnd.tests;

import frontEnd.modelObject.RegisterUserModel;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import frontEnd.pages.HeaderComponent;
import frontEnd.pages.RegisterPage;
import frontEnd.sharedData.SharedData;
import frontEnd.utils.LogUtility;

@Feature("@FEATURE - ACCOUNT")
@Story("@STORY - CREATE ACCOUNT OPERATIONS")
public class CreateAccountFETest extends SharedData {

    @Test(description = "Test flow: Create account (register new user)")
    public void startAccount() {
        String uniqueEmail = getData().getRegister().getEmailPrefix() + System.currentTimeMillis() + getData().getRegister().getEmailDomain();

        RegisterUserModel user = RegisterUserModel.fromRegisterData(getData().getRegister(), uniqueEmail);

        HeaderComponent header = new HeaderComponent(getDriver());
        RegisterPage registerPage = new RegisterPage(getDriver());

        header.clickSignIn();
        registerPage.openRegisterForm();
        registerPage.register(user);

        LogUtility.infoLog("Generated unique email for registration: " + uniqueEmail);
    }
}
