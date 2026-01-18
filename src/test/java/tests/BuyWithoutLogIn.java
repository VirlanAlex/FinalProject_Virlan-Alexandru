package tests;

import org.openqa.selenium.By;
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

public class BuyWithoutLogIn {
    public WebDriver driver;

    @Test
    public void buyProducts(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.get("https://practicesoftwaretesting.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement selectProductElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("img.card-img-top[alt='Combination Pliers']")));
        selectProductElement.click();

        WebElement addToCartElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[data-test='add-to-cart']")));
        addToCartElement.click();

        WebElement cartIconElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("svg[data-icon='cart-shopping']:not(button[data-test='add-to-cart'] svg)")));
        cartIconElement.click();

        WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-test='proceed-1']")));
        checkoutButton.click();

        WebElement emailInputElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#email")));
        emailInputElement.sendKeys("virlanalexandru20@yahoo.com");
        Assert.assertTrue(emailInputElement.isDisplayed(), "Login page not shown: email input is missing");

        WebElement passwordInputElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#password")));
        passwordInputElement.sendKeys("123sd21123@asdadd2Asdsd");
        Assert.assertTrue(passwordInputElement.isDisplayed(), "Login page not shown: password input is missing");

        WebElement logInButtonElement = driver.findElement(By.cssSelector("input.btnSubmit"));
        logInButtonElement.click();
        wait.until(ExpectedConditions.urlContains("/checkout"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/checkout"), "User is not logged in: account page was not loaded");

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
