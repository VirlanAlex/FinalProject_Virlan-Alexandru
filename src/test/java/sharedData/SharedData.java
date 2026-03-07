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

        // Incarcam doar "/" - trece Cloudflare
        // Testele folosesc clickSignIn() pentru a naviga la login (navigare interna Angular)
        driver.get(url("/"));
        LogUtility.infoLog("URL: " + driver.getCurrentUrl() + " | Title: " + driver.getTitle());

        // Asteapta sa treaca Cloudflare si Angular sa randeze navbar-ul
        try {
            new WebDriverWait(driver, Duration.ofSeconds(60))
                    .until(ExpectedConditions.elementToBeClickable(
                            By.cssSelector("a[data-test='nav-sign-in']")
                    ));
            LogUtility.infoLog("Page ready.");
        } catch (Exception e) {
            takeDebugSnapshot("SETUP_FAILED_" + testName);
            throw new RuntimeException(
                    "Page not ready. URL=" + driver.getCurrentUrl() +
                            " Title=" + driver.getTitle(), e);
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
