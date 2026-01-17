package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class LogIn {
    public WebDriver driver;

    @Test
    private void signInAccount(){
        signInAccount("virlanalexandru20@yahoo.com","123sd21123@asdadd2Asdsd");
    }

    public void signInAccount(String signInEmail, String signInPassword){
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
    }
}
