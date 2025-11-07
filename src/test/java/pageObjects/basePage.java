package pageObjects;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import java.util.List;
import java.util.zip.ZipEntry;
//import java.util.NoSuchElementException;
import java.util.zip.ZipOutputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.testng.asserts.SoftAssert;

import Utilities.ExtentReportManager;
import testBase.baseClass;
public class basePage {
	protected WebDriver driver;
	protected String dateNTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	public String screenshotName=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	private WebDriverWait smallerWait = new WebDriverWait(driver, Duration.ofSeconds(2));
	public WebDriverWait wait ;
	public WebDriverWait dropdownWait;
	public String todayDate=new SimpleDateFormat("MM/dd/yyyy").format(new Date());
	public WebDriverWait extendedWait = new WebDriverWait(driver,Duration.ofSeconds(240));
	public Actions actions;
	public String pageName;
	public String filesFolderPath=ExtentReportManager.filesFolderPath+File.separator;
	public basePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		this.dropdownWait= new WebDriverWait(driver,Duration.ofSeconds(20));
	}public void clearTextbox(WebElement e) throws IOException{
		try {
			wait.until(ExpectedConditions.visibilityOf(e));
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
        	captureScreenshot();
            
        }
    }public String getText(WebElement e) {
		String text = e.getText();
		return text;
	}/*public void standardClickButton(WebElement e, String buttonName) {
		try {
			 wait.until(ExpectedConditions.elementToBeClickable(e));
	        e.click();
	        ExtentReportManager.getTest().pass("Clicked on **" + buttonName + "** button successfully.");
	    } catch (Exception ex) {
	        ExtentReportManager.getTest().fail("Failed to click on **" + buttonName + "** button. Exception: " + ex.getMessage());
	    }
	}*/public void standardClickButton(WebElement e, String buttonName){
	    try {
	        wait.until(ExpectedConditions.elementToBeClickable(e));
	        wait.until(driver -> e.isEnabled() && !"true".equals(e.getAttribute("aria-disabled")));

	        try {
	            e.click();
	            ExtentReportManager.getTest().pass("Clicked on **" + buttonName + "** button successfully.");
	        } catch (Exception clickEx) {
	            // Fallback to JavaScript click if normal click fails
	            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);
	            ExtentReportManager.getTest().pass("Clicked on **" + buttonName + "** button successfully using JavaScript.");
	        }

	    } catch (Exception ex) {
	        ExtentReportManager.getTest().fail("Failed to click on **" + buttonName + "** button. Exception: " + ex.getMessage());
	        //captureScreenshot();
	    }
	} public void setPageName(String name) {
        pageName = name;
    }
    public void standardEnterTextbox(WebElement e, String value, String name) throws IOException {
	    try {
	        wait.until(ExpectedConditions.visibilityOf(e));
	        clearTextbox(e); // Optional: clears existing text before entering
	        e.sendKeys(value);
	        ExtentReportManager.getTest().pass("Entered **" + value + "** into **" + name + "** textbox successfully.");
	    } catch (Exception ex) {
	    	//throw new AssertionError("Failed to enter **" + value + "** into **" + name + "** textbox. Exception: " + ex.getMessage());
	        ExtentReportManager.getTest().fail("Failed to enter **" + value + "** into **" + name + "** textbox. Exception: " + ex.getMessage());
	        captureScreenshot();
	        
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
	}public void sortingValidation(String cName,String sort) throws IOException {
		String obj ="//th[@aria-sort='"+sort.toLowerCase()+"']";
		try {
			smallerWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(obj))));
			String screenshotPath = baseClass.captureScreen(driver,"SortingPass_" + cName + "_" + sort);
			ExtentReportManager.getTest()
            .pass(sort + " sorting is present for " + cName +" Column")
            .addScreenCaptureFromPath(screenshotPath);
		}catch(Exception sortValidate) {
			ExtentReportManager.getTest().fail(sort+" sorting is not present for "+cName);
			captureScreenshot();
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
	        Assert.fail();
	    }
	}public void isObjectExist(WebElement e) throws IOException {
	    try {
	        wait.until(ExpectedConditions.visibilityOf(e));
	        if (e.isDisplayed()) {
	            ExtentReportManager.getTest()
	                .pass("'" + e.getText() + "' is present on the page.");
	        }
	    } catch (Exception ex) {
	        String elementText = "Element"; // fallback name
	        try {
	            elementText = e.getText(); // will fail if element truly not present
	        } catch (Exception innerEx) {
	            // ignore, use fallback
	        }
	        ExtentReportManager.getTest()
	            .fail("'" + elementText + "' is not present. Exception: " + ex.getMessage());
	        captureScreenshot();
	    }
	}
