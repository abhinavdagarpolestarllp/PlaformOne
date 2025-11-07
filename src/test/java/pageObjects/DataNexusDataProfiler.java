package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DataNexusDataProfiler extends basePage{
	public DataNexusDataProfiler(WebDriver driver) {
		super(driver);
	}loginPage login = new loginPage(driver);
	@FindBy(xpath="//div[text()='Data Lake House']//following-sibling::div")
	public WebElement dataLakeHouse;
	@FindBy(xpath="//div[text()='Select Table']//following-sibling::div")
	public WebElement selectTable;
	@FindBy(xpath="//p[text()='Profiler data fetched successfully']")
	public WebElement profileDataFecthedMessage;
	@FindBy(xpath="//p[text()='Lakehouse fetched successfully']")
	public WebElement lakehouseDataFecthedMessage;
}
