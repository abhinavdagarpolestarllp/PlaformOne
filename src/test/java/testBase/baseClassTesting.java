package testBase;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;

public class baseClassTesting {
    protected WebDriver driver;
    protected String excelPath;

    @Parameters({"excelFile", "debugPort"})
    @BeforeClass
    public void baseTesting(@Optional(".\\TestData\\ChatBot.xlsx") String excelFile,
                            @Optional("9222") String debugPort) {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("debuggerAddress", "localhost:" + debugPort);

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        this.excelPath = excelFile;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getExcelPath() {
        return excelPath;
    }

    @AfterClass
    public void refresh() {
        if (driver != null) {
            driver.navigate().refresh();
        }
    }
}