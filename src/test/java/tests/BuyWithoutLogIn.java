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

        driver.get(url("/"));

        UserModel user = new UserModel(getData().getValidEmail(), getData().getValidPassword());

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

        checkoutPage.proceedStep2();

        checkoutPage.fillMissingAddressFieldsIfNeeded(getData().getRegister().getPostCode(), getData().getRegister().getState());

        checkoutPage.proceedStep3();
        checkoutPage.selectCashOnDelivery();
        checkoutPage.finishOrder();

        String successText = checkoutPage.getSuccessMessage();
        Assert.assertEquals(successText, getData().getPaymentSuccessMessage(), "Payment success message is incorrect or missing");

        new AlertMethods(driver).showAndValidateAndAccept(successText);
    }
}
