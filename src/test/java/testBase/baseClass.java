package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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

import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import Utilities.ExtentReportManager;
import Utilities.excelUtility;
import io.github.bonigarcia.wdm.WebDriverManager;

public class baseClass {
    // ThreadLocal driver to support parallel execution safely
    private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();

    public Logger logger;
    public Properties p;

    public static Map<String, String> testRunMap = excelUtility.getTestExecutionMap(
        System.getProperty("user.dir") + File.separator + "testData" + File.separator + "Driver.xlsx"
    );

    public static Map<String, String> testRunMapSanity = excelUtility.getTestExecutionMap(
        System.getProperty("user.dir") + File.separator + "testData" + File.separator + "Driver_Sanity.xlsx"
    );

    /**
     * Thread-safe getter for WebDriver
     */
    public static WebDriver getDriver() {
        return threadDriver.get();
    }

    /**
     * Thread-safe setter for WebDriver
     */
    public static void setDriver(WebDriver driver) {
        threadDriver.set(driver);
    }

    /**
     * Setup method - initializes WebDriver according to config and places it into TestNG context
     * Uses RemoteWebDriver if SELENIUM_REMOTE_URL env var is present (for Selenium Grid).
     */
    @BeforeClass
    public void setup(ITestContext context) throws IOException {
        FileReader file = new FileReader("./src/test/resources/config.properties");
        p = new Properties();
        p.load(file);

        logger = LogManager.getLogger(this.getClass());
        String br = p.getProperty("browser") != null ? p.getProperty("browser").toLowerCase() : "chrome";

        // If running in k8s / grid, this env var should be set:
        String seleniumRemoteUrl = System.getenv("SELENIUM_REMOTE_URL"); // e.g. http://selenium-hub:4444/wd/hub

        WebDriver driver = null;

        switch (br) {
            case "edge":
                if (seleniumRemoteUrl != null && !seleniumRemoteUrl.isEmpty()) {
                    EdgeOptions edgeOptions = buildEdgeOptionsForRemote();
                    try {
                        driver = new RemoteWebDriver(new URL(seleniumRemoteUrl), edgeOptions);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to create RemoteWebDriver for Edge: " + e.getMessage(), e);
                    }
                } else {
                    WebDriverManager.edgedriver().setup();

                    // local options (non-remote)
                    Map<String, Object> prefsEdge = new HashMap<>();
                    prefsEdge.put("credentials_enable_service", false);
                    prefsEdge.put("profile.password_manager_enabled", false);
                    prefsEdge.put("download.prompt_for_download", true);
                    prefsEdge.put("download.directory_upgrade", true);
                    prefsEdge.put("safebrowsing.enabled", true);

                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.setExperimentalOption("prefs", prefsEdge);
                    edgeOptions.addArguments("--inprivate");
                    edgeOptions.addArguments("--force-device-scale-factor=0.9");
                    edgeOptions.addArguments("--disable-blink-features=AutomationControlled");
                    edgeOptions.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
                    edgeOptions.setExperimentalOption("useAutomationExtension", false);

                    driver = new EdgeDriver(edgeOptions);
                }
                break;

            case "chrome":
                if (seleniumRemoteUrl != null && !seleniumRemoteUrl.isEmpty()) {
                    ChromeOptions chromeOptions = buildChromeOptionsForRemote();
                    try {
                        driver = new RemoteWebDriver(new URL(seleniumRemoteUrl), chromeOptions);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to create RemoteWebDriver for Chrome: " + e.getMessage(), e);
                    }
                } else {
                    WebDriverManager.chromedriver().setup();

                    Map<String, Object> prefsChrome = new HashMap<>();
                    prefsChrome.put("credentials_enable_service", false);
                    prefsChrome.put("profile.password_manager_enabled", false);
                    prefsChrome.put("download.prompt_for_download", true);
                    prefsChrome.put("download.directory_upgrade", true);
                    prefsChrome.put("safebrowsing.enabled", true);

                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.setExperimentalOption("prefs", prefsChrome);
                    chromeOptions.addArguments("--incognito");
                    chromeOptions.addArguments("--force-device-scale-factor=0.9");
                    chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
                    chromeOptions.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
                    chromeOptions.setExperimentalOption("useAutomationExtension", false);

                    driver = new ChromeDriver(chromeOptions);
                }
                break;

            case "firefox":
                if (seleniumRemoteUrl != null && !seleniumRemoteUrl.isEmpty()) {
                    FirefoxOptions firefoxOptions = buildFirefoxOptionsForRemote();
                    try {
                        driver = new RemoteWebDriver(new URL(seleniumRemoteUrl), firefoxOptions);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to create RemoteWebDriver for Firefox: " + e.getMessage(), e);
                    }
                } else {
                    WebDriverManager.firefoxdriver().setup();

                    FirefoxProfile profile = new FirefoxProfile();
                    profile.setPreference("signon.rememberSignons", false);
                    profile.setPreference("signon.autofillForms", false);
                    profile.setPreference("signon.storeWhenAutocompleteOff", false);
                    profile.setPreference("network.cookie.cookieBehavior", 0);

                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.setProfile(profile);
                    firefoxOptions.addArguments("-private");

                    driver = new FirefoxDriver(firefoxOptions);
                }
                break;

            default:
                throw new IllegalArgumentException("❌ Unsupported browser: " + br);
        }

        // Set thread-local driver
        setDriver(driver);

        // basic navigation & waits
        if (getDriver() != null) {
            String url = p.getProperty("url");
            if (url != null && !url.trim().isEmpty()) {
                getDriver().get(url);
                getDriver().manage().window().maximize();
                getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            }
            // For grid/headless runs window-size is controlled via options; avoid maximize() for headless
            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        } else {
            throw new IllegalStateException("WebDriver initialization failed - driver is null");
        }

        // ✅ Set WebDriver into TestNG context for ExtentReportManager to access
        context.setAttribute("WebDriver", getDriver());
    }

