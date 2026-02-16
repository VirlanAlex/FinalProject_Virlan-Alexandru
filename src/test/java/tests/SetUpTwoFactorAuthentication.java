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

import java.time.Duration;

public class SetUpTwoFactorAuthentication extends SharedData {

    @Test
    public void setUpAuthentication() {
        // 1) Email unic + parola dedicată contului creat
        String uniqueEmail = getData().getRegister().getEmailPrefix() + System.currentTimeMillis() + getData().getRegister().getEmailDomain();
        String regPass = getData().getRegister().getRegisterPassword();

        // 2) Register
        driver.get(url("/auth/login"));

        RegisterUserModel registerUser = new RegisterUserModel(getData().getRegister().getFirstName(), getData().getRegister().getLastName(), getData().getRegister().getDateOfBirth(), getData().getRegister().getStreet(), getData().getRegister().getPostCode(), getData().getRegister().getCity(), getData().getRegister().getState(), getData().getRegister().getCountry(), getData().getRegister().getPhone(), uniqueEmail, regPass);
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.openRegisterForm();
        registerPage.register(registerUser);

        // 3) Assert după register
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> d.getCurrentUrl().contains("/auth/login") || d.getCurrentUrl().contains("/account"));

        // 4) Login cu contul creat
        driver.get(url("/auth/login"));
        UserModel loginUser = new UserModel(uniqueEmail, regPass);
        SignInPage signInPage = new SignInPage(driver);
        signInPage.loginAndAssert(loginUser, getData().getAccountUrlPart());

        // 5) 2FA
        HeaderComponent header = new HeaderComponent(driver);
        ProfilePage profilePage = new ProfilePage(driver);
        header.clickProfile();
        String secret = profilePage.getTotpSecret();
        Assert.assertTrue(secret.matches("[A-Z0-9]{8,64}"), "TOTP secret invalid/empty: '" + secret + "'");

        // intenționat invalid
        profilePage.enterTotpCode(secret);
        profilePage.verifyTotpCode();
        String errorText = profilePage.getTotpErrorMessage();
        Assert.assertEquals(errorText, getData().getTotpErrorMessage(), "TOTP error message is missing or incorrect");
    }
}