package tests;

import helpMethods.AlertMethods;
import modelObject.UserModel;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import sharedData.SharedData;
import utils.LogUtility;

public class BuyWithLogIn extends SharedData {

    @Test
    public void buyWithLogin() {
        LogUtility.infoLog("Test flow: Buy with login (login -> product -> cart -> checkout -> payment)");
        driver.get(url("/auth/login"));
        LogUtility.infoLog("Open login page");
        UserModel user = new UserModel(getData().getValidEmail(), getData().getValidPassword());
        new SignInPage(driver).loginAndAssert(user, getData().getAccountUrlPart());
        LogUtility.infoLog("Login validated");

        HeaderComponent header = new HeaderComponent(driver);
        HomePage homePage = new HomePage(driver);
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        header.clickLogo();
        LogUtility.infoLog("Navigate back to homepage");
        homePage.openCombinationPliers();
        LogUtility.infoLog("Open product and add to cart");
        productDetailsPage.addToCart();
        header.clickCart();

        LogUtility.infoLog("Checkout Step 1");
        checkoutPage.proceedStep1();
        LogUtility.infoLog("Checkout Step 2 (Address)");
        checkoutPage.proceedStep2();
        checkoutPage.fillMissingAddressFieldsIfNeeded("725200", "Suceava");
        LogUtility.infoLog("Checkout Step 3 (Payment)");
        checkoutPage.proceedStep3();
        checkoutPage.selectCashOnDelivery();
        checkoutPage.finishOrder();

        String successText = checkoutPage.getSuccessMessage();
        LogUtility.infoLog("Verify success message is displayed");
        Assert.assertEquals(successText, getData().getPaymentSuccessMessage(), "Payment success message is incorrect or missing");

        new AlertMethods(driver).showAndValidateAndAccept(successText);
    }
}