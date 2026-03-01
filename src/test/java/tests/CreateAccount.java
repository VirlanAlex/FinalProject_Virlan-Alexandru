package tests;

import modelObject.RegisterUserModel;
import org.testng.annotations.Test;
import pages.HeaderComponent;
import pages.RegisterPage;
import sharedData.SharedData;
import utils.LogUtility;

public class CreateAccount extends SharedData {

    @Test
    public void startAccount() {
        LogUtility.infoLog("Test flow: Create account (register new user)");

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
