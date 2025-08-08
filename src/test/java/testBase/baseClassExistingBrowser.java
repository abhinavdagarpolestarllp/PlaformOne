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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

//import com.beust.jcommander.Parameters;

public class baseClassExistingBrowser {
public static WebDriver driver;
public Logger logger;
public Properties p;
	@SuppressWarnings("deprecation")
	@BeforeClass
	public void setup() throws IOException {
		//Runtime.getRuntime().exec("taskkill /F /IM msedge.exe");
		FileReader file = new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(file);
		logger = LogManager.getLogger(this.getClass());
		String br =p.getProperty("browser").toLowerCase();
		switch(br) {
		case "edge" : 
			System.setProperty("webdriver.edge.driver", "C:\\Selenium WebDriver\\EdgeDriver\\msedgedriver.exe");


	        EdgeOptions options1 = new EdgeOptions();

	        // Set path to existing user data directory
	        options1.addArguments("user-data-dir=C:\\Users\\abhinav.dagar\\AppData\\Local\\Microsoft\\Edge\\User Data");

	        // Optionally specify profile (usually "Default")
	        options1.addArguments("profile-directory=Default");

	        driver = new EdgeDriver(options1);
	        break;
		case "chrome" :
			System.setProperty("webdriver.chrome.driver","C:\\Selenium WebDriver\\ChromeDriver\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", Map.of(
			    "credentials_enable_service", false,
			    "profile.password_manager_enabled", false
			));
			options.addArguments("--incognito");
			options.addArguments("user-data-dir=C:/temp/cleanChromeProfile");
			driver = new ChromeDriver(options);
			break;
			
		case "firefox" :
			System.setProperty("webdriver.gecko.driver", "C:\\Selenium WebDriver\\GeckoDriver\\geckodriver.exe");
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("signon.rememberSignons", false);
			profile.setPreference("signon.autofillForms", false);
			profile.setPreference("signon.storeWhenAutocompleteOff", false);
			profile.setPreference("network.cookie.cookieBehavior", 0); // Accept all cookies

			// Use this profile in FirefoxOptions
			FirefoxOptions options2 = new FirefoxOptions();
			options2.setProfile(profile);

			// Start Firefox in private/incognito mode
			options2.addArguments("-private");
			driver = new FirefoxDriver(options2);
	    }
			
		driver.get(p.getProperty("url"));	
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}/*@AfterClass
	public void tearDown() {
		driver.quit();
	}public String captureScreen(String tname) throws IOException {

	    String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

	    TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
	    File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

	    String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + timeStamp + ".png";
	    File targetFile = new File(targetFilePath);

	    sourceFile.renameTo(targetFile);

	    return targetFilePath;*/
	
}
