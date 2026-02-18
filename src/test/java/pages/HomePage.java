package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(HomePage.class);
    private final By combinationPliersProduct = By.cssSelector("img.card-img-top[alt='Combination Pliers']");
    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void openCombinationPliers() {
        logger.info("Home: open product 'Combination Pliers'");
        elements.click(combinationPliersProduct); // are wait inclus
    }
}