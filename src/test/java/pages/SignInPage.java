package pages;

import modelObject.UserModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignInPage extends BasePage {

    private final By emailInput    = By.cssSelector("input[data-test='email']");
    private final By passwordInput = By.cssSelector("input[data-test='password']");
    private final By loginButton   = By.cssSelector("[data-test='login-submit']");
    private final By loginError    = By.cssSelector(".alert-danger, .invalid-feedback, [role='alert']");

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
        elements.waitUrlContains("/auth/login");
        login(user);
        elements.waitUrlContains(accountUrlPart);
        logStep("Login validated — URL contains: " + accountUrlPart);
    }
}
