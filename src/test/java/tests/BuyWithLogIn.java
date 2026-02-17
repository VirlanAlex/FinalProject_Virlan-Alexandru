package tests;

import helpMethods.AlertMethods;
import modelObject.UserModel;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import sharedData.SharedData;

public class BuyWithLogIn extends SharedData {

    @Test
    public void buyWithLogin() {
        driver.get(url("/auth/login"));
        UserModel user = new UserModel(getData().getValidEmail(), getData().getValidPassword());
        new SignInPage(driver).loginAndAssert(user, getData().getAccountUrlPart());

        HeaderComponent header = new HeaderComponent(driver);
        HomePage homePage = new HomePage(driver);
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        header.clickLogo();
        homePage.openCombinationPliers();
        productDetailsPage.addToCart();
        header.clickCart();

        checkoutPage.proceedStep1();
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