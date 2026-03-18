package backEnd.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import backEnd.utils.LogUtility;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "email")
        private WebElement emailElement;

    @FindBy(id = "password")
        private WebElement passwordElement;

    @FindBy(xpath = "//input[@type='submit']")
    private WebElement loginButtonElement;

    public void loginProcess(String email, String password){
        emailElement.sendKeys(email);
        LogUtility.infoLog("The user fills email field with " + email + "value");
        passwordElement.sendKeys(password);
        LogUtility.infoLog("The user fills email field with " + password + "value");
        loginButtonElement.click();
        LogUtility.infoLog("The user clicks on login button ");

    }
}
