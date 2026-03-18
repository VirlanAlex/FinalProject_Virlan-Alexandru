package backEnd.sharedData;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import backEnd.utils.AllureAppender;
import backEnd.utils.LogUtility;

public class SharedData {
    private WebDriver driver;
    private String testName;

    @BeforeMethod
    public void prepareEnvironment(){
        testName = this.getClass().getSimpleName();
        driver = new ChromeDriver();
        driver.get("https://practicesoftwaretesting.com/auth/login");
        driver.manage().window().maximize();

        LogUtility.startTest(testName);
    }

    @AfterMethod
    public void clearEnvironment(ITestResult result){
        if (result.getStatus()==ITestResult.FAILURE) {
            AllureAppender.attachScreenshot(driver);
        }
            driver.quit();

        LogUtility.finishTest(testName);
    }

    public WebDriver getDriver() {
        return driver;
    }
}
