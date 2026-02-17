package tests;

import helpMethods.AlertMethods;
import modelObject.UserModel;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import sharedData.SharedData;
import utils.LogUtility;

public class BuyWithoutLogIn extends SharedData {

    @Test
    public void buyProducts() {

        LogUtility.infoLog("Test flow: Buy without login (product -> cart -> login in checkout -> payment)");

        UserModel user = new UserModel(getData().getValidEmail(), getData().getValidPassword());

        HomePage homePage = new HomePage(driver);
        HeaderComponent header = new HeaderComponent(driver);
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        SignInPage signInPage = new SignInPage(driver);

        driver.get(url("/"));
        LogUtility.infoLog("Open homepage");
        homePage.openCombinationPliers();

        LogUtility.infoLog("Add product to cart");
        productDetailsPage.addToCart();
        header.clickCart();

        LogUtility.infoLog("Proceed to checkout Step 1");
        checkoutPage.proceedStep1();

        LogUtility.infoLog("Login during checkout");
        signInPage.login(user);

        LogUtility.infoLog("Proceed to checkout Step 2 (Address)");
        checkoutPage.proceedStep2();

        LogUtility.infoLog("Fill missing address fields if needed");
        checkoutPage.fillMissingAddressFieldsIfNeeded("725200", "Suceava");

        LogUtility.infoLog("Proceed to checkout Step 3 (Payment)");
        checkoutPage.proceedStep3();
        checkoutPage.selectCashOnDelivery();
        checkoutPage.finishOrder();

        String successText = checkoutPage.getSuccessMessage();
        LogUtility.infoLog("Verify success message is displayed");
        Assert.assertEquals(successText, getData().getPaymentSuccessMessage(), "Payment success message is incorrect or missing");

        new AlertMethods(driver).showAndValidateAndAccept(successText);
    }
}