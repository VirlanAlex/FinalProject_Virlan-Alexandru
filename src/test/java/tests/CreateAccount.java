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

public class CreateAccount {
    public WebDriver driver;

    @Test
    private void startAccount() {
        startAccount("Alexandru", "Virlan", "1987-08-20", "Aleea Trandafirilor,bl 4, sc A", "725200", "Falticeni", "Suceava", "Romania", "0748674628", "virlanalexandru20@yahoo.com", "123sd21123@asdadd2Asdsd");
    }

    public void startAccount(String firstName, String lastName, String dateOfBirth, String street, String postCode, String city, String state, String country, String phone, String email, String password) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.get("https://practicesoftwaretesting.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement clickSignInElement = driver.findElement(By.cssSelector("a[data-test='nav-sign-in']"));
        clickSignInElement.click();

        WebElement registerYourAccountElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[data-test='register-link']")));
        registerYourAccountElement.click();

        WebElement firstNameElement = driver.findElement(By.cssSelector("input#first_name"));
        firstNameElement.sendKeys(firstName);

        WebElement lastNameElement = driver.findElement(By.cssSelector("input#last_name"));
        lastNameElement.sendKeys(lastName);

        WebElement dateOfBirthElement = driver.findElement(By.cssSelector("input#dob"));
        dateOfBirthElement.sendKeys(dateOfBirth);

        WebElement streetElement = driver.findElement(By.cssSelector("input#street"));
        streetElement.sendKeys(street);

        WebElement postCodeElement = driver.findElement(By.cssSelector("input#postal_code"));
        postCodeElement.sendKeys(postCode);

        WebElement cityElement = driver.findElement(By.cssSelector("input#city"));
        cityElement.sendKeys(city);

        WebElement stateElement = driver.findElement(By.cssSelector("input#state"));
        stateElement.sendKeys(state);

        WebElement countryElement = driver.findElement(By.cssSelector("select[data-test='country']"));
        countryElement.sendKeys(country);

        WebElement phoneElement = driver.findElement(By.cssSelector("input#phone"));
        phoneElement.sendKeys(phone);

        WebElement registerEmailElement = driver.findElement(By.cssSelector("input#email"));
        registerEmailElement.sendKeys(email);

        WebElement registerPasswordElement = driver.findElement(By.cssSelector("input#password"));
        registerPasswordElement.sendKeys(password);

        WebElement clickRegisterElement = driver.findElement(By.cssSelector("button.btnSubmit"));
        clickRegisterElement.click();


    }
}

