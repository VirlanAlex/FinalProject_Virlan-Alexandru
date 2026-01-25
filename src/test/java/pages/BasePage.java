package pages;

import helpMethods.AlertMethods;
import helpMethods.ElementsMethod;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

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
    }
