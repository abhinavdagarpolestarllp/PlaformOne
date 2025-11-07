package pageObjects;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Utilities.ExtentReportManager;
import testBase.baseClass;


public class loginPage extends basePage{
	//public WebDriver driver;
	public loginPage(WebDriver driver) {
		super(driver);
	}
	@FindBy(xpath="(//span[text()='Maxlength is 100'])[2]")
	public WebElement maxlength;
	@FindBy(id = "username")
	public WebElement username_textbox;
	@FindBy(xpath="//button[contains(@class,'icon-close')]")
	WebElement messageClose;
	@FindBy(id="password")
	public WebElement password_textbox;
	@FindBy(xpath="//button[text()='LOGIN']")
	WebElement loginButton;
	@FindBy(xpath="//p[text()='Please Check the Fields!']")
	public WebElement blankField_Message;
	@FindBy(xpath="(//span[text()='This is required'])[2]")
	public WebElement fieldRequire_Message;
	@FindBy(xpath="//p[text()='This user doesn't exists']")
	public WebElement userNotExist_Message;
	@FindBy(xpath="//p[text()='Invalid credentials']")
	public WebElement invalidCredentials_Message;
	@FindBy(className="toast-message")
	WebElement loginMsg;
	@FindBy(xpath="(//span[text()='Cancel'])[1]")
	WebElement cancelButton;
	@FindBy(xpath="(//span[text()='Ok'])[1]")
	WebElement okButton;
	@FindBy(xpath="(//span[@aria-label='edit'])[1]")
	WebElement editButton;
	@FindBy(xpath="(//img[@alt='Trigger Icon'])[1]")
	WebElement triggerButton;
	@FindBy(xpath="(//span[@class='anticon anticon-delete'])[1]")
	WebElement deleteButton;
	@FindBy(className="addButonCss")
	WebElement createNewButton;
	@FindBy(xpath="//div[@class='popup-close']")
	WebElement closeButton1;
	@FindBy(xpath="(//button[@class='ant-modal-close'])[1]")
	WebElement closeButton2;
	@FindBy(xpath="//button[@class='ant-modal-close']")
	WebElement closeButton3;
	@FindBy(xpath="//span[text()='Next']")
	WebElement nextButton;
	@FindBy(xpath="//span[text()='Reset']")
	WebElement resetButton;
	@FindBy(xpath="//span[text()='Test Connection']")
	WebElement testConnectionButton;
	@FindBy(xpath="//div[contains(@class,'workspace_profileDivDownIcon')]")
	WebElement profileDropdown;
	@FindBy(xpath="//span[text()='Profile']")
	public WebElement profileProfile;
	@FindBy(xpath="//span[text()='User Management']")
	public WebElement profileUserManagement;
	@FindBy(xpath="//input[@class='searchInput']")
	public WebElement searchbox;
	@FindBy(xpath="//button[@type='submit']")
	public WebElement submitButton;
	@FindBy(xpath="//img[@alt='View Details Icon']")
	public WebElement logsIcon;
	@FindBy(xpath="//p[text()='Logged Out Successfully!']")
	public WebElement logoutUserMsg;
	@FindBy(xpath="//button[text()='No']")
	public WebElement noButton;
	@FindBy(xpath="//button[text()='Yes']")
	public WebElement yesButton;
	@FindBy(xpath="//button[text()='Save']")
	public WebElement saveButton;
	@FindBy(xpath="//img[contains(@src,'back')]")
	public WebElement backButton;
	@FindBy(xpath="(//span[@class='anticon anticon-eye'])[1]")
	public WebElement viewButton;
	@FindBy(xpath="//div[contains(@class,'ProcessLogs_statusContainer')]")
	public WebElement statusElement;
	@FindBy(xpath="(//img[@alt='Schedule Icon'])[1]")
	public WebElement scheduleButton;
	@FindBy(xpath="//input[@id='triggerDate']")
	public WebElement triggerDate;
	@FindBy(xpath="//input[@id='triggerTime']")
	public WebElement triggerTime;
	@FindBy(xpath="//div[contains(@class,'process_scheduledBox')]")
	public WebElement viewSchedule;
	@FindBy(xpath="//p[text()='Schedule updated successfully']")
	public WebElement scheduleUpdatedMsg;
	@FindBy(xpath="(//*[contains(text(),'Are you sure you want to trigger')])[1]")
	public WebElement triggerConfirmation;
	@FindBy(xpath="(//*[contains(text(),'Are you sure you want to delete')])[1]")
	public WebElement deleteConfirmation;
	@FindBy(xpath="//img[contains(@class,'SideNav_sideNavCollapse')]")
	WebElement SideNavcollapse;
	@FindBy(xpath="//input[@aria-label='Select all' and @type='checkbox']")
	WebElement selectAllCheckbox;
	@FindBy(xpath="//span[@aria-label='download']")
	WebElement download;
	public void clickSubmitButton() {
		standardClickButton(submitButton,"Submit");
	}public void clickSaveButton() {
		standardClickButton(saveButton,"Save");
	}public void clickViewButton() {
		standardClickButton(viewButton,"View");
	}
	public void enterInSearchbox(String data) throws IOException {
		standardEnterTextbox(searchbox,data,"Searchbox");
	}
	public void testConnection() {
		testConnectionButton.click();
	}public void clickClose() {
	    boolean clicked = false;

	    try {
	    	closeButton1.click();
	        ExtentReportManager.getTest().pass("✅ Clicked on **Close button** successfully.");
	        clicked = true;
	    } catch (Exception ignored1) {}

	    if (!clicked) {
	        try {
	        	closeButton2.click();
	            ExtentReportManager.getTest().pass("✅ Clicked on **Close button** successfully.");
	            clicked = true;
	        } catch (Exception ignored2) {}
	    }

	    if (!clicked) {
	        try {
	        	closeButton3.click();
	            ExtentReportManager.getTest().pass("✅ Clicked on **Close button** successfully.");
	            clicked = true;
	        } catch (Exception e3) {
	            ExtentReportManager.getTest().fail("❌ Failed to click on **Close button**. Error: " + e3.getMessage());
	        }
	    }
	}public void clickYesButton() {
		standardClickButton(yesButton,"Yes");
	}public void clickNoButton() {
		standardClickButton(noButton,"No");
	}
	public void clickCancel() {
		standardClickButton(cancelButton,"Cancel");
	}public void clickOk() {
		standardClickButton(okButton,"Ok");
	}public void clickEdit() {
		standardClickButton(editButton,"Edit");
	}public void clickNext() {
		standardClickButton(nextButton,"Next");
	}public void clickReset() {
		standardClickButton(resetButton,"Reset");
	}public void clickTrigger() {
		standardClickButton(triggerButton,"Trigger");
	}public void clickDelete() {
		standardClickButton(deleteButton,"Delete");
	}public void clickCreateNew() {
		standardClickButton(createNewButton,"Create New");
	}
	public void enterUsername(String username) throws IOException {
		standardEnterTextbox(username_textbox,username,"Username");
	}
	public void enterPassword(String password) throws IOException {
		standardEnterTextbox(password_textbox,password,"Password");
	}public void clickLogin() {
		standardClickButton(loginButton,"Login");
	}public String getLoginMessage() {
		try {
			return (loginMsg.getText());
		}catch(Exception e) {
			return e.getMessage();
		}
	}public void messageClose() {
		standardClickButton(messageClose,"Message Close");
		//ExtentReportManager.getTest().pass("Close messagebox button is clicked");
	}
	public void clickProfileDropdown() {
		standardClickButton(profileDropdown,"Profile Dropdown");
		profileDropdown.click();
	}public void clickBackButton() {
		standardClickButton(backButton,"Back Button");
	}public void clickViewLogsButton() {
		standardClickButton(logsIcon,"View Logs");
	}public void getPipelineStatus() {
		try {
		String status = statusElement.getText();
		if(status.equalsIgnoreCase("Success")) {
			String screenshotPath = baseClass.captureScreen(driver, "Pipline Success Icon");
	        ExtentReportManager.getTest()
	            .pass("** Pipeline success icon is present**")
	            .addScreenCaptureFromPath(screenshotPath);
		}else {
			ExtentReportManager.getTest().fail("Pipeline failed icon is present");
		}
		}catch(Exception e) {
			ExtentReportManager.getTest().fail("Validation falied due to "+e.getMessage());
		}
		
	}public void clickScheduleButton() {
		standardClickButton(scheduleButton,"Schedule");
	}public void setTriggerDate() {
		typeTextFromKeyboard(triggerDate,todayDate);
	}public void setTriggerTime(int min) {
		typeTextFromKeyboard(triggerTime,calCurrentTime(min));
	}public void clickViewSchedule() {
		standardClickButton(viewSchedule,"View Schedule");
	}public void clickSideNavCollapse() {
		standardClickButton(SideNavcollapse,"Collapse");
	}public void startingClose() throws InterruptedException {
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[text()='Continue to site']")).click();
	}public void clickSelectAllCheckbox() {
		standardClickButton(selectAllCheckbox,"Select All Checkbox");
	}public void changeEnv(String env) {
		By loc = By.xpath("//label[@for='environment-dropdown']//following::div[1]");
		By loc1 =By.xpath("//div[@class='ant-select-item-option-content']//span[text()='"+env+"']");
		try {
			driver.findElement(loc).click();
			WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(loc1));
			option.click();
			ExtentReportManager.getTest().pass(
	                "'" + env + "' is selected from top navigation environment dropdown element");
		}catch(Exception e) {
			ExtentReportManager.getTest().pass(
	                "'" + env + "' was not selected from top navigation environment dropdown element due to "+e.getMessage());
		}
	}public void clickDownloadButton() {
		standardClickButton(download,"Download");
	}
	
}
