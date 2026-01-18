package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class SetUpTwoFactorAuthentication {

    public WebDriver driver;

    @Test
    private void setUpAuthentication() {
        signInAccount("virlanalexandru20@yahoo.com", "123sd21123@asdadd2Asdsd");
    }

    public void signInAccount(String signInEmail, String signInPassword) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.get("https://practicesoftwaretesting.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement clickSignInElement = driver.findElement(By.cssSelector("a[data-test='nav-sign-in']"));
        clickSignInElement.click();

        WebElement signInEmailElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input#email")));
        signInEmailElement.sendKeys(signInEmail);

        WebElement signInPasswordElement = driver.findElement(By.cssSelector("input#password"));
        signInPasswordElement.sendKeys(signInPassword);

        WebElement logInButtonElement = driver.findElement(By.cssSelector("input.btnSubmit"));
        logInButtonElement.click();
        wait.until(ExpectedConditions.urlContains("/account"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/account"), "Login failed: user was not redirected to account page");

        WebElement profileButton = driver.findElement(By.cssSelector(("[data-test='nav-profile']")));
        profileButton.click();

        By allTextSecret = By.cssSelector("[data-test='totp-secret'] strong");
        By allTextInput  = By.cssSelector("input[data-test='totp-code']");
        String secret = wait.until(context -> {
            WebElement secretText = context.findElement(allTextSecret);
            String textText = secretText.getAttribute("textContent");
            if (textText == null) return null;
            textText = textText.trim();
            return textText.isEmpty() ? null : textText;
        });
        Assert.assertTrue(secret.matches("[A-Z0-9]{8,64}"), "TOTP secret invalid/empty: '" + secret + "'");
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(allTextInput));
        input.clear();
        input.sendKeys(secret);

        WebElement verifyButtonElement = driver.findElement(By.cssSelector("button[data-test='verify-totp']"));
        verifyButtonElement.click();

        By textError = By.cssSelector("[data-test='totp-error']");
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(textError));
        Assert.assertEquals(errorMessage.getText().trim(), "Invalid TOTP code. Please try again.", "TOTP error message is missing or incorrect");
    }
}
