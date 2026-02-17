package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailsPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(ProductDetailsPage.class);
    private final By addToCartButton = By.cssSelector("button[data-test='add-to-cart']");
    public ProductDetailsPage(WebDriver driver) {
        super(driver);
    }
    public void addToCart() {
        logger.info("Product: add to cart");
        elements.click(addToCartButton);
    }
}
