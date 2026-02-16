package pages;

import modelObject.UserModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignInPage extends BasePage {

    private final By emailInput = By.cssSelector("input#email");
    private final By passwordInput = By.cssSelector("input#password");
    private final By loginButton = By.cssSelector("input.btnSubmit");

    // ✅ strict: doar erori reale (nu .alert generic)
    private final By loginError = By.cssSelector(".alert-danger, .invalid-feedback, [role='alert']");

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    public void login(UserModel user) {
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

        // așteaptă o schimbare reală: URL s-a schimbat sau apare eroare
        elements.waitUntil(d -> !d.getCurrentUrl().equals(before) || elements.isPresent(loginError));

        if (driver.getCurrentUrl().contains(accountUrlPart)) return;

        String err = elements.firstText(loginError);
        throw new AssertionError("Login failed. Current url: " + driver.getCurrentUrl()
                + (err.isBlank() ? "" : " | UI says: " + err));
    }
}