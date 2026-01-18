package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class BuyWithLogIn {
    public WebDriver driver;

    @Test
    private void signInAccount() {
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

        WebElement logoLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.navbar-brand[title='Practice Software Testing - Toolshop'][href='/']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", logoLink);

        WebElement selectProductElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("img.card-img-top[alt='Combination Pliers']")));
        selectProductElement.click();

        WebElement addToCartElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[data-test='add-to-cart']")));
        addToCartElement.click();

        WebElement cartIconElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("svg[data-icon='cart-shopping']:not(button[data-test='add-to-cart'] svg)")));
        cartIconElement.click();

        WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-test='proceed-1']")));
        checkoutButton.click();

        WebElement proceedToCheckoutStep2Element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-test='proceed-2']")));
        proceedToCheckoutStep2Element.click();

        WebElement proceedToCheckoutStep3Element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-test='proceed-3']")));
        proceedToCheckoutStep3Element.click();

        WebElement paymentMethodDropdownElement = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("select[data-test='payment-method']")));
        Select paymentMethodSelect = new Select(paymentMethodDropdownElement);
        paymentMethodSelect.selectByValue("cash-on-delivery");
        Assert.assertEquals(paymentMethodSelect.getFirstSelectedOption().getAttribute("value"), "cash-on-delivery", "Payment method was not selected correctly");

        WebElement confirmButtonElement = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-test='finish']")));
        confirmButtonElement.click();

        WebElement successMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[data-test='payment-success-message']")));
        Assert.assertEquals(successMessageElement.getText().trim(), "Payment was successful", "Payment success message is incorrect or missing");

    }
}
