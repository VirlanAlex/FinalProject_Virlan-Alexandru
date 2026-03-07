package sharedData;

import modelObject.TestDataModel;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.LogUtility;
import utils.TestDataLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

public class SharedData {

    protected WebDriver driver;
    private String testName;

    // Selector care confirma ca pagina s-a incarcat (prezent pe TOATE paginile site-ului)
    private static final By NAVBAR = By.cssSelector("nav, app-header, .navbar");

    @BeforeMethod(alwaysRun = true)
    public void prepareEnvironment() {
        testName = this.getClass().getSimpleName();
        LogUtility.startTest(testName);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);

        navigateTo("/");
    }

    /**
     * Navigheaza la o pagina si asteapta sa treaca Cloudflare + Angular sa se incarce.
     * Foloseste aceasta metoda in TOATE testele in loc de driver.get().
     */
    protected void navigateTo(String path) {
        String fullUrl = url(path);
        LogUtility.infoLog("Navigating to: " + fullUrl);
        driver.get(fullUrl);

        // Asteapta ca titlul sa nu mai fie "Just a moment..." (Cloudflare challenge)
        try {
            new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(d -> !d.getTitle().toLowerCase().contains("just a moment"));
            LogUtility.infoLog("Cloudflare passed. Title: " + driver.getTitle());
        } catch (Exception e) {
            LogUtility.infoLog("WARNING: Possible Cloudflare block. Title: " + driver.getTitle());
        }

        // Asteapta navbar-ul sa fie vizibil (Angular a randat pagina)
        try {
            new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(ExpectedConditions.visibilityOfElementLocated(NAVBAR));
            LogUtility.infoLog("Page ready: " + driver.getCurrentUrl());
        } catch (Exception e) {
            takeDebugSnapshot("NAV_FAILED_" + path.replace("/", "_"));
            throw new RuntimeException(
                    "Page not ready after navigation to " + fullUrl +
                            " | URL=" + driver.getCurrentUrl() +
                            " | Title=" + driver.getTitle(), e);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void clearEnvironment(ITestResult result) {
        try {
            if (driver != null && result.getStatus() == ITestResult.FAILURE) {
                takeDebugSnapshot("TEST_FAILED_" + testName);
            }
        } finally {
            try {
                if (driver != null) driver.quit();
            } finally {
                LogUtility.finishTest(testName);
            }
        }
    }

    private void takeDebugSnapshot(String label) {
        try {
            Files.createDirectories(Paths.get("target/screenshots"));
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), Paths.get("target/screenshots/" + label + ".png"));
            Files.writeString(Paths.get("target/screenshots/" + label + ".html"), driver.getPageSource());
        } catch (IOException ex) {
            LogUtility.infoLog("Could not save snapshot: " + ex.getMessage());
        }
    }

    protected String url(String path) {
        String base = getData().getBaseUrl();
        if (base == null || base.trim().isEmpty()) {
            throw new IllegalStateException("BaseUrl is missing from test data.");
        }
        base = base.trim();
        if (base.endsWith("/")) base = base.substring(0, base.length() - 1);
        if (path == null || path.isEmpty()) path = "/";
        if (!path.startsWith("/")) path = "/" + path;
        return base + path;
    }

    public WebDriver getDriver() { return driver; }
    public TestDataModel getData() { return TestDataLoader.getTestData(); }
}
