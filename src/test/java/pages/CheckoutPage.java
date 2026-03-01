package pages;

import org.openqa.selenium.By;
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
        logStep("Proceed to checkout step 1");
        elements.click(proceedStep1Button);
    }

    public void proceedStep2() {
        logStep("Proceed to checkout step 2 (Address)");
        elements.waitForPageReady();
        elements.click(proceedStep2Button);
    }

    public void proceedStep3() {
        logStep("Proceed to checkout step 3 (Payment)");
        elements.click(proceedStep3Button);
    }

    public void fillMissingAddressFieldsIfNeeded(String postalCode, String state) {
        if (!elements.isPresent(postalCodeInput) || !elements.isPresent(stateInput)) {
            logStep("Address inputs not present -> skip autofill");
            return;
        }

        elements.visible(postalCodeInput);
        elements.visible(stateInput);

        elements.waitUntilValueStabilizes(postalCodeInput);
        elements.waitUntilValueStabilizes(stateInput);

        boolean postalWasSet = setIfBlankAndEnsure(postalCodeInput, postalCode, "postal_code");
        boolean stateWasSet = setIfBlankAndEnsure(stateInput, state, "state");

        if (postalWasSet || stateWasSet) {
            logStep("Autofilled missing address fields");
        }
    }

    private boolean setIfBlankAndEnsure(By locator, String value, String fieldName) {
        String current = elements.getValue(locator);
        if (current != null && !current.isBlank()) {
            return false;
        }

        for (int attempt = 0; attempt < 2; attempt++) {
            if (attempt == 1) {
                logWarn("Field '" + fieldName + "' was overwritten after blur -> retry once");
            }
            elements.type(locator, value);
            elements.pressTab(locator);

            try {
                elements.waitUntilValueEquals(locator, value);
                elements.waitUntilValueStabilizes(locator);
                return true;
            } catch (RuntimeException ignored) {
                // retry once
            }
        }

        throw new AssertionError("Field value could not be stabilized for: " + fieldName);
    }

    public void selectCashOnDelivery() {
        logStep("Select payment method: cash-on-delivery");
        elements.selectByValue(paymentMethodSelect, "cash-on-delivery");
    }

    public void finishOrder() {
        logStep("Finish order");
        elements.click(finishButton);
    }

    public String getSuccessMessage() {
        return elements.visible(successMessage).getText().trim();
    }
}
