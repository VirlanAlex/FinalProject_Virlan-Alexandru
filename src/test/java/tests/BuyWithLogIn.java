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

        checkoutPage.fillMissingAddressFieldsIfNeeded(getData().getRegister().getPostCode(), getData().getRegister().getState());

        checkoutPage.proceedStep3();
        checkoutPage.selectCashOnDelivery();
        checkoutPage.finishOrder();

        String successText = checkoutPage.getSuccessMessage();
        Assert.assertEquals(successText, getData().getPaymentSuccessMessage(), "Payment success message is incorrect or missing");

        new AlertMethods(driver).showAndValidateAndAccept(successText);
    }
}
