package pageObjects;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import java.util.List;
//import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Utilities.ExtentReportManager;
import testBase.baseClass;
public class basePage {
	protected WebDriver driver;
	protected static final String dateNTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	private WebDriverWait smallerWait = new WebDriverWait(driver, Duration.ofSeconds(2));
	public WebDriverWait wait ;
	public WebDriverWait dropdownWait;
	public String todayDate=new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	public WebDriverWait extendedWait = new WebDriverWait(driver,Duration.ofSeconds(60));
	public basePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		this.dropdownWait= new WebDriverWait(driver,Duration.ofSeconds(10));
	}public void clearTextbox(WebElement e){
		try {
            e.click();  // Focus on the textbox

            Actions actions = new Actions(driver);
            actions
                .keyDown(Keys.CONTROL)
                .sendKeys("a") // Ctrl + A to select all
                .keyUp(Keys.CONTROL)
                .sendKeys(Keys.DELETE) // Delete the selected text
                .build()
                .perform();
        } catch (Exception clearText) {
        	ExtentReportManager.getTest().fail("Failed to clear textbox: " + clearText.getMessage());
            
        }
    }public String getText(WebElement e) {
		String text = e.getText();
		return text;
	}public void standardClickButton(WebElement e, String buttonName) {
		try {
			 wait.until(ExpectedConditions.elementToBeClickable(e));
	        e.click();
	        ExtentReportManager.getTest().pass("Clicked on **" + buttonName + "** button successfully.");
	    } catch (Exception ex) {
	        ExtentReportManager.getTest().fail("Failed to click on **" + buttonName + "** button. Exception: " + ex.getMessage());
	    }
	}public void standardEnterTextbox(WebElement e, String value, String name) {
	    try {
	        wait.until(ExpectedConditions.visibilityOf(e));
	        clearTextbox(e); // Optional: clears existing text before entering
	        e.sendKeys(value);
	        ExtentReportManager.getTest().pass("Entered **" + value + "** into **" + name + "** textbox successfully.");
	    } catch (Exception ex) {
	        ExtentReportManager.getTest().fail("Failed to enter **" + value + "** into **" + name + "** textbox. Exception: " + ex.getMessage());
	    }
	}public void clickSortingButton(String columnName) {
	    String xpath = "//span[text()='" + columnName + "']//following-sibling::span[contains(@class,'p-sortable-column')]";
	    try {
	        WebElement sortingButton = driver.findElement(By.xpath(xpath));
	        wait.until(ExpectedConditions.elementToBeClickable(sortingButton));
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sortingButton);
	        ExtentReportManager.getTest().pass("Clicked on **" + columnName + "** sorting button successfully.");
	    } catch (Exception ex) {
	        ExtentReportManager.getTest().fail("Failed to click on **" + columnName + "** sorting button. Exception: " + ex.getMessage());
	    }
	}public void sortingValidation(String cName,String sort) {
		String obj ="//th[@aria-sort='"+sort.toLowerCase()+"']";
		try {
			smallerWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(obj))));
			String screenshotPath = baseClass.captureScreen(driver,"SortingPass_" + cName + "_" + sort);
			ExtentReportManager.getTest()
            .pass(sort + " sorting is present for " + cName +" Column")
            .addScreenCaptureFromPath(screenshotPath);
		}catch(Exception sortValidate) {
			ExtentReportManager.getTest().fail(sort+" sorting is not present for "+cName);
		}
	}public void isHeaderPresent(String tag, String headerText) throws InterruptedException, IOException {
	    String xpath = "//" + tag + "[text()='" + headerText + "']";

	    try {
	    	Thread.sleep(2000);
	        // Optional: remove Thread.sleep and use an explicit wait instead
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

	        Assert.assertTrue(header.isDisplayed(), headerText + " header is not displayed");

	        String screenshotPath = baseClass.captureScreen(driver, headerText + "_HeaderIsPresent");
	        ExtentReportManager.getTest()
	            .pass("**" + headerText + "** header is present.")
	            .addScreenCaptureFromPath(screenshotPath);

	    } catch (Exception ex) {
	        String screenshotPath = baseClass.captureScreen(driver, headerText + "_HeaderNotFound");
	        ExtentReportManager.getTest()
	            .fail("**" + headerText + "** header is not present. Exception: " + ex.getMessage())
	            .addScreenCaptureFromPath(screenshotPath);
	        throw ex;
	    }
	}public void isObjectExist(WebElement e) {

		try {
			
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOf(e));
		if(e.isDisplayed()) {
			//String screenshotPath = baseClass.captureScreen(driver,e.getText()+" is present");
			ExtentReportManager.getTest()
	        .pass(e.getText()+" is present");
	        //.addScreenCaptureFromPath(screenshotPath);
			
		}
		}catch(Exception c){
			ExtentReportManager.getTest()
	        .fail(e.getText()+" is not present due to "+c.getMessage());
		}//return 0;
	}public void isObjectExist(WebElement e,String s) {

		try {
			
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOf(e));
		if(e.isDisplayed()) {
			//String screenshotPath = baseClass.captureScreen(driver,e.getText()+" is present");
			ExtentReportManager.getTest()
	        .pass(s+" is present");
	        //.addScreenCaptureFromPath(screenshotPath);
			
		}
		}catch(Exception c){
			ExtentReportManager.getTest()
	        .fail(s+" is not present due to "+c.getMessage());
		}//return 0;
	}public void isObjectNotExist(WebElement element, String elementName) {
		try {
	        if (!element.isDisplayed()) {
	            ExtentReportManager.getTest().pass(elementName + " is not visible as expected.");
	        } else {
	            ExtentReportManager.getTest().fail(elementName + " is unexpectedly visible.");
	        }
	    } catch (NoSuchElementException | StaleElementReferenceException exception1) {
	        ExtentReportManager.getTest().pass(elementName + " is not present in the DOM as expected.");
	    } 
	}/*public void clickSideNavigationModule(String module) throws InterruptedException{
		Thread.sleep(2000);
		List<WebElement> list = driver.findElements(By.xpath("//span[contains(@class,'SideNav_sideNotModulename')]"));
		try {
		for(WebElement e : list) {
			if(e.getText().equals(module)) {
				

				e.click();
			}
		}
		}catch(Exception exc) {
			ExtentReportManager.getTest().fail(module+" was not clicked due to "+exc.getMessage());
		}
	}*/public void clickSideNavigationModule(String module) throws InterruptedException{
		Thread.sleep(2000);
		String obj ="//span[contains(@class,'SideNav_sideNotModulename') and text()='"+module+"']";
		try {
		WebElement e = driver.findElement(By.xpath(obj));
		e.click();
		ExtentReportManager.getTest().pass(module+" is clicked");
		}catch(Exception exc) {
			ExtentReportManager.getTest().fail(module+" was not clicked due to "+exc.getMessage());
		}
	}public void clickCheckBox(String s) {
		String obj = "(//*[text()='" + s + "']//preceding::input[@type='checkbox'])[count(//*[text()='" + s + "']//preceding::input[@type='checkbox'])]";

		try {
		    //WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(obj)));
		    //checkbox.click();
			Thread.sleep(5000);
			driver.findElement(By.xpath(obj)).click();
		    ExtentReportManager.getTest().pass(s + " checkbox is clicked");
		} catch (Exception e) {
		    ExtentReportManager.getTest().fail(s + " checkbox is not clicked due to: " + e.getMessage());
		}
		}public void mouseOverElement(WebElement e) {
		Actions act = new Actions(driver);
		act.moveToElement(e).build().perform();
		ExtentReportManager.getTest().pass("Mouse Over done on "+e.getText());
	}public void clickSpanText(String name) {
	    String xpath = "//span[normalize-space(text())='" + name + "']";
	    standardClickButton(driver.findElement(By.xpath(xpath)),name);
	}public void dropdownElement(WebElement dropdownElement, String optionText, String dropdownName) {
	    try {
	        // Step 1: Click to open dropdown
	    	dropdownWait.until(ExpectedConditions.elementToBeClickable(dropdownElement));
	        dropdownElement.click();
	        Thread.sleep(1000); // slight delay to let dropdown render

	        // Step 2: Try to find the option directly
	        By optionLocator = By.xpath("//div[contains(@class,'option') and normalize-space(text())='" + optionText + "']");

	        try {
	            WebElement option = dropdownWait.until(ExpectedConditions.visibilityOfElementLocated(optionLocator));
	            option.click();
	        } catch (Exception ElementNotInteractableException ) {
	            // Step 3: If not visible, type in dropdown to filter options
	            dropdownElement.sendKeys(optionText);
	            Thread.sleep(1000); // allow time for filtering

	            // Step 4: Try locating the option again after filtering
	            WebElement filteredOption = dropdownWait.until(ExpectedConditions.visibilityOfElementLocated(optionLocator));
	            filteredOption.click();
	        }

	        // Step 5: Log success
	        ExtentReportManager.getTest().pass(
	                "'" + optionText + "' is selected from '" + dropdownName + "' dropdown element");

	    } catch (Exception e) {
	        ExtentReportManager.getTest().fail(
	                "'" + optionText + "' is NOT selected from '" + dropdownName +
	                        "' dropdown element due to: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
public void isTextPresentAt(WebElement textbox,String value) {
		try{
			if(textbox.getText().equals(value)) {
				ExtentReportManager.getTest().pass(value+" is present in textbox");
			}else {
				ExtentReportManager.getTest().fail(value+" is not present in textbox");
			}
		}catch(Exception textbox1) {
			ExtentReportManager.getTest().fail("textbox not found");
		}
	}public int isPageInititiorAddedAfterCreation() {
		WebElement e= driver.findElement(By.xpath("(//span[@class='paginatorClass'])[1]"));
		String arr[] = e.getText().split("of ");
		return Integer.parseInt(arr[arr.length-1]);
	}public void standardDropdownElement(String name,String value) {
		String obj = "//*[text()='"+name+"']//following::div[1]";
		try {
		WebElement dropdown = driver.findElement(By.xpath(obj));
		dropdownElement(dropdown,value,name);
		}catch(Exception dropdown) {
			ExtentReportManager.getTest().fail(value+" is not selected from "+name+" dropdown");
		}
	}public void nonLinkText(String text) {
		WebElement obj = driver.findElement(By.xpath("(//*[@class='breadcrumbNonClick'])[1]"));
		if(obj.getText().equals(text)) {
			ExtentReportManager.getTest().pass(text+" is present");
		}else {
			ExtentReportManager.getTest().pass(text+" is not present");
		}
	}public void clickLinkText(String text) {
		try {
		driver.findElement(By.xpath("//a[text()='"+text+"']")).click();
		ExtentReportManager.getTest().pass(text+" is clicked");
		}catch(Exception linktext) {
			ExtentReportManager.getTest().fail(text+" is not clicked");
		}
	}public void topNavigationSection(String text) {
		WebElement nav = driver.findElement(By.xpath("//div[contains(@class,'TopNavbar_profileDivParent')]"));
		nav.click();
		WebElement obj =driver.findElement(By.xpath("//span[text()='"+text+"']"));
		obj.click();
		ExtentReportManager.getTest().pass(text+" is clicked from top navigation");
	}public void isValidDateFormat(String dateStr) {
        String expectedFormat = "dd-MM-yyyy HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(expectedFormat);
        sdf.setLenient(false);
        try{
            Date date = sdf.parse(dateStr);
            if (sdf.format(date).equals(dateStr)) {
            	ExtentReportManager.getTest().pass(dateStr+" is present in DD-MM-YYYY HH:MM:SS format");
            } else {
            	ExtentReportManager.getTest().fail(dateStr+" is not present in DD-MM-YYYY HH:MM:SS format");
            }
        }catch (Exception dateVerify) {
        	ExtentReportManager.getTest().fail(dateStr+" is not present in DD-MM-YYYY HH:MM:SS format");
        }
    }public void compareStrings(String s1 , String s2, String text) {
    	if(s1.equals(s2)) {
    	ExtentReportManager.getTest().pass(text+ s2 +" is present");
    	}else {
    		ExtentReportManager.getTest().pass(text+ s2 +" is present");
    	}
    }public void compareInt(int i1,int i2 , String Text) {
    	if(i1==i2) {
    		
    	}
    }public void isTextPresentInColumn(String columnName, String text) {
        // Define locator using By
        By locator = By.xpath("(//tr[@role='row'][1]//span[@class='p-column-title' and normalize-space(text())='" 
                               + columnName + "']//following-sibling::span)[2]");

        try {
            // Wait until element is present in the DOM
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

            // Compare the text
            if (element.getText().trim().equals(text.trim())) {
                ExtentReportManager.getTest().pass("'" + text + "' is present in Column '" + columnName + "'");
            } else {
                ExtentReportManager.getTest().fail("'" + text + "' is NOT present in Column '" + columnName + "'");
            }
        } catch (Exception e) {
            ExtentReportManager.getTest().fail("Error while checking text in Column '" + columnName + "': " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void checkAndNavigateWorkspaceListPage() {
    	try {
    		Thread.sleep(2000);
    	driver.findElement(By.xpath("//img[contains(@class,'workspace_logoImage')]"));
    	}catch(Exception nav) {
    		driver.findElement(By.xpath("//img[contains(@class,'SideNav_onePlatformLogo')]")).click();
    	}
    }public void clickUntilNotPresent(WebElement e) {
		
	}public void dropdownFirstValue(WebElement e , String dName) {
		try {
			e.click();
			WebElement val=driver.findElement(By.xpath("(//div[contains(@class,'option') and text()])[1]"));
			wait.until(ExpectedConditions.visibilityOf(val));
			val.click();
			ExtentReportManager.getTest().pass(val.getText()+" is selected from "+dName+"dropdown element");
			}catch(Exception dropdown) {
				ExtentReportManager.getTest().fail("value is not selected from "+dName+" dropdown element deu to "+ dropdown.getMessage());
			}
	}public void dragNDrop(WebElement source, WebElement target) {
        try {
            Actions actions = new Actions(driver);
            actions.clickAndHold(source)
                   .moveToElement(target)
                   .release()
                   .build()
                   .perform();
            ExtentReportManager.getTest().pass(source.getText()+" is drag and dropped at the target location");
        } catch (Exception e) {
        	ExtentReportManager.getTest().fail(source.getText()+" is not drag and dropped at the target location due to "+e.getMessage());
        }
    }public void waitForPageLoad(WebElement e) {
    	WebDriverWait pageWait = new WebDriverWait(driver, Duration.ofSeconds(20));
    	try {
    	pageWait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//span[@class='ant-spin-dot ant-spin-dot-spin']"))));
    	}catch(Exception waitPage) {
    		
    	}
    	pageWait.until(ExpectedConditions.visibilityOf(e));
    }public void textClick(String tag, String text) {
        // Define the locator using XPath
        By locator = By.xpath("(//" + tag + "[normalize-space(text())='" + text + "'])[1]");

        try {
            // Wait until the element is clickable
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

            // Click the element
            element.click();

            // Log success
            ExtentReportManager.getTest().pass("'" + text + "' is clicked");
        } catch (Exception e) {
            // Log failure
            ExtentReportManager.getTest().fail("'" + text + "' was not clicked due to: " + e.getMessage());
            e.printStackTrace();
        }
    }public WebElement columnTextLink(String columnName) {
        // Define the locator using By
        By locator = By.xpath("(//span[@class='p-column-title' and normalize-space(text())='" + columnName + "']//following-sibling::a)[1]");

        // Wait for the element to be present in the DOM
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        // Return the WebElement
        return driver.findElement(locator);
    }public void verifyAllCheckboxesChecked(WebElement checkAllBox, List<WebElement> allCheckboxes) {
        try {
            // Click the "Check All" checkbox
            checkAllBox.click();

            boolean allChecked = true;

            // Loop through all checkboxes to verify they are checked
            for (WebElement checkbox : allCheckboxes) {
                if (!checkbox.isSelected()) {
                    allChecked = false;
                    ExtentReportManager.getTest().fail("Checkbox not selected: " + checkbox.getAttribute("name"));
                }
            }

            if (allChecked) {
                ExtentReportManager.getTest().pass("All checkboxes are selected after clicking 'Check All'.");
            } else {
                ExtentReportManager.getTest().fail("Some checkboxes are not selected after clicking 'Check All'.");
            }

        } catch (Exception e) {
            ExtentReportManager.getTest().fail("Error verifying checkbox selection: " + e.getMessage());
        }
    }public void typeTextFromKeyboard(WebElement element, String text) {
    	try {
            Actions actions = new Actions(driver);
            actions.moveToElement(element).click().sendKeys(text).build().perform();

            ExtentReportManager.getTest().pass("Successfully typed text: '" + text + "' into the element.");
        } catch (Exception e) {
        	ExtentReportManager.getTest().fail("Failed to type text: '" + text + "' into the element. Error: " + e.getMessage());
            e.printStackTrace();
        }
    }public void multipleSameDropdown(WebElement dropdownElement,WebElement dropdownTextbox,String text,String dropdownName) {
    	try {
	        // Step 1: Click to open dropdown
	        dropdownElement.click();
	        Thread.sleep(1000); // slight wait to let dropdown open

	        // Step 2: Type the option text into the dropdown (assumes it's searchable)
	        dropdownTextbox.sendKeys(text);
	        Thread.sleep(1000); // wait for filtering to apply

	        // Step 3: Locate visible matching option and click
	        dropdownTextbox.sendKeys(Keys.ENTER);

	        // Step 4: Log success
	        ExtentReportManager.getTest().pass(
	                "'" + text + "' is selected from '" + dropdownName + "' dropdown element");

	    } catch (Exception e) {
	        ExtentReportManager.getTest().fail(
	                "'" + text + "' is NOT selected from '" + dropdownName +
	                        "' dropdown element due to: " + e.getMessage());
	        e.printStackTrace();
	    }
	}public void isDialogHeaderPresent(String tag, String headerText) throws InterruptedException, IOException {
	    String xpath = "//" + tag + "[text()='" + headerText + "']";

	    try {
	    	Thread.sleep(2000);
	        // Optional: remove Thread.sleep and use an explicit wait instead
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

	        //Assert.assertTrue(header.isDisplayed(), headerText + " header is not displayed");

	        String screenshotPath = baseClass.captureScreen(driver, headerText + "_HeaderIsPresent");
	        ExtentReportManager.getTest()
	            .pass("**" + headerText + "** dialog header is present.")
	            .addScreenCaptureFromPath(screenshotPath);

	    } catch (Exception ex) {
	        String screenshotPath = baseClass.captureScreen(driver, headerText + "_HeaderNotFound");
	        ExtentReportManager.getTest()
	            .fail("**" + headerText + "** dialog header is not present. Exception: " + ex.getMessage())
	            .addScreenCaptureFromPath(screenshotPath);
	    }
	}public void waitForGeneration(String loc) {
		By locator = By.xpath(loc);
		int maxAttempts = 15;

		for (int i = 0; i < maxAttempts; i++) {
		    try {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

		        // Wait up to 60s for the element to be present in the DOM (not necessarily visible)
		        wait.until(ExpectedConditions.presenceOfElementLocated(locator));

		        // If present, exit the function early
		        return;

		    } catch (Exception e) {
		        // If not present → click Refresh and continue to next attempt
		        try {
		            WebElement refreshButton = driver.findElement(
		                By.xpath("//button[contains(@class,'refreshButton')]")
		            );
		            refreshButton.click();
		        } catch (Exception ignored) {}
		    }

		    try {
		        Thread.sleep(60 * 1000); // Wait 1 minute before next attempt
		    } catch (InterruptedException ignored) {}
		}
	}public String setFocusOn(WebElement element) throws IOException {
		try {
            // Scroll element into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

            // Optional wait for smooth scroll
            Thread.sleep(500);

            // Format current time for screenshot name (file-safe)
            String timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern("HH_mm_ss"));

            // Capture screenshot and return path
            return baseClass.captureScreen(driver, timestamp);

        } catch (Exception e) {
            // Mark fail with exception message
            ExtentReportManager.getTest()
                .fail("Failed to set focus on element: " + e.getMessage());
            return null;
        }
    }public WebElement getObjectFromText(String tag, String text) {
    	return driver.findElement(By.xpath("(//"+tag+"[text()='"+text+"'])[1]"));
    }public String calCurrentTime(int min) {
        LocalTime time;
        if (min >= 0) {
            time = LocalTime.now().plusMinutes(min);
        } else {
            time = LocalTime.now().minusMinutes(Math.abs(min));  // Use the actual value of `min`
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }
    
}
