package tests;

import pages.*;
import modelObject.UserModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;
import helpMethods.AlertMethods;
import sharedData.SharedData;

public class BuyWithLogIn {
    public WebDriver driver;

    @Test
    public void signInAccount() {

        SharedData data = new SharedData();
        UserModel user = new UserModel(data.getValidEmail(), data.getValidPassword());
        signInAccount(user, data);
    }
    public void signInAccount(UserModel user, SharedData data) {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.get(data.getBaseUrl());
        HeaderComponent header = new HeaderComponent(driver);
        SignInPage signInPage = new SignInPage(driver);
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        HomePage homePage = new HomePage(driver);

        header.clickSignIn();
        signInPage.login(user);
        signInPage.waitRedirectTo(data.getAccountUrlPart());

        Assert.assertTrue(driver.getCurrentUrl().contains(data.getAccountUrlPart()), "Login failed: user was not redirected to account page");

        header.clickLogo();

        homePage.openCombinationPliers();
        productDetailsPage.addToCart();

        header.clickCart();

        checkoutPage.proceedStep1();
        checkoutPage.proceedStep2();
        checkoutPage.proceedStep3();
        checkoutPage.selectCashOnDelivery();
        checkoutPage.finishOrder();

        String successText = checkoutPage.getSuccessMessage();

        Assert.assertEquals(successText, data.getPaymentSuccessMessage(), "Payment success message is incorrect or missing");

        AlertMethods alertMethods = new AlertMethods(driver);
        alertMethods.showAndValidateAndAccept(successText);
    }
}