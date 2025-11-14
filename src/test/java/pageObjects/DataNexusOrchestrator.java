package pageObjects;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.ExtentReportManager;

public class DataNexusOrchestrator extends basePage{
	public DataNexusOrchestrator(WebDriver driver) {
		super(driver);
	}public final String orchestratorName="Orchestrator"+dateNTime;
	@FindBy(xpath="//button[text()='Create New Process']")
	WebElement createNewProcessButton;
	@FindBy(xpath="//span[text()='+ Add Pipeline']")
	public WebElement addPipeline;
	@FindBy(xpath="//input[@name='processName']")
	public WebElement processName;
	@FindBy(xpath="//input[@name='processDescription']")
	public WebElement processDescription;
	@FindBy(xpath="//div[text()='Please Enter Process Name']")
	public WebElement mandatoryProcessNameMsg;
	@FindBy(xpath="(//span[@title='Ingestion']//parent::span)[1]")
	public WebElement selectPipelineType;
	@FindBy(xpath="(//div[text()='Select Pipeline']//following::div[@class='ant-select-selector'])[1]")
	public WebElement selectPipeline;
	@FindBy(xpath="(//div[text()='Dependent Upon (Optional)']//following::div[@class='ant-select-selector'])[1]")
	public WebElement dependentUpon;
	@FindBy(xpath="//p[text()='Schedules are updated successfully']")
	public WebElement processUpdatedMsg;
	@FindBy(xpath="//p[text()='Schedules are updated successfully']")
	public WebElement scheduleUpdatedMsg;
	@FindBy(xpath="//p[text()='Schedules are created successfully']")
	public WebElement scheduleCreatedMsg;
	@FindBy(xpath="//p[text()='Pipeline triggered successfully']")
	public WebElement pipelineTriggeredMessage;
	@FindBy(xpath="(//span[text()='Process Run ID']//following-sibling::div)[1]")
	public WebElement firstProcessId;
	@FindBy(xpath="//p[text()='Process deactivated successfully']")
	public WebElement pipelineDeacticatedMessage;
	@FindBy(xpath="//p[text()='Process deleted successfully']")
	public WebElement pipelineDeletedMessage;
	@FindBy(xpath="//div[text()='Please enter the Trigger Date']")
	public WebElement mandatoryTriggerDate;
	@FindBy(xpath="//div[text()='Please enter the Trigger Time']")
	public WebElement mandatoryTriggerTime;
	@FindBy(xpath="//div[text()='Trigger time cannot be in the past']")
	public WebElement pastTimeErrorMsg;
	@FindBy(xpath="//span[text()='Schedule ID']//parent::td")
	public WebElement scheduleID;
	@FindBy(xpath="//button[@class='ant-modal-close']")
	public WebElement closeButton;
	@FindBy(xpath="(//span[@class='anticon anticon-check-circle'])[1]")
	WebElement activePipeline;
	@FindBy(xpath="(//span[@class='anticon anticon-stop'])[1]")
	WebElement inactivePipeline;
	@FindBy(xpath="//p[text()='Process deactivated successfully']")
	public WebElement pipelineDeativatedMsg;
	@FindBy(xpath="//p[text()='Process activated successfully']")
	public WebElement pipelineAtivatedMsg;
	@FindBy(xpath="(//div[text()='Select Pipeline Type'])[2]//following::div[2]")
	WebElement MDMSelectFIleType;
	@FindBy(xpath="(//div[text()='Select Pipeline Type'])[2]//following::div[2]")
	WebElement selectPipelineDropdownTextbox;
	//@FindBy(xpath="")
	public void clickNewProcessButton() {
		standardClickButton(createNewProcessButton,"New Process");
	}public void clickAddPipelineButton() {
		standardClickButton(addPipeline,"Add Pipeline");
	}public void enterPipelineName(String pName) throws IOException {
		standardEnterTextbox(processName,pName,"Process Name");
	}public void enterPipelineDescription(String description) throws IOException {
		standardEnterTextbox(processDescription,description,"Process Description");
	}public void selectPipelineType(String pipeline) {
		dropdownElement(selectPipelineType,pipeline,"Pipeline Type");
	}public void clickAddedPipelineDeleteButton(String pName) {
		String xpath="(//span[text()='"+pName+"']/preceding::span[@class='anticon anticon-delete'])[count(//span[text()='"+pName+"']/preceding::span[@class='anticon anticon-delete'])]//parent::*[@class='ant-btn-icon']";
		WebElement e = driver.findElement(By.xpath(xpath));
		standardClickButton(e,pName+" Delete");
	}public void selectPipeline(String pipeline) {
		dropdownElementByTextbox(selectPipeline,selectPipelineDropdownTextbox,pipeline,"Pipeline");
	}public void selectPipelineDependentUpon(String pipeline) {
		dropdownElement(dependentUpon,pipeline,"Pipeline Depend Upon");
	}public void isTextPresentInColumnOrchestrator(String columnName, String text) {
        // Define locator using By
        By locator = By.xpath("(//tr[@role='row'][1]//span[@class='p-column-title' and normalize-space(text())='"+columnName+"']//following-sibling::div)[1]");

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
    }public void waitForProcessCompleted() {
    	By loc =By.xpath("(//div[contains(@class,'ProcessLogs_statusContainer')])[1]");
    	int maxAttempts = 10;
		WebElement Searchbox=driver.findElement(By.xpath("(//input[@class='searchInput'])[1]"));
		for (int i = 0; i < maxAttempts; i++) {
		    try {
		    	Thread.sleep(10000);
		  
		    	clearTextbox(Searchbox);
		    	//Searchbox.sendKeys("Manual");
		    	Thread.sleep(2000);
		        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(60));
		        

		        // Wait up to 60s for the element to be present in the DOM (not necessarily visible)
		        wait1.until(ExpectedConditions.presenceOfElementLocated(loc));
		        WebElement element=driver.findElement(loc);
		        // If present, exit the function early
		        if(element.getText().equals("InProgress") || element.getText().equals("NotStarted")) {
		        	Thread.sleep(60000);
		        	WebElement refreshButton = driver.findElement(
			                By.xpath("(//button[contains(@class,'refreshButton')])")
			            );
			            refreshButton.click();
		        	continue;
		        }
		        getPipelineStatus(driver.findElement(loc),orchestratorName);
		        //captureScreenshot();
		        return;

		    } catch (Exception e) {
		        // If not present → click Refresh and continue to next attempt
		        try {
		            WebElement refreshButton = driver.findElement(
		                By.xpath("(//button[contains(@class,'refreshButton')])")
		            );
		            refreshButton.click();
		        } catch (Exception ignored) {}
		    }
		}
		
    }public void clickProcessId() {
    	standardClickButton(firstProcessId,firstProcessId.getText());
    }public void clickProcessPipeline(String pName) {
    	String s = "//span[@title='"+pName+"']//parent::*[@class='ant-select-selection-wrap']/..";
    	WebElement d = driver.findElement(By.xpath(s));
    	standardClickButton(d,pName);
    }public void clickScheduleCloseButton() {
    	standardClickButton(closeButton,"Close");
    }public void clickDeactivatePipelineButton() {
    	standardClickButton(activePipeline,"Deactivate Pipeline");
    }public void clickActivatePipelineButton() {
    	standardClickButton(inactivePipeline,"Activate Pipeline");
    }public void clickCreateNewProcess() {
    	standardClickButton(createNewProcessButton,"Create New Process");
    }public void selectMDMFIleType(String value) {
    	dropdownElement(MDMSelectFIleType,value,"Select MDM FIle Type");
    }public void waitForPipelineVisible( String name) {
    	By loc = By.xpath("//td[text()='"+name+"']");
    	try {
    		wait.until(ExpectedConditions.presenceOfElementLocated(loc));
    	}catch(Exception e) {
    		
    	}
    }
}
