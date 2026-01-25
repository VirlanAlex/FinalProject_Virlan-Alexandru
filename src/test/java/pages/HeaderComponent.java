package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HeaderComponent extends BasePage {
    private final By signInLink = By.cssSelector("a[data-test='nav-sign-in']");
    private final By logoLink = By.cssSelector("a.navbar-brand[title='Practice Software Testing - Toolshop'][href='/']");
    private final By profileLink = By.cssSelector("[data-test='nav-profile']");
    private final By cartIcon = By.cssSelector("svg[data-icon='cart-shopping']:not(button[data-test='add-to-cart'] svg)");

    public HeaderComponent(WebDriver driver) {
        super(driver);
    }

    public void clickSignIn() {
        elements.click(signInLink);
    }

    public void clickLogo() {
        elements.jsClick(logoLink); // JS click păstrat exact ca în teste
    }

    public void clickProfile() {
        elements.click(profileLink);
    }

    public void clickCart() {
        elements.click(cartIcon);
    }
}
