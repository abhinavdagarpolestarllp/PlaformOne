package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Utilities.ExtentReportManager;

public class DataNexusMonitoring extends basePage{
	public DataNexusMonitoring(WebDriver driver) {
		super(driver);
	}public String getColumnData(String cName) {
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
	}

}
