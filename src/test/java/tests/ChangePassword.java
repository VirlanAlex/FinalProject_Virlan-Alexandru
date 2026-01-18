package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class ChangePassword {
    public WebDriver driver;
    JavascriptExecutor js = (JavascriptExecutor) driver;


    @Test
    private void signInAccount() {
        signInAccount("virlanalexandru20@yahoo.com", "123sd21123@asdadd2Asds1");
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

        WebElement currentPasswordInput = driver.findElement(By.cssSelector("input[data-test='current-password']"));
        currentPasswordInput.clear();
        currentPasswordInput.sendKeys("123sd21123@asdadd2Asds1");

        WebElement newPasswordInput = driver.findElement(By.cssSelector("input[data-test='new-password']"));
        newPasswordInput.clear();
        newPasswordInput.sendKeys("123sd21123@asdadd2Asds2");

        WebElement confirmNewPasswordInput = driver.findElement(By.cssSelector("input[data-test='new-password-confirm']"));
        confirmNewPasswordInput.clear();
        confirmNewPasswordInput.sendKeys("123sd21123@asdadd2Asds2");

        WebElement changePasswordButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[data-test='change-password-submit']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", changePasswordButton);

    }
}
