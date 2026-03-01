package helpMethods;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class ElementsMethod {

    private static final Logger logger = LogManager.getLogger(ElementsMethod.class);
    private static final Duration DEFAULT_WAIT   = Duration.ofSeconds(10);
    private static final Duration PAGE_READY_WAIT = Duration.ofSeconds(3);

    private final WebDriver driver;
    private final WebDriverWait wait;

    public ElementsMethod(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, DEFAULT_WAIT);
    }

    // ── Wait conditions ───────────────────────────────────────────────────────

    public WebElement presence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement clickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Waits for the page document to reach readyState "complete".
     * Replaces raw Thread.sleep() calls in page transitions.
     */
    public void waitForPageReady() {
        new WebDriverWait(driver, PAGE_READY_WAIT).until(d ->
                ((JavascriptExecutor) d)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );
    }

    // ── Actions ───────────────────────────────────────────────────────────────

    public void click(By locator) {
        logger.debug("Click: {}", locator);
        clickable(locator).click();
    }

    public void jsClick(By locator) {
        logger.debug("JS Click: {}", locator);
        WebElement el = clickable(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    public void type(By locator, String text) {
        logger.debug("Type into {} | value='{}'", locator, safeValue(locator, text));
        WebElement el = visible(locator);
        el.clear();
        el.sendKeys(text);
    }

    public void typeNoClear(By locator, String text) {
        logger.debug("Type (no clear) into {} | value='{}'", locator, safeValue(locator, text));
        visible(locator).sendKeys(text);
    }

    public void clear(By locator) {
        logger.debug("Clear: {}", locator);
        visible(locator).clear();
    }

    public void pressTab(By locator) {
        logger.debug("Press TAB on: {}", locator);
        visible(locator).sendKeys(Keys.TAB);
    }

    // ── Select ────────────────────────────────────────────────────────────────

    public void selectByValue(By locator, String value) {
        logger.debug("Select by value on {} | value='{}'", locator, value);
        Select select = new Select(clickable(locator));
        select.selectByValue(value);

        String actual = select.getFirstSelectedOption().getAttribute("value");
        if (!value.equals(actual)) {
            throw new AssertionError(
                    "Dropdown selection failed. Expected: " + value + " but was: " + actual);
        }
    }

    // ── URL waits ─────────────────────────────────────────────────────────────

    public void waitUrlContains(String partial) {
        logger.debug("Wait URL contains: '{}'", partial);
        wait.until(ExpectedConditions.urlContains(partial));
    }

    // ── Text / value helpers ─────────────────────────────────────────────────

    public String waitNonEmptyText(By locator) {
        return wait.until(d -> {
            WebElement el = d.findElement(locator);
            String t = el.getText();
            if (t == null) return null;
            t = t.trim();
            return t.isEmpty() ? null : t;
        });
    }

    public String getValue(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
                   .getAttribute("value");
    }

    public String firstText(By locator) {
        return isPresent(locator)
                ? driver.findElements(locator).get(0).getText().trim()
                : "";
    }

    // ── Presence check ────────────────────────────────────────────────────────

    public boolean isPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    // ── Generic condition wait ────────────────────────────────────────────────

    public void waitUntil(java.util.function.Function<WebDriver, Boolean> condition) {
        wait.until(condition);
    }

    // ── Value stability waits ─────────────────────────────────────────────────

    public void waitUntilValueEquals(By locator, String expected) {
        logger.debug("Wait value equals on {} | expected='{}'", locator, expected);
        wait.until(d -> expected.equals(d.findElement(locator).getAttribute("value")));
    }

    public void waitUntilValueStabilizes(By locator) {
        waitUntilValueStabilizes(locator, Duration.ofMillis(700), DEFAULT_WAIT);
    }

    public void waitUntilValueStabilizes(By locator, Duration stableFor, Duration timeout) {
        WebDriverWait customWait = new WebDriverWait(driver, timeout);
        customWait.pollingEvery(Duration.ofMillis(100));

        AtomicReference<String> lastValue  = new AtomicReference<>(null);
        AtomicLong              lastChange = new AtomicLong(System.currentTimeMillis());

        customWait.until(d -> {
            String current  = d.findElement(locator).getAttribute("value");
            String previous = lastValue.getAndSet(current);
            long   now      = System.currentTimeMillis();

            if (previous == null || !previous.equals(current)) {
                lastChange.set(now);
                return false;
            }
            return (now - lastChange.get()) >= stableFor.toMillis();
        });
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    /**
     * Masks values for sensitive fields so they never appear in logs.
     */
    private String safeValue(By locator, String value) {
        if (value == null) return "null";
        String loc = String.valueOf(locator).toLowerCase();
        if (loc.contains("password") || loc.contains("passwd")
                || loc.contains("secret") || loc.contains("token")) {
            return "***";
        }
        return value.length() > 120 ? value.substring(0, 120) + "..." : value;
    }
}
