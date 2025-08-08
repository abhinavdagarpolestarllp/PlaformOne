package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MDMIntegrationPipeline extends basePage{
	//WebDriver driver;
	public MDMIntegrationPipeline(WebDriver driver) {
		super(driver);
	}@FindBy(xpath="(//span[text()='Name'])[2]//following-sibling::div")
	public WebElement firstPipelineName;
	@FindBy(xpath="(//img[contains(@src,'EditPencil')])[1]")
	public WebElement editPipeline;
	@FindBy(xpath="(//img[contains(@src,'pipelineViewEye')])[1]")
	public WebElement viewPipeline;
	@FindBy(xpath="(//img[contains(@src,'schedulePipeline')])[1]")
	public WebElement schedulePipeline;
	@FindBy(xpath="//span[contains(@class,'popupcrossbtnCustom')]")
	public WebElement transformationTypeClose;
	@FindBy(xpath="//*[@data-icon='close']")
	public WebElement schedulePipelineDialogClose;
	public void clickEditPipeline() {
		standardClickButton(editPipeline,"Edit Pipeline");
	}public void clickViewPipeline() {
		standardClickButton(viewPipeline,"View Pipeline");
	}public void clickScheculedPipeline() {
		standardClickButton(schedulePipeline,"Schedule Pipeline");
	}public void clickTransformationTypeDialogCloseButton() {
		standardClickButton(transformationTypeClose,"Close");
	}public void schedulePipelineDialogCloseButton() {
		standardClickButton(schedulePipelineDialogClose,"Close");
	}
	
}
