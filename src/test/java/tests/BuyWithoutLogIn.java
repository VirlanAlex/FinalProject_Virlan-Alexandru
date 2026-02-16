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

        // 1️⃣ Guest – add product
        driver.get(url("/"));
        homePage.openCombinationPliers();
        productDetailsPage.addToCart();
        header.clickCart();

        // 2️⃣ Cart → Sign In step
        checkoutPage.proceedStep1();

        // 3️⃣ Login (suntem în funnel /checkout, pasul SIGN IN)
        signInPage.login(user);

        // 4️⃣ Sign In → Billing Address (încă un proceed)
        checkoutPage.proceedStep2();

        // 5️⃣ Completezi câmpurile lipsă (acum există!)
        checkoutPage.fillMissingAddressFieldsIfNeeded("725200", "Suceava");

        // 6️⃣ Continue flow
        checkoutPage.proceedStep3();
        checkoutPage.selectCashOnDelivery();
        checkoutPage.finishOrder();

        String successText = checkoutPage.getSuccessMessage();
        Assert.assertEquals(successText, getData().getPaymentSuccessMessage(),
                "Payment success message is incorrect or missing");

        new AlertMethods(driver).showAndValidateAndAccept(successText);
    }
}