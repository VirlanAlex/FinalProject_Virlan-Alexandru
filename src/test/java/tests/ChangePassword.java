package tests;

import pages.HeaderComponent;
import pages.ProfilePage;
import pages.SignInPage;
import modelObject.UserModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class ChangePassword {
    public WebDriver driver;

    @Test
    public void signInAccount() {
        UserModel user = new UserModel("virlanalexandru20@yahoo.com", "123sd21123@asdadd2Asdsd", "123sd21123@asdadd2Asdsd", "123sd21123@asdadd2Asds2");
        signInAccount(user);
    }

    public void signInAccount(UserModel user) {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.get("https://practicesoftwaretesting.com/");
        HeaderComponent header = new HeaderComponent(driver);
        SignInPage signInPage = new SignInPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);

        header.clickSignIn();
        signInPage.login(user);
        signInPage.waitRedirectTo("/account");

        Assert.assertTrue(driver.getCurrentUrl().contains("/account"), "Login failed: user was not redirected to account page");

        header.clickProfile();

        profilePage.changePassword(user);

    }
}