public void isObjectExist(WebElement e,String s) throws IOException {

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
			captureScreenshot();
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
	}*/public void clickSideNavigationModule(String module) throws InterruptedException, IOException{
		Thread.sleep(2000);
		String obj ="//span[contains(@class,'SideNav_sideNotModulename') and text()='"+module+"']";
		try {
		WebElement e = driver.findElement(By.xpath(obj));
		e.click();
		ExtentReportManager.getTest().pass(module+" is clicked from side Navigation Module");
		}catch(Exception exc) {
			ExtentReportManager.getTest().fail(module+" was not clicked due to "+exc.getMessage());
			captureScreenshot();
		}
	}public void clickCheckBox(String s) throws IOException {
		String obj = "(//*[text()='" + s + "']//preceding::input[@type='checkbox']/..)[count(//*[text()='" + s + "']//preceding::input[@type='checkbox'])]";

		try {
		    //WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(obj)));
		    //checkbox.click();
			Thread.sleep(5000);
			driver.findElement(By.xpath(obj)).click();
		    ExtentReportManager.getTest().pass("**"+s + "** checkbox is clicked");
		} catch (Exception e) {
			try {
				String obj1="(//*[text()='"+s+"']//following::input[@type='checkbox'])[1]";
				driver.findElement(By.xpath(obj1)).click();
			    ExtentReportManager.getTest().pass(s + " checkbox is clicked");
			}catch(Exception d) {
				ExtentReportManager.getTest().fail(s + " checkbox is not clicked due to: " + e.getMessage());
				captureScreenshot();
			}
		}
		}public void mouseOverElement(WebElement e) {
		Actions act = new Actions(driver);
		act.moveToElement(e).build().perform();
		ExtentReportManager.getTest().pass("Mouse Over done on "+e.getText());
	}public void clickSpanText(String name) {
		try {
	    String xpath = "(//span[normalize-space(text())='" + name + "'])[1]";
	    standardClickButton(driver.findElement(By.xpath(xpath)),name);
		}catch(Exception e) {
			ExtentReportManager.getTest().fail("Failed to click on element "+name+" " + e.getMessage());
		}
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
	        	By loc = By.xpath("//label[text()='"+dropdownName+"']//following::input[1]");
	        	WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(loc));
	        	dropdown.sendKeys(optionText);
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
public void isTextPresentAt(WebElement textbox,String value) throws IOException {
		try{
			if(textbox.getText().equals(value)) {
				ExtentReportManager.getTest().pass(value+" is present in textbox");
			}else {
				ExtentReportManager.getTest().fail(value+" is not present in textbox");
			}
		}catch(Exception textbox1) {
			ExtentReportManager.getTest().fail("textbox not found");
			captureScreenshot();
		}
	}public int isPageInititiorAddedAfterCreation() {
		WebElement e= driver.findElement(By.xpath("(//span[@class='paginatorClass'])[1]"));
		String arr[] = e.getText().split("of ");
		return Integer.parseInt(arr[arr.length-1]);
	}public void standardDropdownElement(String name,String value) {
		By xpath = By.xpath("//*[text()='"+name+"']//following::div[1]");
		
		try {
			WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(xpath));
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
        	
            // Wait for spinner to disappear (using By directly, so it doesn’t throw if element isn’t found)
            pageWait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'ant-spin') and contains(@class, 'ant-spin-spinning')]")
                
            ));
            pageWait.until(ExpectedConditions.visibilityOf(e));
            
        } catch (Exception ignored) {
            // Optional: log spinner not found or timeout
        }

        // Wait for target element to be visible
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
	        captureScreenshot();
	        

	    } catch (Exception ex) {
	    	captureScreenshot();
	        Assert.fail();
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
    }public void setFocusOn() throws IOException  {
		try {
            // Scroll element into view

            // Optional wait for smooth scroll
            Thread.sleep(1000);

            // Format current time for screenshot name (file-safe

            // Capture screenshot and return path
           
            captureScreenshot();
        } catch (Exception e) {
            // Mark fail with exception message
            ExtentReportManager.getTest()
                .fail("Failed to set focus on element: " + e.getMessage());
            captureScreenshot();
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
    }public void isTextPresentInColumnDiv(String columnName, String text) {
        // Define locator using By
        By locator = By.xpath("(//tr[@role='row'][1]//span[@class='p-column-title' and normalize-space(text())='" 
                               + columnName + "']//following-sibling::div)[1]");

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
    }public void isTextPresentInColumnTable(String columnName, String text) {
    	String s ="//span[text()='"+columnName+"']//parent::td";
    	try {
    		WebElement e =driver.findElement(By.xpath(s));
    		if(e.getText().contains(s)) {
    			ExtentReportManager.getTest().pass("'" + text + "' is present in Column '" + columnName + "'");
    		}else {
                ExtentReportManager.getTest().fail("'" + text + "' is NOT present in Column '" + columnName + "'");
            }
    	}catch (Exception e) {
            ExtentReportManager.getTest().fail("Error while checking text in Column '" + columnName + "': " + e.getMessage());
            e.printStackTrace();
        }
    	
    }public WebElement getColumnElement(String cName) {
    	String obj ="//table[@class='p-datatable-table']//td//*[text()='"+cName+"']//following-sibling::*";
    	try {
    		return driver.findElement(By.xpath(obj));
    	}catch(Exception e) {
    		return null;
    	}
    }public void dragAndDrop(WebElement source, WebElement target) {
    	try{
    		actions = new Actions(driver);
    		 actions.clickAndHold(source)
             .moveToElement(target)
             .release()
             .build()
             .perform();
    	}catch(Exception drag) {
    		ExtentReportManager.getTest().fail("due to "+drag.getMessage());
    	}
    }public void doubleClick(WebElement element, String elementName) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            actions.doubleClick(element).perform();
            ExtentReportManager.getTest().pass("Double clicked on **" + elementName + "** successfully.");
        } catch (Exception ex) {
            ExtentReportManager.getTest().fail("ailed to double click on **" + elementName + "**. Exception: " + ex.getMessage());
            throw ex; // rethrow to mark test as failed
        }
    }public void typeAheadWebElement(String name,String value) {
    	By element = By.xpath("//*[text()='"+name+"']//following::div[@class='ant-select-selector'][1]");
		By text = By.xpath("(//*[text()='"+name+"']//following::input[@type='search'])[1]");
		String valueElement = "(//*[contains(@class,'option') and text()='"+value+"'])[1]";
		try {
			WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(element));
			WebElement textbox = wait.until(ExpectedConditions.elementToBeClickable(text));
			dropdown.click();
			Thread.sleep(2000);
			actions = new Actions(driver);
			
			actions.moveToElement(textbox).click().sendKeys(value).build().perform();
			driver.findElement(By.xpath(valueElement)).click();
		}catch(Exception dropdown) {
			ExtentReportManager.getTest().fail(value+" is not selected from "+name+" dropdown "+dropdown.getMessage());
		}
    }public void enterInTextbox(String name,String value) {
    	By loc =By.xpath("(//label[text()='"+name+"']//following::input)[1]");
    	try {
    	WebElement textbox = wait.until(ExpectedConditions.elementToBeClickable(loc));
    	standardEnterTextbox(textbox,value,name);
    	}catch(Exception e) {
    		ExtentReportManager.getTest().fail("Failed to enter value "+value+" in the "+name+" textbox");
    	}
    }public void uploadFile(String filePath) {
    	try {
        Robot robot = new Robot();
        Thread.sleep(2000);

        // Copy file path to clipboard
        StringSelection selection = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);

        // Paste (CTRL + V)
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        Thread.sleep(2000);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        // Press TAB twice
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        Thread.sleep(2000);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        Thread.sleep(2000);

        // Press ENTER
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        ExtentReportManager.getTest().pass("File uploaded successfully");
    	}catch(Exception upload){
    		ExtentReportManager.getTest().fail("Failed to upload the file");
    	}
    }public void downloadFile(String filePath) throws Exception {
    	Robot robot = new Robot();
    	Thread.sleep(5000);

        // Copy file path to clipboard
        StringSelection selection = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        Thread.sleep(2000);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        // Press TAB twice
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        Thread.sleep(2000);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        Thread.sleep(2000);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        Thread.sleep(2000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }public static void createExcelFile(String filePath, String sheetName) throws IOException {
        // Create workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Create sheet with the given name
        XSSFSheet sheet = workbook.createSheet(sheetName);

        // Save file at provided path
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        
        // Cleanup
        fos.close();
        workbook.close();

        System.out.println("Excel file created successfully at: " + filePath);
    }public static void sendMailViaOutlook(String to, String subject, String body, String attachmentPath) {
        try {
            // Build PowerShell script	
            String psScript =
                "$Outlook = New-Object -ComObject Outlook.Application;" +
                "$Mail = $Outlook.CreateItem(0);" +
                "$Mail.To = '" + to + "';" +
                "$Mail.Subject = '" + subject + "';" +
                "$Mail.Body = '" + body + "';" +
                (attachmentPath != null && !attachmentPath.isEmpty()
                        ? "$Mail.Attachments.Add('" + attachmentPath.replace("\\", "\\\\") + "');"
                        : "") +
                "$Mail.Send();";

            // Save PowerShell script
            File tempScript = File.createTempFile("outlookMail", ".ps1");
            try (FileWriter writer = new FileWriter(tempScript)) {
                writer.write(psScript);
            }

            // Use ProcessBuilder to run PowerShell
            ProcessBuilder pb = new ProcessBuilder(
                "powershell", "-ExecutionPolicy", "Bypass", "-File", tempScript.getAbsolutePath()
            );
            pb.inheritIO(); // (optional) show PowerShell output/errors in console
            Process process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("✅ Email sent successfully via Outlook");
            } else {
                System.err.println("❌ Failed to send email. Exit code: " + exitCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }public static void zipFileOrFolder(String sourcePathStr) throws IOException {
        File sourceFile = new File(sourcePathStr);

        if (!sourceFile.exists()) {
            throw new FileNotFoundException("Source file/folder does not exist: " + sourcePathStr);
        }

        String zipFileName = sourceFile.getName() + ".zip";
        File zipFile = new File(sourceFile.getParent(), zipFileName);

        try (
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos)
        ) {
            if (sourceFile.isDirectory()) {
                zipDirectory(sourceFile, sourceFile.getName(), zos);
            } else {
                zipSingleFile(sourceFile, zos);
            }
        }

        System.out.println("Zip created successfully: " + zipFile.getAbsolutePath());
    }

    private static void zipDirectory(File folder, String parentFolder, ZipOutputStream zos) throws IOException {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                zipDirectory(file, parentFolder + "/" + file.getName(), zos);
                continue;
            }
            try (FileInputStream fis = new FileInputStream(file)) {
                String zipEntryName = parentFolder + "/" + file.getName();
                ZipEntry zipEntry = new ZipEntry(zipEntryName);
                zos.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zos.write(bytes, 0, length);
                }

                zos.closeEntry();
            }
        }
    }

    private static void zipSingleFile(File file, ZipOutputStream zos) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            ZipEntry zipEntry = new ZipEntry(file.getName());
            zos.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }

            zos.closeEntry();
        }
    }public void getPipelineStatus(WebElement e,String pName) throws IOException {
    	wait.until(ExpectedConditions.visibilityOf(e));
    	if(e.getText().equalsIgnoreCase("Success")) {
    		ExtentReportManager.getTest().pass("Pipeline "+pName+" is successfully completed");
    		String screenshotPath = baseClass.captureScreen(driver, pName + "Pipiline is executed successfully");
	        ExtentReportManager.getTest()
	            .pass("**" + pName + "** header is present.")
	            .addScreenCaptureFromPath(screenshotPath);
    	}else {
    		ExtentReportManager.getTest().pass("Pipeline "+pName+" is failed");
    	}
    }public void dialogCloseButton(String tag,String name) throws IOException {
    	String obj="(//"+tag+"[text()='"+name+"']//following::button[contains(@class,'commonPanel_closeButton')])[1]";
    	standardClickButton(driver.findElement(By.xpath(obj)),name+" Dialog Box Close Button");
    }public void captureScreenshot() throws IOException {
    	String screenshotPath = baseClass.captureScreen(driver, screenshotName);
        ExtentReportManager.getTest().addScreenCaptureFromPath(screenshotPath);
    }public void isDataPresentInColumn( String columnName, String expectedValue) {
        try {
        	String loc ="//table[@class='p-datatable-table']";
            WebElement table = driver.findElement(By.xpath(loc));
            List<WebElement> headers = table.findElements(By.xpath(loc+"//thead/tr/th"));
            int colIndex = -1;
            for (int i = 0; i < headers.size(); i++) {
                if (headers.get(i).getText().trim().equalsIgnoreCase(columnName)) {
                    colIndex = i;
                    break;
                }
            }
            if (colIndex == -1) {
                ExtentReportManager.getTest().fail("❌ Column '" + columnName + "' not found in table.");
                
            }
            List<WebElement> rows = table.findElements(By.xpath(loc+"//tbody/tr"));
            boolean found = false;
            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                if (cells.size() > colIndex) {
                    String cellValue = cells.get(colIndex).getText().trim();
                    if (cellValue.equalsIgnoreCase(expectedValue)) {
                        ExtentReportManager.getTest().pass("✅ Value '" + expectedValue + "' is present under column '" + columnName + "'.");
                        found = true;
                        captureScreenshot();
                        break;
                    }
                }
            }

            if (!found) {
                ExtentReportManager.getTest().fail("❌ Value '" + expectedValue + "' is not present under column '" + columnName + "'.");
                captureScreenshot();
            }
            
        } catch (Exception e) {
            ExtentReportManager.getTest().fail("❌ Exception while verifying value in table: " + e.getMessage());
            
        }
    }public void isSuccessMessagePresent(WebElement e) throws IOException {
	    try {
	        wait.until(ExpectedConditions.visibilityOf(e));
	        if (e.isDisplayed()) {
	            ExtentReportManager.getTest()
	                .pass("'" + e.getText() + "' success message is present on the page.");
	            captureScreenshot();
	        }
	    } catch (Exception ex) {
	        String elementText = "Element"; // fallback name
	        try {
	            elementText = e.getText(); // will fail if element truly not present
	        } catch (Exception innerEx) {
	            // ignore, use fallback
	        }
	        ExtentReportManager.getTest()
	            .fail("'" + elementText + "' success message is not present. Exception: " + ex.getMessage());
	        captureScreenshot();
	    }
	}public void clickRadioButton(String s) {
		String obj = "(//*[text()='" + s + "']//preceding::input[@type='radio']/..)[count(//*[text()='" + s + "']//preceding::input[@type='radio'])]";

		try {
		    //WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(obj)));
		    //checkbox.click();
			Thread.sleep(5000);
			driver.findElement(By.xpath(obj)).click();
		    ExtentReportManager.getTest().pass("**"+s + "** checkbox is clicked");
		} catch (Exception e) {
			try {
				String obj1="(//*[text()='"+s+"']//following::input[@type='checkbox'])[1]";
				driver.findElement(By.xpath(obj1)).click();
			    ExtentReportManager.getTest().pass(s + " checkbox is clicked");
			}catch(Exception d) {
				ExtentReportManager.getTest().fail(s + " checkbox is not clicked due to: " + e.getMessage());
			}
		}
		}
	public void dropdownElementByTextbox(WebElement dropdownElement,WebElement textbox, String optionText, String dropdownName) {
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
	        	textbox.sendKeys(optionText);
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
}
