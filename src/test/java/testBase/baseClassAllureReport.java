package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import Utilities.excelUtility;

public class baseClassAllureReport {

    public WebDriver driver;
    public Logger logger;
    public Properties p;

    public static Map<String, String> testRunMap = excelUtility.getTestExecutionMap(".\\testData\\Driver.xlsx");

    @BeforeClass
    public void setup(ITestContext context) throws IOException {
        FileReader file = new FileReader("./src/test/resources/config.properties");
        p = new Properties();
        p.load(file);

        logger = LogManager.getLogger(this.getClass());
        String br = p.getProperty("browser").toLowerCase();

        switch (br) {
            case "edge":
                System.setProperty("webdriver.edge.driver", "C:\\Selenium WebDriver\\EdgeDriver\\msedgedriver.exe");
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setExperimentalOption("prefs", Map.of(
                        "credentials_enable_service", false,
                        "profile.password_manager_enabled", false
                ));
                edgeOptions.addArguments("-inprivate");
                driver = new EdgeDriver(edgeOptions);
                break;

            case "chrome":
                System.setProperty("webdriver.chrome.driver", "C:\\Selenium WebDriver\\ChromeDriver\\chromedriver.exe");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setExperimentalOption("prefs", Map.of(
                        "credentials_enable_service", false,
                        "profile.password_manager_enabled", false
                ));
                chromeOptions.addArguments("--incognito");
                chromeOptions.addArguments("user-data-dir=C:/temp/cleanChromeProfile");
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                System.setProperty("webdriver.gecko.driver", "C:\\Selenium WebDriver\\GeckoDriver\\geckodriver.exe");
                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("signon.rememberSignons", false);
                profile.setPreference("signon.autofillForms", false);
                profile.setPreference("signon.storeWhenAutocompleteOff", false);
                profile.setPreference("network.cookie.cookieBehavior", 0);

                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setProfile(profile);
                firefoxOptions.addArguments("-private");
                driver = new FirefoxDriver(firefoxOptions);
                break;

            default:
                throw new RuntimeException("Unsupported browser: " + br);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(p.getProperty("url"));

        // ✅ Make driver available to AllureReportManager
        context.setAttribute("WebDriver", driver);

        logStep("Launched browser: " + br + " | URL: " + p.getProperty("url"));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logStep("Browser closed");
        }
    }

    @Attachment(value = "Failure Screenshot", type = "image/png")
    public static byte[] captureScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public static String captureScreen(WebDriver driver, String tname) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String filePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + timeStamp + ".png";

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(filePath);
        src.renameTo(dest);
        return filePath;
    }

    public void checkRunFlag(String testName) {
        if (!"Yes".equalsIgnoreCase(testRunMap.getOrDefault(testName, "No"))) {
            logStep("Test skipped based on Excel driver: " + testName);
            throw new SkipException("Skipping test: " + testName);
        }
    }

    @Step("{0}")
    public void logStep(String message) {
        System.out.println("[STEP] " + message);
    }
}
