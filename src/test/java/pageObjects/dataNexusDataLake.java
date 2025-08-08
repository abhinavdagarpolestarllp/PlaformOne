package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class dataNexusDataLake extends basePage{
	WebDriver driver;
	public dataNexusDataLake(WebDriver driver){
		super(driver);
	}@FindBy(xpath="//span[@class='ant-select-selection-item']")
	public WebElement lakeHouseSelectDropdown;
	@FindBy(xpath="//div[contains(@class,'SideNav_moduleNames')]//span[text()='Data Lake']")
	//@FindBy(xpath="//span[text()='Data Lake']")
	public WebElement dataLake;
	@FindBy(xpath="//button[contains(@class,'datalake_tabButton') and text()='Files']")
	public WebElement filesColumn;
	@FindBy(xpath="//button[contains(@class,'datalake_tabButton') and text()='Tables']")
	public WebElement tablesColumn;
	@FindBy(xpath="//span[@class='anticon anticon-folder-add']")
	public WebElement addRootDirectory;
	@FindBy(xpath="(//span[@class='ant-tree-switcher ant-tree-switcher_close'])[1]")
	public WebElement treeSwitch;
	@FindBy(xpath="//input[@placeholder='Enter directory name']")
	public WebElement directoryNameTextbox;
	public void clickFiles() {
		filesColumn.click();
	}public void clickTable() {
		tablesColumn.click();
	}public void addRootDirectory() {
		addRootDirectory.click();
	}public void clickDataLake() {
		dataLake.click();
	}public void driectoryName(String s) {
		directoryNameTextbox.sendKeys(s);
	}
	
}
