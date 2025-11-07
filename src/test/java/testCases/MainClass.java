package testCases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainClass {
	WebDriver driver;
	WebDriverWait wait;

	public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Selenium WebDriver\\ChromeDriver\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        //options.setExperimentalOption("debuggerAddress", "localhost:9222");
        options.addArguments("user-data-dir=C:\\ChromeData"); // custom profile folder
        options.addArguments("profile-directory=Default");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        WebDriver driver = new ChromeDriver(options);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        String URL="https://admanager.google.com/22050442738#reports/interactive/list";
        driver.get(URL);
        System.out.print("Launched");;

    }

}
