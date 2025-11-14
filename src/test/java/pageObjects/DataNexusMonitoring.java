package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Utilities.ExtentReportManager;

public class DataNexusMonitoring extends basePage{
	public DataNexusMonitoring(WebDriver driver) {
		super(driver);
	}@FindBy(xpath="(//img[contains(@class,'ParserLogic_documentIcon')])[1]")
	WebElement parserViewLogs;
	@FindBy(xpath="(//span[contains(@class,'ParserLogic_preview')])[1]")
	WebElement parserPreview;
	@FindBy(xpath="(//span[contains(@class,'ParserLogic_versionHistory')])[1]")
	WebElement parserVersionHistory;
	@FindBy(xpath="(//button[contains(@class,'ParserLogic_backButton')])[1]")
	WebElement parserBackButton;
	@FindBy(xpath="//p[text()='Form data saved successfully!']")
	public WebElement formDataSavedMsg;
	@FindBy(xpath="//p[text()='Successfully approved 1 file(s)!']")
	public WebElement parserApproveMsg;
	public String getColumnData(String cName) {
		String loc = "(//span[text()='"+cName+"']//parent::td/div)[1]";
		return driver.findElement(By.xpath(loc)).getText();
	}public void clickcolumnData(String cName) {
		String loc = "(//span[text()='"+cName+"']//parent::td/div)[1]";
		driver.findElement(By.xpath(loc)).click();
	}public void pipelineStatus(String pType) {
		try {
			WebElement e = driver.findElement(By.xpath("(//span[text()='Status']//following-sibling::span)[2]"));
			if(e.getText().equalsIgnoreCase("Success")) {
				ExtentReportManager.getTest().pass("Pipeline status is marked as Success for "+pType+" Pipeline at monitoring Page");
			}else {
				ExtentReportManager.getTest().fail("Pipeline status is marked as Failed for "+pType+" Pipeline at monitoring Page");
			}
		}catch(Exception e) {
			ExtentReportManager.getTest().fail("Validation was failed due to exception "+e.getMessage());
			 	
		}
	}public void searchBox(String index , String value) {
    	By loc = By.xpath("(//input[@class='searchInput'])["+index+"]");
    	try {
    		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(loc));
    		standardEnterTextbox(element,value,"Searchbox");
    	}catch(Exception e) {
    		
    	}
    }public void clickParserViewLogs() {
    	standardClickButton(parserViewLogs,"View Logs");
    }public void clickParserPreview() {
    	standardClickButton(parserPreview,"Preview");
    }public void clickParserVersionHistory() {
    	standardClickButton(parserVersionHistory,"Version Histroy");
    }public void clickParserBack() {
    	standardClickButton(parserBackButton,"Back Button");
    }

}
