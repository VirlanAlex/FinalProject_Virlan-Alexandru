package tests;

import helpMethods.AlertMethods;
import modelObject.UserModel;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import sharedData.SharedData;

public class BuyWithoutLogIn extends SharedData {

    @Test
    public void buyProducts() {

        UserModel user = new UserModel(getData().getValidEmail(), getData().getValidPassword());

        HomePage homePage = new HomePage(driver);
        HeaderComponent header = new HeaderComponent(driver);
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        SignInPage signInPage = new SignInPage(driver);

        driver.get(url("/"));
        homePage.openCombinationPliers();
        productDetailsPage.addToCart();
        header.clickCart();

        checkoutPage.proceedStep1();

        signInPage.login(user);

        checkoutPage.proceedStep2();

        checkoutPage.fillMissingAddressFieldsIfNeeded("725200", "Suceava");

        checkoutPage.proceedStep3();
        checkoutPage.selectCashOnDelivery();
        checkoutPage.finishOrder();

        String successText = checkoutPage.getSuccessMessage();
        Assert.assertEquals(successText, getData().getPaymentSuccessMessage(), "Payment success message is incorrect or missing");

        new AlertMethods(driver).showAndValidateAndAccept(successText);
    }
}