package frontEnd.tests;

import frontEnd.pages.*;
import frontEnd.helpMethods.AlertMethods;
import frontEnd.modelObject.UserModel;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import frontEnd.sharedData.SharedData;

@Feature("@FEATURE - BUY")
@Story("@STORY - BUY WITHOUT LOGIN OPERATIONS")
public class BuyWithoutLogInFETest extends SharedData {

    @Test(description = "Test flow: Buy without login (product -> cart -> login in checkout -> payment)")
    public void buyProducts() {
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
