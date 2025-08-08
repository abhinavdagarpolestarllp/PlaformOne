package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MDMOverview extends basePage{
	//WebDriver driver;
	public MDMOverview(WebDriver driver) {
		super(driver);
		
	}@FindBy(xpath="(//div[text()='Potential Duplicate %']//following::div[text()='View Detail'])[1]")
	public WebElement potentialDuplicateViewDetail;
	@FindBy(xpath="(//div[text()='Incomplete Records %']//following::div[text()='View Detail'])[1]")
	public WebElement incompleteRecordsViewDetail;
	@FindBy(xpath="(//img[@alt='View Table'])[1]")
	public WebElement viewTable;
	public WebElement mDMHomeRecordIcon(String s) {
		String obj="//div[text()='"+s+"']//parent::div[contains(@class,'dashboard_DQCard_')]";
		return driver.findElement(By.xpath(obj));
	}public void clickincompleteRecordsViewDetail(){
		standardClickButton(incompleteRecordsViewDetail, "Incomplete Records View Button");
	}public void clickpotentialDuplicateViewDetail(){
		standardClickButton(potentialDuplicateViewDetail, "Potential Duplicate View Button");
	}public void clickViewTableButton() {
		standardClickButton(viewTable,"View Table");
	}

}
