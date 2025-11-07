package pageObjects;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class dataNexusHome extends basePage{
	WebDriver driver;
	public dataNexusHome (WebDriver driver) {
		super(driver);
	}@FindBy(className="searchInput")
	public WebElement searchBox;
	@FindBy(xpath="SideNav_innersearch")
	public WebElement selectWorkspaceDropdown;
	@FindBy(xpath="//div[@role='tab' and text()='All']")
	public WebElement allButton;
	@FindBy(xpath="//div[@role='tab' and text()='Active']")
	public WebElement activeButton;
	@FindBy(xpath="//div[@role='tab' and text()='Inactive']")
	public WebElement inactiveButton;
	@FindBy(xpath="//div[@role='tab' and text()='Scheduled']")
	public WebElement scheduledButton;
	@FindBy(xpath="//img[contains(@class,'SideNav_plusIcon')]")
	public WebElement addWorkspace;
	@FindBy(xpath="(//tr[@class='pipeline-active-row']/td/div)[1]")
	public WebElement firstPipeline;
	@FindBy(xpath="//div[text()='Total Pipeline']//following-sibling::div")
	public WebElement totalPipelines;
	@FindBy(xpath="//div[text()='Scheduled Pipeline']//following-sibling::span")
	public WebElement scheduledPipelines;
	@FindBy(xpath="//div[text()='Total Trigger']//following::p[text()='Success']/span")
	public WebElement totalSuccessTrigger;
	@FindBy(xpath="//div[text()='Total Trigger']//following::p[text()='Failed']/span")
	public WebElement totalFailedTrigger;
	@FindBy(xpath="(//span[text()='Created Date']//following-sibling::div)[1]")
	public WebElement createdDate;
	public void searchBox(String text) throws IOException {
		standardEnterTextbox(searchBox,text,"Searchbox");
	}public void addWorkspace() {
		addWorkspace.click();
	}public void selectWorlspace() {
		selectWorkspaceDropdown.click();
	}public void clickAll() {
		allButton.click();
	}public void clickInctive() {
		inactiveButton.click();
	}public void clickScheduled() {
		scheduledButton.click();
	}public void clickActive() {
		activeButton.click();
	}
}
