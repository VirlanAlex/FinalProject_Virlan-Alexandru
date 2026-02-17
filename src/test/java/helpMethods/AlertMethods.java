package helpMethods;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

    public class AlertMethods {

        private final WebDriver driver;
        private final WebDriverWait wait;
        private static final Duration ALERT_WAIT = Duration.ofSeconds(2);

        public AlertMethods(WebDriver driver) {
            this.driver = driver;
            this.wait = new WebDriverWait(driver, ALERT_WAIT);
        }

        public void showAlertWithText(String message) {
            ((JavascriptExecutor) driver).executeScript("alert(arguments[0]);", message);
        }

        public String getAlertText() {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            return alert.getText();
        }

        public void acceptAlert() {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        }

        public boolean isAlertPresent() {
            try {
                wait.until(ExpectedConditions.alertIsPresent());
                return true;
            } catch (TimeoutException e) {
                return false;
            }
        }

        public void showAndValidateAndAccept(String message) {
            showAlertWithText(message);
            String actual = getAlertText();
            if (!message.equals(actual)) {
                throw new AssertionError("Alert text mismatch. Expected: " + message + " but was: " + actual);
            }
            acceptAlert();
        }
    }
