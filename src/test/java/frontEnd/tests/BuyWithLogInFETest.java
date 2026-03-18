package frontEnd.tests;

import frontEnd.pages.*;
import frontEnd.helpMethods.AlertMethods;
import frontEnd.modelObject.UserModel;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import frontEnd.sharedData.SharedData;
import frontEnd.utils.LogUtility;

@Feature("@FEATURE - BUY")
@Story("@STORY - BUY WITH LOGIN OPERATIONS")
public class BuyWithLogInFETest extends SharedData {

    @Test(description = "Buy with login workflow")
    public void buyWithLogin() {
        LogUtility.infoLog("Test flow: Buy with login (login -> product -> cart -> checkout -> payment)");

        HeaderComponent header = new HeaderComponent(driver);
        header.clickSignIn();

        UserModel user = new UserModel(getData().getValidEmail(), getData().getValidPassword());
        new SignInPage(driver).loginAndAssert(user, getData().getAccountUrlPart());

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
