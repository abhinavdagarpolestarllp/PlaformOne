package pageObjects;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;

import Utilities.ExtentReportManager;
import testBase.baseClass;

public class MigrationCICD extends basePage{
	WebDriver driver;
	public MigrationCICD (WebDriver driver) {
		super(driver);
	}@FindBy(xpath="//p[text()='Pipeline movement is in progress']")
	public WebElement pipelineMovementMsg;
	@FindBy(xpath="(//span[contains(@class,'DeploymentHistoryPopup_transactionIdLink')])[1]")
	WebElement transactionId;
	@FindBy(xpath="//input[contains(@class,'searchInput DeploymentHistoryPopup')]")
	WebElement deployementHistorySearchbox;
	@FindBy(xpath="(//span[contains(@class,'DeploymentHistoryPopup_statusTag')])[1]")
	WebElement deployStatus;
	@FindBy(xpath="//input[contains(@class,'searchInput MigrationCICD')]")
	WebElement searchbox;
	public void clickTransactionId() {
		standardClickButton(transactionId,transactionId.getText()+" Transaction id");
	}public void getDeployedPipelineStatus(String pipelineName) throws IOException, InterruptedException {
		int attempt=0;
		try {
		while(attempt<6) {
		standardEnterTextbox(deployementHistorySearchbox,pipelineName,"Searchbox");
		wait.until(ExpectedConditions.visibilityOf(deployStatus));
    	if(deployStatus.getText().equalsIgnoreCase("Success")) {
    		ExtentReportManager.getTest().pass("Pipeline "+pipelineName+" pipeline is succesfully deployed");
    		captureScreenshot();
    		break;
    	}else if(deployStatus.getText().equalsIgnoreCase("IN_PROGRESS")) {
    		Thread.sleep(10000);
    		clearTextbox(deployementHistorySearchbox);
    		clickSpanText("Back to Transactions");
    		clickTransactionId();
    		attempt++;
    		continue;
    	}else if(attempt==5) {
    		ExtentReportManager.getTest().fail("Pipeline "+pipelineName+" deployment is failed");
    		captureScreenshot();
    		break;
    	}
    	else {
    		ExtentReportManager.getTest().fail("Pipeline "+pipelineName+" deployment is failed");
    		captureScreenshot();
    		break;
    		//throw new SkipException("Skipping test because " + pipelineName + " deployment failed");
    	}
		}
    	
		}catch(Exception e) {
			ExtentReportManager.getTest().fail("Pipeline "+pipelineName+" deployment is failed due to "+e.getMessage());
		}
	}public void selectMigrationPipelineType(String name) {
		By loc =By.xpath("//div[@data-node-key='"+name+"']");
		WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
		standardClickButton(option,name+" migration pipeline type");
	}public void enterInSearchbox(String value) throws IOException {
		standardEnterTextbox(searchbox,value,"Searchbox");
	}

}
