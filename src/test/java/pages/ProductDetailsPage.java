package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailsPage extends BasePage {
    private final By addToCartButton = By.cssSelector("button[data-test='add-to-cart']");
    public ProductDetailsPage(WebDriver driver) {
        super(driver);
    }
    public void addToCart() {
        elements.click(addToCartButton);
    }
}
