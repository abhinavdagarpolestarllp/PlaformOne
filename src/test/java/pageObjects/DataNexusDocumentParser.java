package pageObjects;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DataNexusDocumentParser extends basePage{
	public String pname = "TestingProcess"+ dateNTime;
	public DataNexusDocumentParser(WebDriver driver) {
		super(driver);
		
	}@FindBy(xpath="//button[contains(@class,'document-parser_addButton')]")
	WebElement addProcess;
	@FindBy(xpath="//input[@id='process_name']")
	public WebElement processName;
	@FindBy(xpath="//input[@id='lakehouse']//ancestor::div[1]")
	WebElement lakehouse;
	@FindBy(xpath="//input[@id='source_path']//ancestor::div[1]")
	WebElement definePath;
	@FindBy(xpath="//input[@id='target_path']//ancestor::div[1]")
	WebElement targetPath;
	@FindBy(xpath="//div[contains(@class,'searchContainer')]/input")
	WebElement searchBox;
	@FindBy(xpath="//span[text()='Process updated successfully!']")
	public WebElement updateProcessMsg;
	@FindBy(xpath="//span[text()='Process saved successfully!']")
	public WebElement createProcessMsg;
	@FindBy(xpath="//span[text()='Process Deleted Successfully']")
	public WebElement deleteProcessMsg;
	@FindBy(xpath="//span[text()='Failed to save process. Please check all required fields.']")
	public WebElement mandatoryFieldsMessage;
	@FindBy(xpath="//div[text()='Please select lakehouse']")
	public WebElement mandatoryLakehouseMessage ;
	@FindBy(xpath="//div[text()='Please select source path']")
	public WebElement mandatorySourceMessage;
	@FindBy(xpath="//div[text()='Please select target path']")
	public WebElement mandatoryTargetMessage;
	@FindBy(xpath="//span[text()='Failed to save process. Please try again.']")
	public WebElement duplicateNameMessage;
	@FindBy(xpath="//span[text()='Extraction Triggered Successfully']")
	public WebElement processTriggerMsg;
	public void clickAddProcess() {
		standardClickButton(addProcess,"Add Process");
	}public void enterProcessName(String name) throws IOException {
		standardEnterTextbox(processName,name,"Process Name");
	}public void chooseLakehouse(String value) {
		dropdownElement(lakehouse,value,"Choose Lakehouse");
	}public void choosedefinePath(String value) {
		dropdownElement(definePath,value,"Define Path");
	}public void chooseTargetPath(String value) {
		dropdownElement(targetPath,value,"Target Path");
	}public void enterInSearchbox(String name) throws IOException {
		standardEnterTextbox(searchBox,name,"Searchbox");
	}public void waitForDocumentProcessExecution(String pName) {
		By loc =By.xpath("//span[text()='Status']//parent::span//following-sibling::span[contains(@class,'ParserLogic')]");
		int maxAttempts = 15;
		WebElement Searchbox=driver.findElement(By.xpath("(//input[@class='searchInput'])[2]"));
		for (int i = 0; i < maxAttempts; i++) {
		    try {
		    	Thread.sleep(4000);
		    	clearTextbox(Searchbox);
		    	Searchbox.sendKeys(pName);
		        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(60));
		        

		        // Wait up to 60s for the element to be present in the DOM (not necessarily visible)
		        wait1.until(ExpectedConditions.presenceOfElementLocated(loc));
		        WebElement element=driver.findElement(loc);
		        // If present, exit the function early
		        if(element.getText().equals("In Progress") || element.getText().equals("NotStarted") || element.getText().equals("Partial")) {
		        	Thread.sleep(60000);
		        	continue;
		        }
		        getPipelineStatus(driver.findElement(loc),pName);
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
		}
	}
	
}
