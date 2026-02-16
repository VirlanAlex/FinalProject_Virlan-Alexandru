package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    private final By proceedStep1Button = By.cssSelector("button[data-test='proceed-1']");
    private final By proceedStep2Button = By.cssSelector("button[data-test='proceed-2']");
    private final By proceedStep3Button = By.cssSelector("button[data-test='proceed-3']");
    private final By paymentMethodSelect = By.cssSelector("select[data-test='payment-method']");
    private final By finishButton = By.cssSelector("button[data-test='finish']");
    private final By successMessage = By.cssSelector("div[data-test='payment-success-message']");
    private final By postalCodeInput = By.id("postal_code");
    private final By stateInput = By.id("state");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void proceedStep1() {
        elements.click(proceedStep1Button);
    }

    public void proceedStep2() {
        elements.click(proceedStep2Button);
    }

    public void proceedStep3() {
        elements.click(proceedStep3Button);
    }

    public void fillMissingAddressFieldsIfNeeded(String postalCode, String state) {

        if (!elements.isPresent(postalCodeInput) || !elements.isPresent(stateInput)) {
            return;
        }

        if (elements.getValue(postalCodeInput).isBlank()) {
            elements.type(postalCodeInput, postalCode);
        }

        if (elements.getValue(stateInput).isBlank()) {
            elements.type(stateInput, state);
        }
    }

    public void selectCashOnDelivery() {
        elements.selectByValue(paymentMethodSelect, "cash-on-delivery");
    }

    public void finishOrder() {
        elements.click(finishButton);
    }

    public String getSuccessMessage() {
        return elements.visible(successMessage).getText().trim();
    }
}