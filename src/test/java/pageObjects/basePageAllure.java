package pageObjects;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import testBase.baseClass;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public class basePageAllure {
    protected WebDriver driver;
    public String dateNTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    public String todayDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());

    private WebDriverWait smallerWait;
    public WebDriverWait wait;
    public WebDriverWait extendedWait;

    public basePageAllure(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        smallerWait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        extendedWait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    @Step("Clearing textbox")
    public void clearTextbox(WebElement e) {
        e.sendKeys(Keys.CONTROL + "a");
        e.sendKeys(Keys.DELETE);
    }

    @Step("Getting text from element")
    public String getText(WebElement e) {
        return e.getText();
    }

    @Step("Clicking button: {1}")
    public void standardClickButton(WebElement e, String name) {
        try {
            e.click();
            logStep(name + " button clicked");
        } catch (Exception ex) {
            logStep(name + " button click failed: " + ex.getMessage());
            captureScreenshot("Click failure: " + name);
            Assert.fail(name + " button click failed");
        }
    }

    @Step("Entering '{0}' into textbox '{1}'")
    public void standardEnterTextbox(WebElement e, String value, String name) {
        try {
            e.sendKeys(value);
            logStep(value + " entered into textbox " + name);
        } catch (Exception ex) {
            logStep("Failed to enter '" + value + "' into textbox " + name + ": " + ex.getMessage());
            captureScreenshot("Enter textbox failure");
            Assert.fail("Textbox entry failed");
        }
    }

    @Step("Click sorting button: {0}")
    public void clickSortingButton(String s) {
        try {
            String obj = "//span[text()='" + s + "']//following-sibling::span[contains(@class,'p-sortable-column')]";
            driver.findElement(By.xpath(obj)).click();
            logStep(s + " sorting button clicked");
        } catch (Exception ex) {
            logStep(s + " sorting button click failed: " + ex.getMessage());
            captureScreenshot("Sorting click failure");
        }
    }

    @Step("Validate sorting '{1}' for column '{0}'")
    public void sortingValidation(String s, String sort) {
        try {
            String obj = "//th[@aria-sort='" + sort.toLowerCase() + "']";
            smallerWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(obj)));
            captureScreenshot("Sorting validated for " + s);
        } catch (Exception ex) {
            logStep("Sorting validation failed for " + s + ": " + ex.getMessage());
            captureScreenshot("Sorting validation failure");
        }
    }

    @Step("Verify header '{1}' of tag '{0}' is present")
    public void isHeaderPresent(String s, String q) throws InterruptedException {
        Thread.sleep(2000);
        String obj = "//" + s + "[text()='" + q + "']";
        Assert.assertTrue(driver.findElement(By.xpath(obj)).isDisplayed(), "Header not displayed: " + q);
        captureScreenshot(q + " header present");
    }

    @Step("Verify object presence: {0}")
    public void isObjectExist(WebElement e) {
        try {
            Thread.sleep(1000);
            wait.until(ExpectedConditions.visibilityOf(e));
            if (e.isDisplayed()) {
                captureScreenshot("Element present: " + e.getText());
            }
        } catch (Exception ex) {
            logStep("Element not present: " + ex.getMessage());
            captureScreenshot("Element existence failure");
        }
    }

    @Step("Check if object is not present: {0}")
    public int isObjectNotExist(WebElement e) {
        try {
            Thread.sleep(2000);
            return e.isDisplayed() ? 0 : 1;
        } catch (Exception ex) {
            return 0;
        }
    }

    @Step("Click side navigation module: {0}")
    public void clickSideNavigationModule(String module) throws InterruptedException {
        Thread.sleep(2000);
        List<WebElement> list = driver.findElements(By.xpath("//span[contains(@class,'SideNav_sideNotModulename')]"));
        for (WebElement e : list) {
            if (e.getText().equals(module)) {
                e.click();
                logStep(module + " side nav clicked");
            }
        }
    }

    @Step("Click checkbox for: {0}")
    public void clickCheckBox(String s) {
        String obj = "(//*[text()='" + s + "']//preceding::input[@type='checkbox'])[last()]";
        driver.findElement(By.xpath(obj)).click();
        logStep(s + " checkbox clicked");
    }

    @Step("Mouse over element")
    public void mouseOverElement(WebElement e) {
        new Actions(driver).moveToElement(e).build().perform();
        logStep("Mouse over on: " + e.getText());
    }

    @Step("Click span with text '{0}'")
    public void clickSpanText(String name) {
        driver.findElement(By.xpath("//span[text()='" + name + "']")).click();
        logStep(name + " clicked");
    }

    @Step("Dropdown element '{1}' selection using WebElement")
    public void dropdownElement(WebElement e, String c) {
        try {
            e.click();
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[contains(@class,'option') and text()='" + c + "']"))));
            driver.findElement(By.xpath("//div[contains(@class,'option') and text()='" + c + "']")).click();
            logStep(c + " selected from dropdown element");
        } catch (Exception ex) {
            logStep(c + " dropdown selection failed: " + ex.getMessage());
            captureScreenshot("Dropdown element failure");
        }
    }

    @Step("Verify text '{1}' present at element")
    public void isTextPresentAt(WebElement textbox, String value) {
        try {
            Assert.assertEquals(textbox.getText(), value);
            logStep(value + " is present in textbox");
        } catch (Exception ex) {
            logStep("Textbox text mismatch: " + ex.getMessage());
            captureScreenshot("Textbox text failure");
            Assert.fail("Textbox verification failed");
        }
    }

    @Step("Get page initiator count after creation")
    public int isPageInititiorAddedAfterCreation() {
        WebElement e = driver.findElement(By.xpath("(//span[@class='paginatorClass'])[1]"));
        String[] arr = e.getText().split("of ");
        return Integer.parseInt(arr[arr.length - 1]);
    }

    @Step("Select '{1}' from {0} dropdown")
    public void standardDropdownElement(String name, String value) {
        try {
            String obj = "//*[text()='" + name + "']//following::div[1]";
            driver.findElement(By.xpath(obj)).click();
            String opt = "//div[contains(@class,'option') and text()='" + value + "']";
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(opt)));
            driver.findElement(By.xpath(opt)).click();
            logStep(value + " selected from dropdown " + name);
        } catch (Exception ex) {
            logStep("Dropdown selection failed for " + name + ": " + ex.getMessage());
            captureScreenshot("Dropdown failure");
        }
    }

    @Step("Verify non-link text '{0}' presence")
    public void nonLinkText(String text) {
        WebElement obj = driver.findElement(By.xpath("(//*[@class='breadcrumbNonClick'])[1]"));
        logStep(obj.getText().equals(text) ? text + " is present" : text + " is not present");
    }

    @Step("Click link text '{0}'")
    public void clickLinkText(String text) {
        try {
            driver.findElement(By.xpath("//a[text()='" + text + "']")).click();
            logStep(text + " link clicked");
        } catch (Exception ex) {
            logStep(text + " link click failed: " + ex.getMessage());
            captureScreenshot("Link click failure");
        }
    }

    @Step("Select '{0}' from top navigation section")
    public void topNavigationSection(String text) {
        WebElement nav = driver.findElement(By.xpath("//div[contains(@class,'TopNavbar_profileDivParent')]"));
        nav.click();
        driver.findElement(By.xpath("//span[text()='" + text + "']")).click();
        logStep(text + " clicked in top navigation");
    }

    @Step("Verify valid date format: {0}")
    public void isValidDateFormat(String dateStr) {
        String expected = "yyyy-mm-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(expected);
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(dateStr);
            Assert.assertEquals(sdf.format(date), dateStr);
            logStep(dateStr + " matches format " + expected);
        } catch (Exception ex) {
            logStep(dateStr + " does not match format: " + ex.getMessage());
            captureScreenshot("Date format failure");
            Assert.fail(dateStr + " invalid format");
        }
    }

    @Step("Verify text '{1}' in column '{0}'")
    public void isTextPresentInColumn(String columnName, String text) {
        try {
            String obj = "(//tr[@role='row'][1]//span[@class='p-column-title' and text()='" + columnName + "']//following-sibling::span)[2]";
            String actual = driver.findElement(By.xpath(obj)).getText();
            Assert.assertEquals(actual, text);
            logStep(text + " found in column " + columnName);
        } catch (Exception ex) {
            logStep("Column verification failed: " + ex.getMessage());
            captureScreenshot("Column failure");
            Assert.fail("Text not present in column: " + columnName);
        }
    }

    /** Reusable helper **/
    @Step("{0}")
    public void logStep(String message) {
        System.out.println("[STEP] " + message);
    }

    @Attachment(value = "{0}", type = "image/png")
    public byte[] captureScreenshot(String name) {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            System.out.println("Screenshot error: " + e.getMessage());
            return new byte[0];
        }
    }
}
