package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
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

public class baseClass {
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
                throw new IllegalArgumentException("❌ Unsupported browser: " + br);
        }

        driver.get(p.getProperty("url"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // ✅ Set WebDriver into TestNG context for ExtentReportManager to access
        context.setAttribute("WebDriver", driver);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ✅ Safe screenshot method with null-check
    public static String captureScreen(WebDriver driver, String tname) throws IOException {
        if (driver == null) {
            System.out.println("⚠️ Cannot take screenshot: WebDriver is null.");
            return null;
        }

        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String screenshotDir = System.getProperty("user.dir") + "\\screenshots\\";
        new File(screenshotDir).mkdirs(); // Ensure directory exists

        String targetFilePath = screenshotDir + tname + "_" + timeStamp + ".png";
        File targetFile = new File(targetFilePath);

        sourceFile.renameTo(targetFile); // Or use Files.copy() for better OS support

        return targetFilePath;
    }

    // ✅ Skip logic from Excel map
    public void checkRunFlag(String testName) {
        if (!"Yes".equalsIgnoreCase(testRunMap.getOrDefault(testName, "No"))) {
            throw new SkipException("Skipping test: " + testName);
        }
    }
}
