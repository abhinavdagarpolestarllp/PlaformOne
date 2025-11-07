package pageObjects;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DataNexusDocumentParser extends basePage{
	public String pname = "TestingProcess"+ dateNTime;
	public DataNexusDocumentParser(WebDriver driver) {
		super(driver);
		
	}@FindBy(xpath="//button[contains(@class,'document-parser_addButton')]")
	WebElement addProcess;
	@FindBy(xpath="//input[@id='process_name']")
	WebElement processName;
	@FindBy(xpath="//input[@id='lakehouse']//ancestor::div[1]")
	WebElement lakehouse;
	@FindBy(xpath="//input[@id='source_path']//ancestor::div[1]")
	WebElement definePath;
	@FindBy(xpath="//input[@id='target_path']//ancestor::div[1]")
	WebElement targetPath;
	@FindBy(xpath="//div[contains(@class,'searchContainer')]/input")
	WebElement searchBox;
	@FindBy(xpath="//span[text()='Process updated successfully!']")
	public WebElement updateProcessMsg;
	@FindBy(xpath="//span[text()='Process saved successfully!']")
	public WebElement createProcessMsg;
	@FindBy(xpath="//span[text()='Process Deleted Successfully']")
	public WebElement deleteProcessMsg;
	public void clickAddProcess() {
		standardClickButton(addProcess,"Add Process");
	}public void enterProcessName(String name) throws IOException {
		standardEnterTextbox(processName,name,"Process Name");
	}public void chooseLakehouse(String value) {
		dropdownElement(lakehouse,value,"Choose Lakehouse");
	}public void choosedefinePath(String value) {
		dropdownElement(definePath,value,"Define Path");
	}public void chooseTargetPath(String value) {
		dropdownElement(targetPath,value,"Target Path");
	}public void enterInSearchbox(String name) throws IOException {
		standardEnterTextbox(searchBox,name,"Searchbox");
	}
	
}
