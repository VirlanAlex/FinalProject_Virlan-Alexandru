package frontEnd.tests;

import frontEnd.modelObject.RegisterUserModel;
import frontEnd.modelObject.UserModel;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import frontEnd.pages.HeaderComponent;
import frontEnd.pages.ProfilePage;
import frontEnd.pages.RegisterPage;
import frontEnd.pages.SignInPage;
import frontEnd.sharedData.SharedData;
import frontEnd.utils.LogUtility;

import java.time.Duration;

public class SetUpTwoFactorAuthenticationFETest extends SharedData {

    @Test
    public void setUpAuthentication() {
        LogUtility.infoLog("Test flow: Set up Two-Factor Authentication (register -> login -> profile -> TOTP)");

        String uniqueEmail = getData().getRegister().getEmailPrefix() + System.currentTimeMillis() + getData().getRegister().getEmailDomain();
        RegisterUserModel registerUser = RegisterUserModel.fromRegisterData(getData().getRegister(), uniqueEmail);
        String regPass = getData().getRegister().getRegisterPassword();

        HeaderComponent header = new HeaderComponent(driver);

        // Pasul 1: navigare la sign in
        header.clickSignIn();

        // Pasul 2: register
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.openRegisterForm();
        registerPage.register(registerUser);

        // Pasul 3: dupa register Angular redirecteaza la /auth/login — asteptam URL-ul explicit
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.urlContains("/auth/login"));

        // Pasul 4: login cu noul user
        UserModel loginUser = new UserModel(uniqueEmail, regPass);
        new SignInPage(driver).loginAndAssert(loginUser, getData().getAccountUrlPart());

        // Pasul 5: TOTP setup
        ProfilePage profilePage = new ProfilePage(driver);
        header.clickProfile();

        String secret = profilePage.getTotpSecret();
        Assert.assertTrue(secret.matches("[A-Z0-9]{8,64}"), "TOTP secret invalid/empty: '" + secret + "'");

        profilePage.enterTotpCode(secret);
        profilePage.verifyTotpCode();

        String errorText = profilePage.getTotpErrorMessage();
        Assert.assertEquals(errorText, getData().getTotpErrorMessage(), "TOTP error message is missing or incorrect");

        LogUtility.infoLog("Generated unique email: " + uniqueEmail);
    }
}
