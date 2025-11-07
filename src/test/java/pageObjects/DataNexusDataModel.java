package pageObjects;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DataNexusDataModel extends basePage{
	//WebDriver driver;
	public String modelName="TestingModel"+dateNTime;
	public DataNexusDataModel(WebDriver driver) {
		super(driver);
	}@FindBy(xpath="//input[@id='schemaName']")
	public WebElement dataModelName;
	@FindBy(xpath="//input[@placeholder='Enter schema description']")
	public WebElement dataModelDescription;
	@FindBy(xpath="//button[text()='Add Data Model']")
	public WebElement addDataModel;
	@FindBy(xpath="//p[text()='Schema saved successfully!']")
	public WebElement createdDataModelMsg;
	@FindBy(xpath="//p[contains(text(),'already exists')]")
	public WebElement duplicateDataModelMsg;
	@FindBy(xpath="//p[text()='Schema Updated successfully!']")
	public WebElement updatedDataModelMsg;
	@FindBy(xpath="//button[@class='addButonCss']")
	public WebElement addTablesButton;
	@FindBy(xpath="//input[@name='masterName']")
	public WebElement tableDisplayName;
	@FindBy(xpath="//input[@name='customer']")
	public WebElement tableLogicname;
	@FindBy(xpath="//input[@name='description']")
	public WebElement tableDescription;
	@FindBy(xpath="//input[@placeholder='Enter column name']")
	public WebElement columnName;
	@FindBy(xpath="//span[@class='ant-select-selection-search']")
	public WebElement dataType;
	@FindBy(xpath="/input[@placeholder='Enter Default Value']")
	public WebElement defaultValue;
	@FindBy(xpath="(//input[@class='ant-checkbox-input'])[2]")
	public WebElement primaryKeyCheckbox;
	@FindBy(xpath="(//input[@class='ant-checkbox-input'])[1]")
	public WebElement columnSelectCheckbox;
	@FindBy(xpath="//p[text()='Display Name is required!']")
	public WebElement mandatoryDisplayNameMsg;
	@FindBy(xpath="//p[text()='Column name is required for each row!']")
	public WebElement mandatoryColumnNameMsg;
	@FindBy(xpath="//p[text()='Data type is required for each row!']")
	public WebElement mandatoryDataTypeMsg;
	@FindBy(xpath="//p[text()='Select column to take action!']")
	public WebElement mandatorySelectColumnMsg;
	@FindBy(xpath="(//span[text()='Select Group']//ancestor::span)[1]")
	public WebElement addToSelectGroup;
	@FindBy(xpath="//p[text()='Table Created successfully.']")
	public WebElement tableCreatedMsg;
	@FindBy(xpath="//p[text()='Table updated successfully!']")
	public WebElement tableUpdatedMsg;
	@FindBy(xpath="//p[contains(text(),'successfully deleted')]")
	public WebElement tableDeleteMsg;
	@FindBy(xpath="//span[@aria-label='close']")
	public WebElement addToGroupCloseIcon;
	@FindBy(xpath="//div[text()='Add Column']//following::span[@aria-label='down'][1]")
	public WebElement addColumn;
	@FindBy(xpath="(//tr[@role='row'][1]//span[@class='p-column-title' and text()='Created On']//following-sibling::span)[2]")
	public WebElement fristCreatedDate;
	@FindBy(xpath="//span[text()='File uploaded and processed successfully!']")
	public WebElement fileUploadedMsg;
	public void clickAddDataModelButton() throws IOException {
		standardClickButton(addDataModel,"Add Data Model");
	}public void enterDataModelName(String name) throws IOException {
		standardEnterTextbox(dataModelName,name,"Model Name");
	}public void enterDataModelDescription(String desc) throws IOException {
		standardEnterTextbox(dataModelDescription,desc,"Data Model Description");
	}public void clickAddTablesButton() throws IOException {
		standardClickButton(addTablesButton,"Add Tables");
	}public void clickDeleteButton(String index) throws IOException {
		String obj = "(//th[text()='Delete']//following::span[contains(@class,'anticon anticon-delete')])["+index+"]";
		By locator = By.xpath(obj);

        // Wait for the element to be present in the DOM
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        standardClickButton(driver.findElement(locator),"Delete");
	}public void enterDisplayName(String name) throws IOException {
		standardEnterTextbox(tableDisplayName,name,"Display Name");
	}public void enterTableDescription(String name) throws IOException {
		standardEnterTextbox(tableDescription,name,"Table Description");
	}public void enterColumnName(String name) throws IOException {
		standardEnterTextbox(columnName,name,"Column Name");
	}public void selectDataType(String name) {
		dropdownElement(dataType,name,"Data Type");
	}public void addGroupSelectGroup(String name) {
		dropdownElement(addToSelectGroup,name,"Add to group");
	}public void clickAddToGroupCloseIcon() throws IOException {
		standardClickButton(addToGroupCloseIcon,"Close");
	}public void clickAddColumnButton() throws IOException {
		standardClickButton(addColumn,"Add Column");
	}public void tableAddColumnValue(String value) throws IOException {
		String xpath ="//span[@class='dropdown-option-label' and text()='"+value+"']";
		standardClickButton(driver.findElement(By.xpath(xpath)),value);
	}
}
