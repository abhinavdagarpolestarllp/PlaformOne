package pageObjects;



import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DataNexusTransformationPipeline extends basePage{
	loginPage login = new loginPage(driver);
	public DataNexusTransformationPipeline(WebDriver driver){
		super(driver);
	}@FindBy(xpath="//textarea[@id='description']")
	public WebElement profileDescription;
	@FindBy(xpath="//span[text()='Add Source']")
	public WebElement addSource;
	@FindBy(xpath="//input[@placeholder='Multi Source Pipeline']")
	public WebElement pipelineName;
	@FindBy(xpath="//span[@class='ant-select-selection-placeholder'and text()='Choose Lake House']")
	public WebElement dataLakeSelectLakeHouse;
	@FindBy(xpath="//span[@class='ant-select-selection-placeholder'and text()='Choose LakeHouse Type']")
	public WebElement dataLakeSelectLakeHouseType;
	@FindBy(xpath="//button[@aria-label='Close']")
	public WebElement selectDataLakeCloseIcon;
	public void clickSourceTable(String cname) {
		List<WebElement> lis = driver.findElements(By.xpath("//img[@alt='Source Table']//parent::span//following-sibling::span[1]"));
		for(WebElement e:lis) {
			if(e.getText()==cname) {
				e.click();
			}
		}
	}public String tPipeplineName="TestingTPipeline"+dateNTime;
	public void selectLakeHouse(String lakehouse) {
		login.dropdownElement(dataLakeSelectLakeHouse, lakehouse,"LakeHouse");
	}public void selectLakeHouseType(String lhtype) {
		login.dropdownElement(dataLakeSelectLakeHouseType, lhtype,"Lakehouse Type");
	}
	
}
