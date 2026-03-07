package tests;

import modelObject.RegisterUserModel;
import modelObject.UserModel;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
        RegisterUserModel registerUser = RegisterUserModel.fromRegisterData(getData().getRegister(), uniqueEmail);
        String regPass = getData().getRegister().getRegisterPassword();

        // Pasul 1: navigare interna Angular la pagina de sign in
        HeaderComponent header = new HeaderComponent(driver);
        header.clickSignIn();

        // Pasul 2: deschide formularul de inregistrare si inregistreaza utilizatorul
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.openRegisterForm();
        registerPage.register(registerUser);

        // Pasul 3: asteapta sa ajunga pe pagina de login dupa inregistrare
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("a[data-test='nav-sign-in'], input#email")
                ));

        // Pasul 4: daca nu suntem deja pe pagina de login, click Sign In
        if (!driver.getCurrentUrl().contains("/auth/login")) {
            header.clickSignIn();
        }

        // Pasul 5: login cu noul user inregistrat
        UserModel loginUser = new UserModel(uniqueEmail, regPass);
        new SignInPage(driver).loginAndAssert(loginUser, getData().getAccountUrlPart());

        // Pasul 6: TOTP setup
        ProfilePage profilePage = new ProfilePage(driver);
        header.clickProfile();

        String secret = profilePage.getTotpSecret();
        Assert.assertTrue(secret.matches("[A-Z0-9]{8,64}"), "TOTP secret invalid/empty: '" + secret + "'");

        profilePage.enterTotpCode(secret);
        profilePage.verifyTotpCode();

        String errorText = profilePage.getTotpErrorMessage();
        Assert.assertEquals(errorText, getData().getTotpErrorMessage(), "TOTP error message is missing or incorrect");

        LogUtility.infoLog("Generated unique email for registration: " + uniqueEmail);
    }
}
