package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private final By combinationPliersProduct = By.cssSelector("img.card-img-top[alt='Combination Pliers']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void openCombinationPliers() {
        logStep("Open product: Combination Pliers");
        elements.click(combinationPliersProduct);
    }
}
