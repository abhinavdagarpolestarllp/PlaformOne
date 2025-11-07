package pageObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class basePageAllure {

    protected WebDriver driver;
    protected WebDriverWait wait;

    // Constructor
    public basePageAllure(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // 🔹 Standard Click
    @Step("Click on {buttonName} button")
    public void standardClickButton(WebElement e, String buttonName) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(e));
            e.click();
            Allure.step("✅ Clicked on **" + buttonName + "** button successfully.");
        } catch (Exception ex) {
            Allure.step("❌ Failed to click on **" + buttonName + "** button. Exception: " + ex.getMessage());
            throw ex; // Ensures AllureReportManager captures screenshot + failure
        }
    }

    // 🔹 Standard Textbox Entry
    @Step("Enter {value} into {name} textbox")
    public void standardEnterTextbox(WebElement e, String value, String name) {
        try {
            wait.until(ExpectedConditions.visibilityOf(e));
            e.clear(); // ✅ Added clear step (good practice)
            e.sendKeys(value);
            Allure.step("✅ Entered **" + value + "** into **" + name + "** textbox successfully.");
        } catch (Exception ex) {
            Allure.step("❌ Failed to enter **" + value + "** into **" + name + "** textbox. Exception: " + ex.getMessage());
            throw ex;
        }
    }

    // 🔹 Wait for visibility
    @Step("Wait until element is visible")
    public void waitForVisibility(WebElement e) {
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    // 🔹 Get element text
    @Step("Get text from element")
    public String getElementText(WebElement e) {
        waitForVisibility(e);
        return e.getText();
    }
}
