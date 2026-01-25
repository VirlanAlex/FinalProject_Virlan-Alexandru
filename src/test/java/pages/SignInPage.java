package pages;

import modelObject.UserModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class SignInPage extends BasePage {
    private final By emailInput = By.cssSelector("input#email");
    private final By passwordInput = By.cssSelector("input#password");
    private final By loginButton = By.cssSelector("input.btnSubmit");

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    public void login(UserModel user) {
        elements.type(emailInput, user.getEmail());
        elements.type(passwordInput, user.getPassword());
        elements.click(loginButton);
    }
    public void waitRedirectTo(String partialUrl) {
        elements.waitUrlContains(partialUrl);
    }
}
