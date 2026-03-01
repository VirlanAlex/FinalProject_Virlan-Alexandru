package pages;

import helpMethods.AlertMethods;
import helpMethods.ElementsMethod;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utils.LogUtility;

public class BasePage {

    protected WebDriver driver;
    protected ElementsMethod elements;
    protected AlertMethods alerts;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.elements = new ElementsMethod(driver);
        this.alerts = new AlertMethods(driver);
        PageFactory.initElements(driver, this);
    }

    protected void logStep(String message) {
        LogUtility.infoLog(getClass().getSimpleName() + " -> " + message);
    }

    protected void logWarn(String message) {
        LogUtility.warnLog(getClass().getSimpleName() + " -> " + message);
    }

    protected void logError(String message) {
        LogUtility.errorLog(getClass().getSimpleName() + " -> " + message);
    }
}
