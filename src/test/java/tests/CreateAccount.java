package tests;

import pages.HeaderComponent;
import pages.RegisterPage;
import modelObject.RegisterUserModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;
import java.time.Duration;

public class CreateAccount {
    public WebDriver driver;

    @Test
    public void startAccount() {
        RegisterUserModel user = new RegisterUserModel("Alexandru", "Virlan", "1987-08-20", "Aleea Trandafirilor, bl 4, sc A", "725200", "Falticeni", "Suceava", "Romania", "0748674628", "virlanalexandru20+" + System.currentTimeMillis() + "@yahoo.com", "123sd21123@asdadd2Asdsd");
        startAccount(user);
    }

    public void startAccount(RegisterUserModel user) {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.get("https://practicesoftwaretesting.com/");

        HeaderComponent header = new HeaderComponent(driver);
        RegisterPage registerPage = new RegisterPage(driver);

        header.clickSignIn();

        registerPage.openRegisterForm();

        registerPage.register(user);
    }
}

