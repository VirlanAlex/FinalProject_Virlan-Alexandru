package pages;

import modelObject.UserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage extends BasePage {

    private static final Logger logger = LogManager.getLogger(ProfilePage.class);

    // -------- Change Password --------
    private final By currentPasswordInput = By.cssSelector("input[data-test='current-password']");
    private final By newPasswordInput = By.cssSelector("input[data-test='new-password']");
    private final By confirmNewPasswordInput = By.cssSelector("input[data-test='new-password-confirm']");
    private final By changePasswordButton = By.cssSelector("button[data-test='change-password-submit']");

    // -------- Two Factor Authentication (TOTP) --------
    private final By totpSecretLocator = By.cssSelector("[data-test='totp-secret'] strong");
    private final By totpInputLocator = By.cssSelector("input[data-test='totp-code']");
    private final By verifyTotpButton = By.cssSelector("button[data-test='verify-totp']");
    private final By totpErrorLocator = By.cssSelector("[data-test='totp-error']");

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    public void changePassword(UserModel user) {
        logger.info("Profile: change password");
        elements.type(currentPasswordInput, user.getCurrentPassword());
        elements.type(newPasswordInput, user.getNewPassword());
        elements.type(confirmNewPasswordInput, user.getNewPassword());

        elements.jsClick(changePasswordButton);
    }

    public String getTotpSecret() {
        logger.info("Profile: get TOTP secret");
        return elements.waitNonEmptyText(totpSecretLocator);
    }

    public void enterTotpCode(String code) {
        logger.info("Profile: enter TOTP code");
        elements.type(totpInputLocator, code);
    }

    public void verifyTotpCode() {
        logger.info("Profile: verify TOTP code");
        elements.click(verifyTotpButton);
    }

    public String getTotpErrorMessage() {
        return elements.visible(totpErrorLocator).getText().trim();
    }
}

