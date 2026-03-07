package sharedData;

import modelObject.TestDataModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.LogUtility;
import utils.TestDataLoader;

import java.time.Duration;

public class SharedData {

    protected WebDriver driver;
    private String testName;

    @BeforeMethod(alwaysRun = true)
    public void prepareEnvironment() {
        testName = this.getClass().getSimpleName();
        LogUtility.startTest(testName);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-size=1920,1080");

        // Headless automat pe GitHub Actions (CI=true) sau cu -Dheadless=true
        boolean isCI = "true".equalsIgnoreCase(System.getenv("CI"));
        boolean isHeadlessProp = Boolean.parseBoolean(System.getProperty("headless", "false"));
        if (isCI || isHeadlessProp) {
            options.addArguments("--headless=new");
        }

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.get(url("/"));
        waitForAngularToLoad();
    }


    private void waitForAngularToLoad() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(ExpectedConditions.elementToBeClickable(
                            By.cssSelector("a[data-test='nav-sign-in']")
                    ));
            LogUtility.infoLog("Angular app loaded successfully.");
        } catch (Exception e) {
            LogUtility.infoLog("WARNING: Angular app may not have loaded fully: " + e.getMessage());
        }
    }

    @AfterMethod(alwaysRun = true)
    public void clearEnvironment() {
        try {
            if (driver != null) {
                driver.quit();
            }
        } finally {
            LogUtility.finishTest(testName);
        }
    }

    protected String url(String path) {
        String base = getData().getBaseUrl();
        if (base == null || base.trim().isEmpty()) {
            throw new IllegalStateException("BaseUrl is missing from test data (testdata.json).");
        }
        base = base.trim();
        if (base.endsWith("/")) base = base.substring(0, base.length() - 1);
        if (path == null || path.isEmpty()) path = "/";
        if (!path.startsWith("/")) path = "/" + path;
        return base + path;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public TestDataModel getData() {
        return TestDataLoader.getTestData();
    }
}
