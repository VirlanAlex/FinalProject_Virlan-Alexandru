package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(CheckoutPage.class);
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
        logger.info("Checkout: proceed to Step 1");
        elements.click(proceedStep1Button);
    }

    public void proceedStep2() {
        logger.info("Checkout: proceed to Step 2 (Address)");
        elements.click(proceedStep2Button);
    }

    public void proceedStep3() {
        logger.info("Checkout: proceed to Step 3 (Payment)");
        elements.click(proceedStep3Button);
    }

    
    public void fillMissingAddressFieldsIfNeeded(String postalCode, String state) {
        if (!elements.isPresent(postalCodeInput) || !elements.isPresent(stateInput)) {
            logger.info("Checkout: address inputs are not present - skip filling");
            return;
        }
        elements.visible(postalCodeInput);
        elements.visible(stateInput);
        elements.waitUntilValueStabilizes(postalCodeInput);
        elements.waitUntilValueStabilizes(stateInput);
        logger.info("Checkout: ensure address fields are filled (postal_code/state)");
        setIfBlankAndEnsure(postalCodeInput, postalCode);
        setIfBlankAndEnsure(stateInput, state);
    }

    private void setIfBlankAndEnsure(By locator, String value) {
        String current = elements.getValue(locator);
        if (current != null && !current.isBlank()) {
            logger.info("Checkout: '{}' already filled - keep existing value", locator);
            return;
        }
        for (int i = 0; i < 2; i++) {
            if (i == 1) {
                logger.warn("Checkout: field '{}' was overwritten after blur - retrying", locator);
            }
            elements.type(locator, value);
            elements.pressTab(locator);
            try {
                elements.waitUntilValueEquals(locator, value);
                elements.waitUntilValueStabilizes(locator);
                return;
            } catch (RuntimeException ignored) {
            }
        }
        throw new AssertionError("Field value could not be stabilized for locator: " + locator);
    }

    public void selectCashOnDelivery() {
        logger.info("Checkout: select payment method = cash-on-delivery");
        elements.selectByValue(paymentMethodSelect, "cash-on-delivery");
    }

    public void finishOrder() {
        logger.info("Checkout: finish order");
        elements.click(finishButton);
    }

    public String getSuccessMessage() {
        return elements.visible(successMessage).getText().trim();
    }
}