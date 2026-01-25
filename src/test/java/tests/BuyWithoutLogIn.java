package tests;

import pages.*;
import helpMethods.AlertMethods;
import modelObject.UserModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;


public class BuyWithoutLogIn {
    public WebDriver driver;

    @Test
    public void buyProducts() {

        UserModel user = new UserModel("virlanalexandru20@yahoo.com", "123sd21123@asdadd2Asdsd");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.get("https://practicesoftwaretesting.com/");
        HomePage homePage = new HomePage(driver);
        HeaderComponent header = new HeaderComponent(driver);
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        SignInPage signInPage = new SignInPage(driver);

        homePage.openCombinationPliers();
        productDetailsPage.addToCart();
        header.clickCart();

        checkoutPage.proceedStep1();

        signInPage.login(user);
        signInPage.waitRedirectTo("/checkout");

        Assert.assertTrue(driver.getCurrentUrl().contains("/checkout"), "User is not logged in: account page was not loaded");

        checkoutPage.proceedStep2();
        checkoutPage.proceedStep3();

        checkoutPage.selectCashOnDelivery();
        checkoutPage.finishOrder();

        String successText = checkoutPage.getSuccessMessage();
        Assert.assertEquals(successText, "Payment was successful", "Payment success message is incorrect or missing");

        AlertMethods alertMethods = new AlertMethods(driver);
        alertMethods.showAndValidateAndAccept(successText);
    }
}