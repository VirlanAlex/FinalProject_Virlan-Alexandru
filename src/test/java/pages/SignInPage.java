package pages;

import modelObject.UserModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignInPage extends BasePage {

    private final By emailInput = By.cssSelector("input#email");
    private final By passwordInput = By.cssSelector("input#password");
    private final By loginButton = By.cssSelector("input.btnSubmit");
    private final By loginError = By.cssSelector(".alert-danger, .invalid-feedback, [role='alert']");

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    public void login(UserModel user) {
        logStep("Login (email=" + user.getEmail() + ")");
        elements.visible(emailInput);
        elements.visible(passwordInput);
        elements.type(emailInput, user.getEmail());
        elements.type(passwordInput, user.getPassword());
        elements.click(loginButton);
    }

    public void loginAndAssert(UserModel user, String accountUrlPart) {
        String before = driver.getCurrentUrl();
        login(user);

        elements.waitUntil(d -> !d.getCurrentUrl().equals(before) || elements.isPresent(loginError));

        if (driver.getCurrentUrl().contains(accountUrlPart)) {
            logStep("Login validated");
            return;
        }

        String err = elements.firstText(loginError);
        logError("Login failed. Current url: " + driver.getCurrentUrl() + (err.isBlank() ? "" : " | UI says: " + err));
        throw new AssertionError("Login failed. Current url: " + driver.getCurrentUrl()
                + (err.isBlank() ? "" : " | UI says: " + err));
    }
}
