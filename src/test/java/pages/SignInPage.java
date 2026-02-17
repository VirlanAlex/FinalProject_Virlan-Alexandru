package pages;

import modelObject.UserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignInPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(SignInPage.class);

    private final By emailInput = By.cssSelector("input#email");
    private final By passwordInput = By.cssSelector("input#password");
    private final By loginButton = By.cssSelector("input.btnSubmit");

    private final By loginError = By.cssSelector(".alert-danger, .invalid-feedback, [role='alert']");

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    public void login(UserModel user) {
        logger.info("Login: fill credentials and submit (email={})", user.getEmail());
        elements.visible(emailInput);
        elements.visible(passwordInput);

        elements.type(emailInput, user.getEmail());
        elements.type(passwordInput, user.getPassword());
        elements.click(loginButton);
    }

    public void waitRedirectTo(String partialUrl) {
        elements.waitUrlContains(partialUrl);
    }

    public void loginAndAssert(UserModel user, String accountUrlPart) {
        String before = driver.getCurrentUrl();

        login(user);

        elements.waitUntil(d -> !d.getCurrentUrl().equals(before) || elements.isPresent(loginError));

        if (driver.getCurrentUrl().contains(accountUrlPart)) return;

        String err = elements.firstText(loginError);
        logger.error("Login failed. Current url: {} | UI says: {}", driver.getCurrentUrl(), err);
        throw new AssertionError("Login failed. Current url: " + driver.getCurrentUrl()
                + (err.isBlank() ? "" : " | UI says: " + err));
    }
}