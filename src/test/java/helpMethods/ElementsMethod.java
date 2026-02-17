package helpMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class ElementsMethod {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final Duration DEFAULT_WAIT = Duration.ofSeconds(10);

    public ElementsMethod(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, DEFAULT_WAIT);
    }

    public WebElement presence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement clickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void click(By locator) {
        clickable(locator).click();
    }

    public void jsClick(By locator) {
        WebElement el = clickable(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    public void type(By locator, String text) {
        WebElement el = visible(locator);
        el.clear();
        el.sendKeys(text);
    }

    public void typeNoClear(By locator, String text) {
        visible(locator).sendKeys(text);
    }

    public void clear(By locator) {
        visible(locator).clear();
    }

    public void waitUrlContains(String partial) {
        wait.until(ExpectedConditions.urlContains(partial));
    }

    public void selectByValue(By locator, String value) {
        WebElement dropdown = clickable(locator);
        Select select = new Select(dropdown);
        select.selectByValue(value);

        String actual = select.getFirstSelectedOption().getAttribute("value");
        if (!value.equals(actual)) {
            throw new AssertionError("Dropdown selection failed. Expected: " + value + " but was: " + actual);
        }
    }

    public String waitNonEmptyText(By locator) {
        return wait.until(d -> {
            WebElement el = d.findElement(locator);
            String t = el.getText();
            if (t == null) return null;
            t = t.trim();
            return t.isEmpty() ? null : t;
        });
    }
    public void waitUntil(java.util.function.Function<WebDriver, Boolean> condition) {
        wait.until(condition);
    }
    public boolean isPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    public String firstText(By locator) {
        return isPresent(locator) ? driver.findElements(locator).get(0).getText().trim() : "";
    }
    public String getValue(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element.getAttribute("value");
    }



    public void pressTab(By locator) {
        visible(locator).sendKeys(Keys.TAB);
    }

    public void waitUntilValueEquals(By locator, String expected) {
        wait.until(d -> {
            WebElement el = d.findElement(locator);
            String v = el.getAttribute("value");
            return expected.equals(v);
        });
    }

    /**
     * Waits until the input value stops changing for a short period (useful for async auto-fill after login).
     */
    public void waitUntilValueStabilizes(By locator) {
        waitUntilValueStabilizes(locator, Duration.ofMillis(700), DEFAULT_WAIT);
    }

    public void waitUntilValueStabilizes(By locator, Duration stableFor, Duration timeout) {
        WebDriverWait customWait = new WebDriverWait(driver, timeout);
        customWait.pollingEvery(Duration.ofMillis(100));

        AtomicReference<String> lastValue = new AtomicReference<>(null);
        AtomicLong lastChange = new AtomicLong(System.currentTimeMillis());

        customWait.until(d -> {
            WebElement el = d.findElement(locator);
            String current = el.getAttribute("value");
            String previous = lastValue.getAndSet(current);

            long now = System.currentTimeMillis();
            if (previous == null || !previous.equals(current)) {
                lastChange.set(now);
                return false;
            }
            return (now - lastChange.get()) >= stableFor.toMillis();
        });
    }

}
