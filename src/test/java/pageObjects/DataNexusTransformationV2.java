package pageObjects;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.ExtentReportManager;

public class DataNexusTransformationV2 extends basePage{
	//WebDriver driver;
	
	
	public DataNexusTransformationV2(WebDriver driver) {
		super(driver);
	}loginPage login = new loginPage(driver);
	public String TransformationPipelineV2Name = "TestingTransformPipeline" + dateNTime;
	@FindBy(xpath="//div[@title='Open transformations panel']")
	WebElement openTrasformationPannel;
	@FindBy(xpath="//div[contains(text(),'Connect to Delta Lake tables')]//preceding-sibling::div")
	WebElement selectSourceDataLake;
	@FindBy(xpath="//div[contains(@class,'sourceOptionTitle') and text()='Custom Script']")
	WebElement selectSourceCustomScript;
	@FindBy(xpath="//div[contains(text(),'Write data to Delta Lake')]//preceding-sibling::div")
	WebElement addTargetDataLake;
	@FindBy(xpath="//div[contains(@class,'PipelineCanvas_canvasNode')]")
	public WebElement pipelineNode;
	@FindBy(xpath="//input[@placeholder='Search Pipelines']")
	WebElement searchbox;
	@FindBy(xpath="//span[@class='anticon anticon-close ant-modal-close-icon']")
	WebElement closeButton;
	@FindBy(xpath="//div[contains(@class,'SourceSelectionModal_optionTitle')]")
	WebElement sourceSelectionModel;
	@FindBy(xpath="//input[@id='create_pipeline_form_name']")
	WebElement pipelineName;
	@FindBy(xpath="//textarea[@id='create_pipeline_form_description']")
	WebElement pipelineDescription;
	@FindBy(xpath="//span[text()='Pipeline saved to draft successfully']")
	public WebElement pipelinesaved;
	@FindBy(xpath="//button[contains(@class,'controls-zoomout')]")
	WebElement zoomOut;
	@FindBy(xpath="//span[text()='Notebook created successfully']")
	public WebElement deployMessage;
	@FindBy(xpath="(//h2[text()='Target Configuration: Target_Operation']//following::*[@aria-label='close'])[1]")
	WebElement targetCloseButton;
	@FindBy(xpath="(//span[@aria-label='play-circle'])[1]")
	WebElement trigger;
	@FindBy(xpath="//textarea[contains(@placeholder,'Enter commit message')]")
	WebElement commitMessage;
	@FindBy(xpath="//textarea[contains(@placeholder,'Enter description of the changes')]")
	WebElement changeSummaryMessage;
	@FindBy(xpath="//button[contains(@class,'ChangeSummaryModal_confirmButton')]")
	WebElement commitButton;
	@FindBy(xpath="//span[text()='Pipeline loaded successfully for editing']")
	public WebElement pipelineEditMsg;
	@FindBy(xpath="//span[text()='Pipeline copy created successfully']")
	public WebElement pipelineCopyCreatedMsg;
	@FindBy(xpath="//span[text()='Pipeline committed successfully']")
	public WebElement pipelineCommitedMsg;
	@FindBy(xpath="//span[text()='Notebook updated successfully']")
	public WebElement piplineUpdatedMsg;
	@FindBy(xpath="//span[text()='Notebook triggered successfully']")
	public WebElement piplineTriggeredMsg;
	@FindBy(xpath="//p[text()='Transformation deleted successfully']")
	public WebElement piplineDeletedMsg;
	@FindBy(xpath="//div[contains(@class,'transformationSearch_searchContainer')]//input")
	WebElement tranfomrationInputSearchbox;
	@FindBy(xpath="//input[@placeholder='Enter value']")
	WebElement filterConditionValueTextbox;
	@FindBy(xpath="//span[text()='Filter configuration saved successfully!']")
	public WebElement filterAppliedMsg;
	@FindBy(xpath="//span[text()='Cast Data Type configuration saved successfully!']")
	public WebElement castDataConfigAppliedMsg;
	@FindBy(xpath="//span[text()='Remove duplicate rows based on all or selected columns']")
	public WebElement removeDuplicateMsg;
	@FindBy(xpath="//span[text()='Pipeline copy created successfully']")
	public WebElement copeCreatedMsg;
	@FindBy(xpath="//span[text()='Join configuration saved successfully!']")
	public WebElement saveJoinConfigMsg;
	@FindBy(xpath="//span[text()='Trim configuration saved successfully!']")
	public WebElement saveTrimConfigMsg;
	@FindBy(xpath="//textarea[contains(@placeholder,'Enter rejection message')]")
	WebElement dataQualityRejectionRemark;
	@FindBy(xpath="//span[text()='Data Quality Rules configuration saved successfully!']")
	public WebElement dataQualityConfigSaveMsg;
	@FindBy(xpath="//span[text()='Cast Data Type configuration saved successfully!']")
	public WebElement dataTypeConfigSaveMsg;
	@FindBy(xpath="//span[text()='Drop columns configuration saved successfully!']")
	public WebElement dropCOlumnConfigSaveMsg;
	@FindBy(xpath="(//input[contains(@placeholder,'Search columns')])[1]")
	WebElement castDatatypeSearchColumn;
	@FindBy(xpath="//span[@class='anticon anticon-history']")
	WebElement versionHistory;
	@FindBy(xpath="//span[text()='Hover to view']")
	public WebElement hoverToView;
	@FindBy(xpath="//div[text()='Please input the pipeline name!']")
	public WebElement mandatoryPipelineName;
	@FindBy(xpath="//span[contains(text(),'Duplicate column names detected')]")
	public WebElement joinDuplicateColumnMsg;
	public void openTransformationPannel() {
		standardClickButton(openTrasformationPannel,"Open Tranformation Pannel");
	}public void selectSourceDataLake() {
		standardClickButton(selectSourceDataLake,"Select Source -> Data Lake");
	}public void selectSourceCustomScript() {
		standardClickButton(selectSourceCustomScript,"Select Source -> Custom Script");
	}public void addTargetDataLake() {
		standardClickButton(addTargetDataLake,"Add Target -> Data Lake");
	}public void searchbox(String name) throws IOException {
		standardEnterTextbox(searchbox,name,"Searchbox");
	}public void clickCloseButton() {
		standardClickButton(closeButton,"Close Button");
	}public void clicksourceSelectionModel() {
		standardClickButton(sourceSelectionModel,"Source Selection Model -> Delta Lake / Lakehouse");
	}public void enterPipelineName(String name) throws IOException {
		standardEnterTextbox(pipelineName,name,"Pipeline Name");
	}public void enterPipelineDescription(String name) throws IOException {
		standardEnterTextbox(pipelineDescription,name,"Pipeline Description");
	}public void connectSourceTarget(String sourceName,String targetName) {
		try
		{
		By source = By.xpath("//span[text()='"+sourceName+"']//following::div[contains(@class,'source connectable')]");
		By target = By.xpath("(//span[text()='"+targetName+"']//preceding::div[contains(@class,'target connectable')])[count(//div[contains(@class,'target connectable')])]");
		WebElement option = dropdownWait.until(ExpectedConditions.visibilityOfElementLocated(source));
		WebElement option1 = dropdownWait.until(ExpectedConditions.visibilityOfElementLocated(target));
		dragNDrop(option,option1);
		}catch(Exception connect) {
			ExtentReportManager.getTest().fail("Source pipeline "+sourceName+" is not connected to target pipeline "+targetName+" due to "+connect.getMessage());
		}
	}public void clickZoomOutButton() {
		standardClickButton(zoomOut,"Zoom Out");
	}public WebElement getPipelineNode(String name) {
		try {
		By xpath=By.xpath("(//span[contains(text(),'"+name+"')]/../..)[1]");
		WebElement option = dropdownWait.until(ExpectedConditions.visibilityOfElementLocated(xpath));
		return option;
		}catch(Exception e) {
			
			return driver.findElement(By.xpath("//img[contains(@class,'SideNav_onePlatformLogo')]"));
		}
	}public void selectTransformationOutput(String value) {
		By loc = By.xpath("(//span[contains(text(),'Select transformation output column')])[1]//ancestor::div[contains(@class,'ant-select-show-arrow')]");
		String xpath ="(//span[contains(text(),'Select transformation output column')])[1]//ancestor::span//input";
		try {
			WebElement element = dropdownWait.until(ExpectedConditions.visibilityOfElementLocated(loc));
			element.click();
			WebElement textbox =driver.findElement(By.xpath(xpath));
			actions = new Actions(driver);
			actions.moveToElement(textbox).click().sendKeys(value).build().perform();
			driver.findElement(By.xpath("//span[text()='incremental_id']")).click();
			ExtentReportManager.getTest().pass(value+" was selected from tranformation output column");
		}catch(Exception e) {
			ExtentReportManager.getTest().fail("Failed to select tranformation type column due to "+e.getMessage());
		}
	}public void targetConfigurationCloseButton() {
		standardClickButton(targetCloseButton,"Close");
	}public void clickTrigger() {
		standardClickButton(trigger,"Trigger");
	}public void entercommitMessage(String name) throws IOException{
		standardEnterTextbox(commitMessage,name,"Commit Message");
	}public void enterSummaryMessage(String name) throws IOException{
		standardEnterTextbox(changeSummaryMessage,name,"Change Summary Description");
	}public void clickCommit() {
		standardClickButton(commitButton,"Commit");
	}public void tranfomrationInputSearchbox(String name) throws IOException{
		standardEnterTextbox(tranfomrationInputSearchbox,name,"Searchbox");
	}public void drangNDropInputType() {
		By pane1 = By.xpath("//div[@class='react-flow__pane']");
		By loc = By.xpath("//li[contains(@class,'transformationCategoryList_operationItem')]");
		try {
			WebElement pane = wait.until(ExpectedConditions.visibilityOfElementLocated(pane1));
			WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
			dragNDrop(option,pane);
		}catch(Exception e) {
			ExtentReportManager.getTest().fail("Failed to drang and drop option");
		}
	}public void waitForTransfomationPipelineExecution(String pName) {
		By loc =By.xpath("//span[contains(@class,'pipeline_ellipsisText')]");
		int maxAttempts = 15;
		WebElement Searchbox=driver.findElement(By.xpath("(//input[@class='searchInput'])[2]"));
		for (int i = 0; i < maxAttempts; i++) {
		    try {
		    	Thread.sleep(4000);
		    	clearTextbox(Searchbox);
		    	Searchbox.sendKeys(pName);
		        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(60));
		        

		        // Wait up to 60s for the element to be present in the DOM (not necessarily visible)
		        wait1.until(ExpectedConditions.presenceOfElementLocated(loc));
		        WebElement element=driver.findElement(loc);
		        // If present, exit the function early
		        if(element.getText().equals("In Progress")) {
		        	Thread.sleep(60000);
		        	continue;
		        }
		        getPipelineStatus(driver.findElement(loc),pName);
		        return;

		    } catch (Exception e) {
		        // If not present → click Refresh and continue to next attempt
		        try {
		            WebElement refreshButton = driver.findElement(
		                By.xpath("//button[contains(@class,'refreshButton')]")
		            );
		            refreshButton.click();
		        } catch (Exception ignored) {}
		    }
		}
	}public void enterFilterVlue(String value) throws IOException{
		standardEnterTextbox(filterConditionValueTextbox,value,"Value");
	}public void selectCastDataTypeConfig(String column,String value) {
		By loc =By.xpath("(//div[text()='"+column+"']//following::div[contains(@class,'ant-select ant-select-outlined')])[1]");
		By loc1 = By.xpath("(//div[text()='"+column+"']//following::input)[1]");
		try {
			WebElement element=driver.findElement(loc);
			WebElement element1=driver.findElement(loc1);
			element.click();
			typeTextFromKeyboard(element1, value);
			element1.sendKeys(Keys.ENTER);
		}catch(Exception e){
			
		}
		
	}public void deleteCanvas(String name) throws IOException {
		By loc = By.xpath("(//span[text()='"+name+"']//preceding::div[contains(@class,'PipelineCanvas_deleteIcon')])[count(//span[text()='"+name+"']//preceding::div[contains(@class,'PipelineCanvas_deleteIcon')])]");
		try {
			mouseOverElement(getPipelineNode(name));
			WebElement option = dropdownWait.until(ExpectedConditions.visibilityOfElementLocated(loc));
			standardClickButton(option,name+" delete");
			clickSpanText("Delete");
		}catch(Exception e) {
			 ExtentReportManager.getTest().fail("Unable to delete the pipeline canvas due to "+e.getMessage());
			 captureScreenshot();
		}
	}public void selectJoinFrom(String value) {
		By loc = By.xpath("//div[text()='From']//following::div[1]");
		By loc2 = By.xpath("//*[text()='From']//following::input[@type='search'][1]");
		By loc1=By.xpath("//span[contains(@class,'sequentialJoinBuilder_columnName') and text()='"+value+"']");
		try {
			WebElement option = dropdownWait.until(ExpectedConditions.visibilityOfElementLocated(loc));
			option.click();
			WebElement textbox = dropdownWait.until(ExpectedConditions.visibilityOfElementLocated(loc2));
			textbox.sendKeys(value);
			WebElement option1 = dropdownWait.until(ExpectedConditions.visibilityOfElementLocated(loc1));
			option1.click();
			ExtentReportManager.getTest().pass(
	                "'" + value + "' is selected from 'From' dropdown element");
		}catch(Exception e) {
			ExtentReportManager.getTest().fail(
	                "'" + value + "' is not selected from 'From' dropdown element due to "+e.getMessage());
		}
	}public void selectJoinTo(String value) {
		By loc = By.xpath("//div[text()='To']//following::div[1]");
		By loc1=By.xpath("//span[contains(@class,'sequentialJoinBuilder_columnName') and text()='"+value+"']");
		By loc2 = By.xpath("//*[text()='To']//following::input[@type='search'][1]");
		try {
			WebElement option = dropdownWait.until(ExpectedConditions.visibilityOfElementLocated(loc));
			option.click();
			WebElement textbox = dropdownWait.until(ExpectedConditions.visibilityOfElementLocated(loc2));
			textbox.sendKeys(value);
			WebElement option1 = dropdownWait.until(ExpectedConditions.visibilityOfElementLocated(loc1));
			option1.click();
			ExtentReportManager.getTest().pass(
	                "'" + value + "' is selected from 'To' dropdown element");
		}catch(Exception e) {
			ExtentReportManager.getTest().fail(
	                "'" + value + "' is not selected from 'To' dropdown element due to "+e.getMessage());
		}
	}public void clickDuplicateSlectAll(String name) {
		By loc = By.xpath("(//span[text()='"+name+"']//following::label[contains(@class,'selectAllCheckbox')])[1]");
		try {
			WebElement option = dropdownWait.until(ExpectedConditions.visibilityOfElementLocated(loc));
			standardClickButton(option,"Select All");
		}catch(Exception e) {
			
		}	
	}public void selectDataQualityRuleType(String rule) {
		By loc = By.xpath("//div[contains(@class,'DataQualityRulesConfigurationPanel_section')]//following::div[@class='ant-select-selector'][1]");
		try {
			WebElement dropdown =dropdownWait.until(ExpectedConditions.visibilityOfElementLocated(loc));
			dropdown.click();
			textClick("div",rule);
		}catch(Exception e) {
			ExtentReportManager.getTest().fail("Failed to select "+rule+" from Data Quality Rule Dropdown due to "+e.getMessage());
		}
		
	}public void selectDataQualityRuleColumn(String column) {
		By loc = By.xpath("(//label[@title='Columns']//following::div[@class='ant-form-item-control-input'])[1]");
		By loc1 = By.xpath("//div[@class='ant-select-item-option-content' and contains(text(),'"+column+"')]");
		By loc2 = By.xpath("//*[text()='Columns']//following::input[@type='search'][1]");
		try {
			WebElement dropdown =dropdownWait.until(ExpectedConditions.visibilityOfElementLocated(loc));
			dropdown.click();
			WebElement textbox = dropdownWait.until(ExpectedConditions.visibilityOfElementLocated(loc2));
			textbox.sendKeys(column);
			WebElement value =dropdownWait.until(ExpectedConditions.visibilityOfElementLocated(loc1));
			value.click();
			ExtentReportManager.getTest().pass("Succesfully selected "+column+" value from Data Quality Rules Columns dropdwon");
		}catch(Exception e) {
			ExtentReportManager.getTest().fail("Failed to select "+column+" value from Data Quality Rules Columns dropdwon due to "+e.getMessage());
		}
	}public void enterDataQualityRejectionRemark(String text) throws IOException {
		standardEnterTextbox(dataQualityRejectionRemark,text,"Rejection Remark");
	}public void selectNewDataType(String column,String dataType) {
		By loc = By.xpath("//div[text()='"+column+"']//following::div[@class='ant-select-selector'][1]");
		try {
			WebElement dropdown =dropdownWait.until(ExpectedConditions.visibilityOfElementLocated(loc));
			dropdownElement(dropdown,dataType,column+" New Data Type");
		}catch(Exception e) {
			
		}
	}public void enterCastDatatypeSearchColumn(String value) throws IOException {
		standardEnterTextbox(castDatatypeSearchColumn,value,"Search Column");
	}public void clickVersionHistory() {
		standardClickButton(versionHistory,"Version History");
	}public void pipelineScriptVerification(String s) {
		By loc = By.xpath("//div[contains(@class,'dataFlowPreview_dataFlowPreview')]");
		try {
			WebElement textbox =dropdownWait.until(ExpectedConditions.visibilityOfElementLocated(loc));
			String text = textbox.getText();
			if(text.contains(s)) {
				ExtentReportManager.getTest().pass(s +" is present in Pipeline script");
			}else {
				ExtentReportManager.getTest().fail(s +" is not present in Pipeline script");
			}
		}catch(Exception e) {
			
		}
	}public void selectFromVersionDropdown(String version) {
		By loc = By.xpath("//div[contains(@class,'VersionDropdown_versionSection')]");
		By loc1 = By.xpath("//span[text()='"+version+"']");
		try {
			WebElement dropdown =dropdownWait.until(ExpectedConditions.visibilityOfElementLocated(loc));
			dropdown.click();
			WebElement element =dropdownWait.until(ExpectedConditions.visibilityOfElementLocated(loc1));
			element.click();
			ExtentReportManager.getTest().pass(version +" is selected from version dropdown");
		}catch(Exception e) {
			ExtentReportManager.getTest().fail(version +" is not selected from version dropdown due to "+e.getMessage());
		}
	}public void clickViewVersionHistory() {
		By loc = By.xpath("//div[contains(@class,'VersionDropdown_versionSection')]");
		try {
			WebElement dropdown =dropdownWait.until(ExpectedConditions.visibilityOfElementLocated(loc));
			dropdown.click();
			textClick("div","View Version History");
		}catch(Exception e) {
			
		}
	}

}
