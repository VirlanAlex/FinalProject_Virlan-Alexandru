package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HeaderComponent extends BasePage {
    private static final Logger logger = LogManager.getLogger(HeaderComponent.class);
    private final By signInLink = By.cssSelector("a[data-test='nav-sign-in']");
    private final By logoLink = By.cssSelector("a.navbar-brand[title='Practice Software Testing - Toolshop'][href='/']");
    private final By profileLink = By.cssSelector("[data-test='nav-profile']");
    private final By cartIcon = By.cssSelector("svg[data-icon='cart-shopping']:not(button[data-test='add-to-cart'] svg)");

    public HeaderComponent(WebDriver driver) {
        super(driver);
    }

    public void clickSignIn() {
        logger.info("Header: click Sign In");
        elements.click(signInLink);
    }

    public void clickLogo() {
        logger.info("Header: click Logo");
        elements.jsClick(logoLink); // JS click păstrat exact ca în teste
    }

    public void clickProfile() {
        logger.info("Header: click Profile");
        elements.click(profileLink);
    }

    public void clickCart() {
        logger.info("Header: open Cart");
        elements.click(cartIcon);
    }
}
