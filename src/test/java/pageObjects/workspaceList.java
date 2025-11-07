package pageObjects;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class workspaceList extends basePage{
	WebDriver driver;
	loginPage login = new loginPage(driver);
	public workspaceList(WebDriver driver) {
		super(driver);
	}@FindBy(xpath="//span[@class='ant-menu-title-content']/div[text()='All']")
	public WebElement allButton;
	@FindBy(xpath="//span[@class='ant-menu-title-content']/div[text()='MDM']")
	public WebElement mDMButton;
	@FindBy(xpath="//span[@class='ant-menu-title-content']/div[text()='Data Nexus']")
	public WebElement dataNexusButton;
	@FindBy(xpath="//span[@class='ant-menu-title-content']/div[text()='P.AI']")
	public WebElement pAIButton;
	@FindBy(xpath="//input[contains(@class,'workspace_searchInput')]")
	public WebElement searchBox;
	@FindBy(xpath="//button[contains(@class,'workspace_newProjectButton')]")
	public WebElement createNewWorkspace;
	@FindBy(xpath="(//h5[contains(@class,'ant-typography')])[1]")
	public WebElement firstWorkspace;
	@FindBy(xpath="(//h5[contains(@class,'ant-typography')])[2]")
	public WebElement secondWorkspace;
	@FindBy(xpath="//button[contains(@class,'workspace_backImage')]")
	public WebElement workspaceBackButton;
	@FindBy(xpath="(//span[contains(@class,'ant-tag') and text()='MDM'])[1]")
	public WebElement firstMDMWorkspace;
	@FindBy(xpath="(//span[contains(@class,'ant-tag') and text()='P.AI'])[1]")
	public WebElement firstPAIWorkspace;
	@FindBy(xpath="//img[contains(@class,'workspace_logoImage')]")
	public WebElement onePlatformLogoWorkspaceListPage;
	public void clickAllButton() {
		allButton.click();
	}public void clickMDMButton() {
		mDMButton.click();
	}public void clickDataNexus() {
		dataNexusButton.click();
	}public void searchBox(String name) {
		searchBox.sendKeys(name);
	}public void createNewWorkspace(String wName) {
		standardClickButton(createNewWorkspace,"Create New Workspace");
	}public void clickWorkSpace(String wName) {
		By loc =By.xpath("(//h5[contains(@class,'ant-typography') and text()='"+wName+"'])");
		WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
		standardClickButton(option,wName+"Worspace");
	}public void clickPAIButton(){
		pAIButton.click();
	}public void workspaceHeaderValidation() throws InterruptedException, IOException {
		login.isHeaderPresent("h1", "Workspaces");
	}
	
}