    /**
     * Tear down driver at class end
     */
    @AfterClass
    public void tearDown() {
        try {
            WebDriver d = getDriver();
            if (d != null) {
                d.quit();
            }
        } finally {
            // remove from ThreadLocal to avoid memory leaks
            threadDriver.remove();
        }
    }

    // ================= Helper methods to build options for remote/grid runs =================

    private ChromeOptions buildChromeOptionsForRemote() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefsChrome = new HashMap<>();
       /* prefsChrome.put("credentials_enable_service", false);
        prefsChrome.put("profile.password_manager_enabled", false);
        prefsChrome.put("download.prompt_for_download", true);
        prefsChrome.put("download.directory_upgrade", true);
        prefsChrome.put("safebrowsing.enabled", true);
        options.setExperimentalOption("prefs", prefsChrome);

        // container-friendly args
        //options.addArguments("--headless=new");           // modern headless; if older chrome use "--headless"
        //options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
        options.setExperimentalOption("useAutomationExtension", false);*/
        return options;
    }

    private EdgeOptions buildEdgeOptionsForRemote() {
        EdgeOptions options = new EdgeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("download.prompt_for_download", true);
        prefs.put("download.directory_upgrade", true);
        prefs.put("safebrowsing.enabled", true);
        options.setExperimentalOption("prefs", prefs);

        //options.addArguments("--headless=new");
        //options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
        options.setExperimentalOption("useAutomationExtension", false);
        return options;
    }

    private FirefoxOptions buildFirefoxOptionsForRemote() {
        FirefoxOptions options = new FirefoxOptions();
        //options.addArguments("-headless"); // Firefox uses -headless
        //options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");

        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("signon.rememberSignons", false);
        profile.setPreference("signon.autofillForms", false);
        profile.setPreference("signon.storeWhenAutocompleteOff", false);
        options.setProfile(profile);
        return options;
    }

    // ================= existing screenshot & utility methods =================

    // ✅ Safe screenshot method with null-check
    public static String captureScreen(WebDriver driver, String tname) {
        if (driver == null) {
            System.err.println("⚠️ Cannot take screenshot: WebDriver is null.");
            return null;
        }

        try {
            // timestamped filename
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String screenshotFileName = tname + "_" + timeStamp + ".png";

            // Prefer ExtentReportManager's screenshotFolderPath (listener creates correct run folder)
            String screenshotDir = ExtentReportManager.screenshotFolderPath;

            // If not set, construct a fallback Run Results path with timestamp (use the manager timestamp if available)
            if (screenshotDir == null || screenshotDir.trim().isEmpty()) {
                String useTs = (ExtentReportManager.timeStamp != null && !ExtentReportManager.timeStamp.isEmpty())
                        ? ExtentReportManager.timeStamp
                        : new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

                String fallbackRunFolder = System.getProperty("user.dir") + File.separator + "Run Results"
                        + File.separator + "Run-" + useTs;
                screenshotDir = fallbackRunFolder + File.separator + "Screenshot"; // singular per your request
            }

            Path screenshotsFolder = Paths.get(screenshotDir);
            Files.createDirectories(screenshotsFolder);

            // capture and copy
            TakesScreenshot ts = (TakesScreenshot) driver;
            File sourceFile = ts.getScreenshotAs(OutputType.FILE);
            Path targetPath = screenshotsFolder.resolve(screenshotFileName);
            Files.copy(sourceFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            // Because the report HTML is inside Report/, the relative path from report -> screenshot is ../Screenshot/<file>
            String relativePath = "../Screenshot/" + screenshotFileName;

            return relativePath.replace("\\", "/");

        } catch (IOException e) {
            System.err.println("❌ Failed to capture screenshot: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.err.println("❌ Unexpected error while taking screenshot: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // ✅ Skip logic from Excel map
    public void checkRunFlag(String testName) {
        if (!"Yes".equalsIgnoreCase(testRunMap.getOrDefault(testName, "No"))) {
            throw new SkipException("Skipping test: " + testName);
        }
    }

    public void checkRunFlagSanity(String testName) {
        if (!"Yes".equalsIgnoreCase(testRunMapSanity.getOrDefault(testName, "No"))) {
            throw new SkipException("Skipping test: " + testName);
        }
    }
}
