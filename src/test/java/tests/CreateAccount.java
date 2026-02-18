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

        LogUtility.infoLog("Generated unique email for registration: " + uniqueEmail);
        RegisterUserModel user = new RegisterUserModel(getData().getRegister().getFirstName(), getData().getRegister().getLastName(), getData().getRegister().getDateOfBirth(), getData().getRegister().getStreet(), getData().getRegister().getPostCode(), getData().getRegister().getCity(), getData().getRegister().getState(), getData().getRegister().getCountry(), getData().getRegister().getPhone(), uniqueEmail, getData().getValidPassword());
        HeaderComponent header = new HeaderComponent(getDriver());
        RegisterPage registerPage = new RegisterPage(getDriver());

        LogUtility.infoLog("Navigate to Sign In -> Register form");
        header.clickSignIn();
        registerPage.openRegisterForm();

        LogUtility.infoLog("Submit registration form");
        registerPage.register(user);
    }
}