package pageObjects;

import java.io.IOException;

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
	public void clickFiles() throws IOException {
		standardClickButton(filesColumn,"Files");
	}public void clickTable() throws IOException {
		standardClickButton(tablesColumn,"Tables");
	}public void addRootDirectory() throws IOException {
		standardClickButton(addRootDirectory,"Add Root Directory");
	}public void driectoryName(String s) throws IOException {
		standardEnterTextbox(directoryNameTextbox,s,"Driectory Name");
	}
	
}
