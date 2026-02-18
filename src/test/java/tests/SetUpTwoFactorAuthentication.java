package tests;

import modelObject.RegisterUserModel;
import modelObject.UserModel;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HeaderComponent;
import pages.ProfilePage;
import pages.RegisterPage;
import pages.SignInPage;
import sharedData.SharedData;
import utils.LogUtility;

import java.time.Duration;

public class SetUpTwoFactorAuthentication extends SharedData {

    @Test
    public void setUpAuthentication() {
        LogUtility.infoLog("Test flow: Set up Two-Factor Authentication (register -> login -> profile -> TOTP)");
        String uniqueEmail = getData().getRegister().getEmailPrefix() + System.currentTimeMillis() + getData().getRegister().getEmailDomain();
        String regPass = getData().getRegister().getRegisterPassword();

        LogUtility.infoLog("Generated unique email for registration: " + uniqueEmail);
        driver.get(url("/auth/login"));
        RegisterUserModel registerUser = new RegisterUserModel(getData().getRegister().getFirstName(), getData().getRegister().getLastName(), getData().getRegister().getDateOfBirth(), getData().getRegister().getStreet(), getData().getRegister().getPostCode(), getData().getRegister().getCity(), getData().getRegister().getState(), getData().getRegister().getCountry(), getData().getRegister().getPhone(), uniqueEmail, regPass);
        RegisterPage registerPage = new RegisterPage(driver);

        LogUtility.infoLog("Open register form");
        registerPage.openRegisterForm();

        LogUtility.infoLog("Submit registration");
        registerPage.register(registerUser);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> d.getCurrentUrl().contains("/auth/login") || d.getCurrentUrl().contains("/account"));
        driver.get(url("/auth/login"));

        LogUtility.infoLog("Login with newly created user");
        UserModel loginUser = new UserModel(uniqueEmail, regPass);
        SignInPage signInPage = new SignInPage(driver);
        signInPage.loginAndAssert(loginUser, getData().getAccountUrlPart());
        HeaderComponent header = new HeaderComponent(driver);
        ProfilePage profilePage = new ProfilePage(driver);

        LogUtility.infoLog("Navigate to Profile");
        header.clickProfile();

        LogUtility.infoLog("Read TOTP secret and validate format");
        String secret = profilePage.getTotpSecret();
        Assert.assertTrue(secret.matches("[A-Z0-9]{8,64}"), "TOTP secret invalid/empty: '" + secret + "'");

        LogUtility.infoLog("Generate TOTP code, submit and verify error message");
        profilePage.enterTotpCode(secret);
        profilePage.verifyTotpCode();
        String errorText = profilePage.getTotpErrorMessage();

        LogUtility.infoLog("Verify TOTP error message");
        Assert.assertEquals(errorText, getData().getTotpErrorMessage(), "TOTP error message is missing or incorrect");
    }
}