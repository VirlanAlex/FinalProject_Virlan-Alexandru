package sharedData;

import modelObject.TestDataModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.LogUtility;
import utils.TestDataLoader;

import java.time.Duration;

public class SharedData {

    protected WebDriver driver;
    private TestDataModel data;
    private String testName;

    @BeforeMethod(alwaysRun = true)
    public void prepareEnvironment() {
        testName = this.getClass().getSimpleName();
        LogUtility.startTest(testName);

        data = TestDataLoader.load("testdata.json", TestDataModel.class);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        boolean isCi = "true".equalsIgnoreCase(System.getenv("CI"))
                || "true".equalsIgnoreCase(System.getenv("GITHUB_ACTIONS"));

        boolean headless = Boolean.parseBoolean(System.getProperty("headless", String.valueOf(isCi)));

        if (headless) {
            options.addArguments("--headless=new");
        } else {
            options.addArguments("--start-maximized");
        }

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.get(url("/"));
    }

    @AfterMethod(alwaysRun = true)
    public void clearEnvironment() {
        try {
            if (driver != null) driver.quit();
        } finally {
            LogUtility.finishTest(testName);
        }
    }

    protected String url(String path) {
        String base = data.getBaseUrl();
        if (base.endsWith("/")) base = base.substring(0, base.length() - 1);
        if (!path.startsWith("/")) path = "/" + path;
        return base + path;
    }

    public WebDriver getDriver() {
        return driver;
    }
    public TestDataModel getData() {
        return data;
    }
}