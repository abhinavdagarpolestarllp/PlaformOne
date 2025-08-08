package pageObjects;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Utilities.ExtentReportManager;

public class DataNexusIngestionPipeline extends basePage{
	//WebDriver driver;
	loginPage login = new loginPage(driver);
	 public final String iGPipelineName = "TestingIgPipeline" + dateNTime;
	public DataNexusIngestionPipeline(WebDriver driver){
		super(driver);
	}@FindBy(xpath="//input[@id='pipeline_name']")
	public WebElement pipelineName;
	@FindBy(xpath="//input[@id='lake_house_name']")
	public WebElement lakehouseName;
	@FindBy(xpath="//input[@id='Data_Lake']")
	public WebElement datalakeName;
	@FindBy(xpath="//input[@id='source']")
	public WebElement source;
	@FindBy(xpath="//input[@id='schema']")
	public WebElement schema;
	@FindBy(xpath="//span[text()='Save Pipeline']")
	public WebElement savePipeline;
	@FindBy(xpath="//button[contains(text(),'Selected Tables')]")
	public WebElement selectedTables;
	@FindBy(xpath="//div[text()='Please enter the pipeline name!']")
	public WebElement blankPipelineMsg;
	@FindBy(xpath="(//span[text()='Pipeline Name']//following-sibling::span)[2]")
	public WebElement firstPipeline;
	@FindBy(xpath="//p[text()='Pipeline Created Successfully']")
	public WebElement pipelineCreatedMsg;
	@FindBy(xpath="//p[text()='Pipeline Updated Successfully']")
	public WebElement pipelineupdatedMsg;
	@FindBy(xpath="//p[text()='Enter Pipeline name']")
	public WebElement mandatoryPipelineNameMsg;
	@FindBy(xpath="//button[contains(@class,'closeButton')]")
	public WebElement closeButton;
	@FindBy(xpath="(//span[@class='p-column-title']//input)[1]")
	public WebElement selectAllCheckbox;
	@FindBy(xpath="//p[text()='Incremental condition and Column selection are required for preview.']")
	public WebElement mandatoryColumnSelectionMsg;
	@FindBy(xpath="//p[text()='For Incremental Load on ADDUsers, either FX Column Selector with selections or Custom Column Selector is required.']")
	public WebElement columnSelectionMsg;
	@FindBy(xpath="//p[text()='Pipeline deleted Successfully']")
	public WebElement deletePipelineMsg;
	public void selectCustomQuery(String tname) {
		try {
		String obj="(//div[text()='"+tname+"']//following::button[@role='switch'])[1]";
		driver.findElement(By.xpath(obj)).click();
		ExtentReportManager.getTest().pass(tname+" custom query switch is clicked");
		}catch(Exception e) {
			ExtentReportManager.getTest().fail(tname+" custom query switch is not clicked due to "+e.getMessage());
		}
	}public void enterCustomQuery(String tname,String cQuery) throws InterruptedException, IOException {
		String obj="(//div[text()='"+tname+"']//following::input[@placeholder='Custom Query'])[1]";
		WebElement e = driver.findElement(By.xpath(obj));
		Thread.sleep(3000);
		login.wait.until(ExpectedConditions.elementToBeClickable(e));
		e.click();
		isHeaderPresent("h3","Custom Query");
		clearTextbox(driver.findElement(By.xpath("//div[@class='view-lines monaco-mouse-cursor-text']")));
		typeTextFromKeyboard(driver.findElement(By.xpath("//div[@class='view-lines monaco-mouse-cursor-text']")),cQuery);
	}public void selectLoadType(String tname,String ltype ) {
		String obj = "(//div[text()='"+tname+"']//following::div[@class='ant-select-selector'])[1]";
		WebElement dropdown = driver.findElement(By.xpath(obj));
		String text ="(//div[text()='"+tname+"']//following::input[@role='combobox'])[1]";
		WebElement textbox = driver.findElement(By.xpath(text));
		multipleSameDropdown(dropdown,textbox,ltype,"Load Type");
	}public void enterPipelineName(String name) {
		standardEnterTextbox(pipelineName,name,"Pipeline Name");
	}public void selectLakehouseName(String name) {
		login.dropdownElement(lakehouseName, name,"Lakehouse Name");
	}public void selectDatalkeName(String name) {
		login.dropdownElement(datalakeName, name,"Datalake Name");
	}public void selectSource(String name) {
		login.dropdownElement(source, name,"Source");
	}public void selectSchema(String name) {
		login.dropdownElement(schema, name,"Schema");
	}public void savePipeline() {
		savePipeline.click();
	}public void selectLoadTypeColumn(String Tname,String Cvalue) {
		String obj ="(//div[text()='"+Tname+"']//following::input[@type='search'])[2]";
		try {
		WebElement e=login.wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(obj))));
		login.dropdownElement(e, Cvalue, "Select Column");
		}catch(Exception e) {
			ExtentReportManager.getTest().fail(Cvalue+" is not selected from selct column dropdown due to "+e.getMessage());
		}
	}public void selectLoadTypeCondition(String Tname,String Cvalue) {
		String obj ="(//div[text()='"+Tname+"']//following::input[@type='search'])[3]";
		try {
		WebElement e=login.wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(obj))));
		login.dropdownElement(e, Cvalue, "Select Condition");
		}catch(Exception e) {
			ExtentReportManager.getTest().fail(Cvalue+" is not selected from selct condition dropdown due to "+e.getMessage());
		}
	}public void selectLoadTypeIncValue(String Tname,String Cvalue) {
		String obj ="(//div[text()='"+Tname+"']//following::input[@type='text'])[1]";
		try {
		WebElement e=login.wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(obj))));
		login.standardEnterTextbox(e, Cvalue, "Select Value");
		}catch(Exception e) {
			ExtentReportManager.getTest().fail(Cvalue+" is not selected from selct value dropdown due to "+e.getMessage());
		}
	}public void columnSelector(String tname) throws InterruptedException, IOException {
		String xpath="(//div[text()='"+tname+"']//following::div[contains(@class,'AddNewIngestionPipeline_columnSelectorContainer')])[1]";
		driver.findElement(By.xpath(xpath)).click();
		waitForPageLoad(driver.findElement(By.xpath("//h3[text()='Column Selector']")));
		isHeaderPresent("h3","Column Selector");
	}public void clickViewQueryBody(String tname) throws InterruptedException, IOException {
		String xpath="(//div[text()='"+tname+"']//following::div[contains(@class,'AddNewIngestionPipeline_previewQueryBody')])[1]";
		driver.findElement(By.xpath(xpath)).click();
	}public void clickCloseButton() {
		standardClickButton(closeButton,"Close");
	}public void duplicatePipelineMsg(String pName) {
		String locator ="//p[text()='Ingestion with name "+pName+" already exists']";
		isObjectExist(driver.findElement(By.xpath(locator)));
	}
}
