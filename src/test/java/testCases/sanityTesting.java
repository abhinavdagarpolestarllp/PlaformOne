package testCases;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.DataNexusConnection;
import pageObjects.DataNexusDataDictionary;
import pageObjects.DataNexusDataLake;
import pageObjects.DataNexusTransformationV2;
import pageObjects.DataNexusDataModel;
import pageObjects.DataNexusDataProfiler;
import pageObjects.DataNexusDocumentParser;
import pageObjects.DataNexusIngestionPipeline;
import pageObjects.DataNexusMonitoring;
import pageObjects.DataNexusOrchestrator;
import pageObjects.DataNexusTransformationPipeline;
import pageObjects.MDMEntities;
import pageObjects.MDMIntegrationPipeline;
import pageObjects.MDMOverview;
import pageObjects.MigrationCICD;
import pageObjects.UserManagement;
import pageObjects.dataNexusHome;
import pageObjects.landingPage;
import pageObjects.loginPage;
import pageObjects.MDMMasters;
import pageObjects.workspaceList;
import testBase.baseClass;
public class sanityTesting extends baseClass{
	loginPage login;
	workspaceList ws;
	dataNexusHome home ;
	MDMEntities entities;
	MDMOverview overview;
	DataNexusTransformationV2 v2;
	String pipelineName;
	String IGPipelineName;
	DataNexusOrchestrator orch;
	String OrchestratorName;
	String processName;
	DataNexusDocumentParser parse;
	DataNexusIngestionPipeline ig;
	MigrationCICD migrate;
	String pageName;
	@Test(priority=1,description ="Login Page")
	public void LoginPage() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("LoginSetup");
		login = new loginPage(getDriver());
		login.setPageName("Login Page - ");
		FileReader file = new FileReader(System.getProperty("user.dir") 
		        + "/src/test/resources/config.properties");
		p=new Properties();
		p.load(file);
		login.enterUsername(p.getProperty("username"));
		login.enterPassword(p.getProperty("password"));
		login.clickLogin();
		String successLoginMessage=login.getLoginMessage();
		Thread.sleep(2000);
		login.compareStrings(successLoginMessage, "Logged In Successfully.","Successful Login message");
	}@Test(priority=2)
	public void landingPage() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("LandingPage");
		landingPage landing = new landingPage(getDriver());
		landing.setPageName("Landing Page - ");
		
		login.isObjectExist(landing.dataSolutions,"Data Nexus Logo");
		landing.dataNexusClick();
	}
	@Test(priority=3)
	public void workspaceListDataNexus() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("DataNexus");
		ws = new workspaceList(getDriver());
		ws.setPageName("Workspace List - ");

		login.isObjectExist(ws.onePlatformLogoWorkspaceListPage,"One Platform Logo");
		login.isHeaderPresent("h1", "Workspaces");
		ws.clickMDMButton();
		login.isObjectExist(ws.firstMDMWorkspace);
		ws.clickPAIButton();
		login.isObjectExist(ws.firstPAIWorkspace);
		ws.clickDataNexus();
		//String secondWorkspace = login.getText(ws.secondWorkspace);
		ws.searchBox("Automation_DataNexus");
		Thread.sleep(1000);
		ws.clickWorkSpace("Automation_DataNexus");
	}
	@Test(priority=4)
	public void dataNexusHome() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("DataNexusHome");
		login = new loginPage(getDriver());
		home= new dataNexusHome(getDriver());
		login.extendedWait.until(ExpectedConditions.visibilityOf(home.totalPipelines));
		login.isHeaderPresent("h5", "Home");
		home.searchBox("Test");
		//login.isObjectExist(home.firstPipeline);
		home.clickActive();
		home.clickInctive();
		home.clickScheduled();
	}@Test(priority=5)
	public void dataNexusDataLake() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("DataNexusDataLake");
		DataNexusDataLake dataLake = new DataNexusDataLake(getDriver());
		login.clickSideNavigationModule("Data Lake");
		login.isHeaderPresent("h5", "Data Lake");
		//login.standardDropdownElement("LakeHouse: ", "LAK1");
		login.wait.until(ExpectedConditions.elementToBeClickable(dataLake.treeSwitch));
		//login.dropdownElement(dataLake.lakeHouseSelectDropdown, dataLake.);
		//Select select = new Select(dataLake.lakeHouseSelectDropdown);
		//select.selectByVisibleText("Silver_LH");
		login.isHeaderPresent("h2", "Directory Structure");
		/*dataLake.addRootDirectory();
		dataLake.driectoryName("Testing");
		login.clickCancel();*/
		dataLake.clickTable();
		//login.wait.until(ExpectedConditions.elementToBeClickable(dataLake.treeSwitch));
		login.isHeaderPresent("h2", "Tables");
	}@Test(priority=6)
	public void dataNexusConnections() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("DataNexusConnection");
		login.clickSideNavigationModule("Connections");
		login.isHeaderPresent("h5","Connections");
		login.clickCreateNew();
		login.isHeaderPresent("span", "New Connection");
		DataNexusConnection conn =new DataNexusConnection(getDriver());
		login.textClick("div", "Database");
		conn.newConnectionSearchbox("ms server");
		conn.setConnectionName(conn.connectionname);
		conn.clickFirstConnection();
		login.clickNext();
		conn.setDatabase("AdventureWorks2022");
		conn.setPassword("sql@1234");
		conn.setUserName("sqladmin");
		conn.setServer("psslp-genai.database.windows.net");
		conn.setPort("1433");
		login.testConnection();
		login.isObjectExist(conn.connectionTestSuccessMsg);
		login.messageClose();
		login.clickClose();
		login.clickEdit();
		login.isDialogHeaderPresent("span", "Edit Source");
		login.clickClose();
	}@Test(priority=7)
	public void dataNexusIngestionPipeline() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("DataNexusIngestionPipeline");
		login.clickSideNavigationModule("Ingestion Pipeline");
		login.isHeaderPresent("h5","Ingestion Pipelines");
		login.clickCreateNew();
		 ig = new DataNexusIngestionPipeline(getDriver());
		IGPipelineName=ig.iGPipelineName;
		ig.enterPipelineName(IGPipelineName);
		ig.selectLakehouseName("LAK1");
		ig.selectDataLakePath("Customers");
		//ig.selectDatalkeName("BDS_SAMPLE_DATA");
		ig.selectSource("SQL Server");
		ig.selectSchema("HumanResources");
		ig.clickCustomSelectorCheckBox("Customers");
		ig.columnSelector("Customers");
		login.clickSaveButton();
		ig.setFocusOn();
		ig.clickSpanText("Save Pipeline");
		ig.isObjectExist(ig.pipelineCreatedMsg);
		login.enterInSearchbox(IGPipelineName);
		login.clickEdit();
		login.isHeaderPresent("span", "Edit");
		ig.columnSelector("Customers");
		login.clickSaveButton();
		login.clickSpanText("Update Pipeline");
		ig.isObjectExist(ig.pipelineupdatedMsg);
		login.clickTrigger();
		login.isObjectExist(login.triggerConfirmation);
		Actions actions = new Actions(getDriver());
        actions.sendKeys(Keys.ESCAPE).perform();
		//login.clickClose();
		login.clickDelete();
		login.isObjectExist(login.deleteConfirmation);
		actions.sendKeys(Keys.ESCAPE).perform();
		//login.clickCancel();
	}@Test(priority=8)
	public void dataNexusTransformationPipeline() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("DataNexusTransformationPipeline");
		login.clickSideNavigationModule("Transformation Pipeline");
		login.isHeaderPresent("h5","Transformation Pipeline");
		login.clickCreateNew();
		DataNexusTransformationPipeline ts = new DataNexusTransformationPipeline(getDriver());
		login.mouseOverElement(ts.addSource);
		login.clickSpanText("Delta Lake");
		login.isHeaderPresent("div", "Select Data Lake");
		ts.standardDropdownElement("Select Lake House","Lakehouse_Datalake_Analytics_Dev.Lakehouse");
		ts.standardDropdownElement("Select Lake House Type","Tables");
		ts.selectDataLakeCloseIcon.click();
		login.mouseOverElement(ts.addSource);
		login.clickSpanText("Datasource Connection");
		login.isHeaderPresent("h2", "Select Source Connections");
		login.clickClose();
		login.mouseOverElement(ts.addSource);
		login.clickSpanText("Custom Script");
		login.isObjectExist(ts.customScriptDialog);
		login.clickLinkText("Pipelines");
		login.clickYesButton();
		//login.clickTrigger();
		//login.isObjectExist(login.triggerConfirmation);
		//login.clickNoButton();
		login.clickDelete();
		login.isObjectExist(login.deleteConfirmation);
		login.clickNoButton();
	}@Test(priority=9)
	public void dataNexusDataModel() throws Exception{
		checkRunFlagSanity("DataNexusDataModel");
		DataNexusDataModel datamodel = new DataNexusDataModel(getDriver());
		login.clickSideNavigationModule("Data Model");
		login.isHeaderPresent("h5","Data Model");
		login.standardClickButton(datamodel.addDataModel,"Add Data Model");
		login.isHeaderPresent("div","Create Data Model");
		datamodel.standardEnterTextbox(datamodel.dataModelName, datamodel.modelName, "Data Model Name");
		datamodel.standardEnterTextbox(datamodel.dataModelDescription, "Testing1234", "Description");
		login.clickCancel();
		login.clickEdit();
		login.isDialogHeaderPresent("div", "Edit Data Model");
		login.clickCancel();
		DataNexusDataModel model = new DataNexusDataModel(getDriver());
		login.clickViewButton();
		model.clickAddTablesButton();	
		login.isDialogHeaderPresent("h4", "Add New Table");
		//login.clickSpanText("Upload Excel");
		//login.isDialogHeaderPresent("span", "Upload Excel File");
		//login.clickSpanText("Click to Upload Excel File");
		//login.uploadFile("C:\\Users\\abhinav.dagar\\Downloads\\ChatBot_Testing\\column_template.xlsx");
		//login.clickSpanText("Upload");
		//login.isObjectExist(model.fileUploadedMsg);
		/*login.clickSideNavigationModule("Data Dictionary");
		login.isHeaderPresent("h5","Data Dictionary");
		DataNexusDataDictionary dict = new DataNexusDataDictionary(driver);
		dict.standardClickButton(dict.silverLayer, "Silver Layer");
		dict.standardClickButton(dict.goldLayer, "Gold Layer");*/
	}@Test(priority=10)
	public void dataNexusTransformationPipelineV2() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("DataNexusTransformationV2");
		v2 = new DataNexusTransformationV2(getDriver());
		pipelineName=v2.TransformationPipelineV2Name;
		login.clickSideNavigationModule("Transformation Pipeline v2");
		login.isHeaderPresent("h5", "Transformation Pipeline");
		login.clickSpanText("Create New");
		login.isDialogHeaderPresent("div", "Create New Transformation Pipeline");
		v2.enterPipelineName(pipelineName);
		v2.enterPipelineDescription("Automation Testing");
		login.clickSpanText("Create & Proceed");
		//v2.openTransformationPannel();
		v2.selectSourceDataLake();
		login.isDialogHeaderPresent("div", "Configure Delta Lake Source");
		login.standardDropdownElement("Select Lakehouse", "LAK1");
		login.standardDropdownElement("Select Lakehouse Type", "Tables");
		Thread.sleep(15000);
		login.typeAheadWebElement("Source Table", "customertestscd2");
		login.clickSpanText("Proceed");
		//Thread.sleep(8000);
		//v2.getPipelineNode("customertestscd2");
		//login.waitForPageLoad(v2.getPipelineNode("customertestscd2"));
		v2.clickZoomOutButton();
		v2.clickZoomOutButton();
		v2.clickZoomOutButton();
		//v2.selectSourceCustomScript();
		//login.isObjectExist(v2.pipelineNode);
		v2.addTargetDataLake();
		login.isDialogHeaderPresent("div", "Select Target Type");
		v2.clicksourceSelectionModel();
		login.isDialogHeaderPresent("div", "Configure Delta Lake Target");
		login.standardDropdownElement("Select Lakehouse", "LAK1");
		login.standardDropdownElement("Select Lakehouse Type", "Tables");
		login.typeAheadWebElement("Target Table", "customertargetdim");
		login.clickSpanText("Proceed");
		login.waitForPageLoad(v2.getPipelineNode("customertargetdim"));
		v2.connectSourceTarget("customertestscd2", "customertargetdim");
		v2.clickSpanText("Save");
		login.isObjectExist(v2.pipelinesaved);
		login.doubleClick(v2.getPipelineNode("customertargetdim"), "customertargetdim");
		login.isDialogHeaderPresent("h2", "Target Configuration: Target_Operation");
		login.textClick("div", "Column Mapping");
		login.clickSpanText("Auto Map");
		v2.selectTransformationOutput("incremental_id");
		v2.targetConfigurationCloseButton();
		v2.clickSpanText("Save");
		login.clickSpanText("Deploy");
		login.isObjectExist(v2.deployMessage);
		v2.searchbox(pipelineName);
		login.clickEdit();
		login.isObjectExist(v2.pipelineEditMsg);
		login.clickSpanText("Make a copy");
		v2.clickSpanText("Save");
		login.isObjectExist(v2.pipelinesaved);
		login.clickSpanText("Commit");
		login.isDialogHeaderPresent("div", "Commit Pipeline");
		v2.entercommitMessage("Pipeline updated for testing purpose");
		v2.clickCommit();
		login.clickSpanText("Deploy");
		v2.enterSummaryMessage("Pipeline updated for testing purpose");
		login.isObjectExist(v2.pipelineCommitedMsg);
		login.clickSpanText("Submit");
		login.isObjectExist(v2.piplineUpdatedMsg);
		v2.searchbox(pipelineName);
		v2.clickTrigger();
		login.isObjectExist(v2.piplineTriggeredMsg);
		
		/*v2.searchbox("");
		login.clickEdit();
		login.isObjectExist(v2.pipelineEditMsg);
		login.clickSpanText("Make a copy");
		v2.clickSpanText("Save");
		login.isObjectExist(v2.pipelinesaved);
		login.clickSpanText("Commit");
		login.isDialogHeaderPresent("div", "Commit Pipeline");
		v2.entercommitMessage("Pipeline updated for testing purpose");
		v2.clickCommit();
		login.clickSpanText("Deploy");
		v2.enterSummaryMessage("Pipeline updated for testing purpose");
		login.isObjectExist(v2.pipelineCommitedMsg);
		login.clickSpanText("Submit");
		login.isObjectExist(v2.piplineUpdatedMsg);*/
	}@Test(priority=11)
	public void dataNexusOrchestrator() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("DataNexusOrchestrator");
		login.clickSideNavigationModule("Orchestrator");
		login.isHeaderPresent("h5","Process Scheduler");
		 orch = new DataNexusOrchestrator(getDriver());
		OrchestratorName = orch.orchestratorName;
		orch.clickCreateNewProcess();
		orch.enterPipelineName(OrchestratorName);
		orch.clickAddPipelineButton();
		login.isHeaderPresent("h2", "Select Pipeline");
		orch.selectPipelineType("Ingestion");
		orch.selectPipeline(IGPipelineName);
		orch.clickSpanText("Done");
		orch.clickSpanText("Save");
		login.isObjectExist(orch.scheduleCreatedMsg);
		login.enterInSearchbox(OrchestratorName);
		login.clickEdit();
		login.clickLinkText("Process");
		login.clickYesButton();
		login.clickTrigger();
		//login.isObjectExist(login.triggerConfirmation);
		login.clickYesButton();
		login.isObjectExist(orch.pipelineTriggeredMessage);
		Thread.sleep(4000);		
		login.enterInSearchbox(OrchestratorName);
		login.clickViewLogsButton();
		login.isHeaderPresent("span", "Process Scheduler Logs");
		//login.enterInSearchbox("Manual");
		orch.waitForProcessCompleted();
		orch.clickProcessId();
		orch.waitForPipelineVisible(IGPipelineName);
		login.captureScreenshot();
		//login.nonLinkText("New Process");
	}@Test(priority=12)
	public void dataNexusDocumentParser() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("DataNexusDocumentParser");
		login.clickSideNavigationModule("Document Parser");
		login.isHeaderPresent("h5", "Document Parser");
		parse = new DataNexusDocumentParser(getDriver());
		processName = parse.pname;
		parse.clickAddProcess();
		login.isDialogHeaderPresent("span", "New Process");
		parse.enterProcessName("Automation_Test_Parser");
		parse.chooseLakehouse("LAK1");
		parse.choosedefinePath("DevPras/Files/FORM_C/");
		Thread.sleep(2000);
		parse.chooseTargetPath("DevPras/to_be_processed/files/");
		parse.textClick("button", "Save");
		login.isObjectExist(parse.createProcessMsg);
		parse.enterInSearchbox("Automation_Test_Parser");
		login.clickEdit();
		login.isDialogHeaderPresent("span", "Edit Process");
		parse.enterProcessName(processName);
		parse.textClick("button", "Update");
		login.isObjectExist(parse.updateProcessMsg);
		parse.enterInSearchbox(processName);
		login.clickViewButton();
		login.isDialogHeaderPresent("span", "View Process");
		parse.textClick("button", "Close");
		
	}
	
	@Test(priority=12)
	public void dataNexusDataProfiler() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("DataNexusDataProfiler");
		login.clickSideNavigationModule("Data Profiler");
		login.isHeaderPresent("h5","Data Profiler");
		DataNexusDataProfiler dataprofile = new DataNexusDataProfiler(getDriver());
		login.isObjectExist(dataprofile.lakehouseDataFecthedMessage);
		login.dropdownElement(dataprofile.dataLakeHouse, "Lakehouse_Datalake_Analytics_Dev","Date LakeHouse");
		login.dropdownElement(dataprofile.selectTable,"Config_Logs_Transformation","Table");
		login.isObjectExist(dataprofile.profileDataFecthedMessage);
	}@Test(priority=13)
	public void dataNexusMonitoring() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("DataNexusMonitoring");
		login.clickSideNavigationModule("Monitoring");
		login.isHeaderPresent("h5","Monitoring");
		DataNexusMonitoring monitor = new DataNexusMonitoring(getDriver());
		login.clickTrigger();
		monitor.clickcolumnData("Log Id");
		login.textClick("div", "Transformation And Data Quality");
		v2.waitForTransfomationPipelineExecution(pipelineName);
		//login.clickViewLogsButton();
		//login.isObjectExist(login.logsIcon);
	}@Test(priority=13)
	public void migrationCICD() throws InterruptedException, IOException {
		checkRunFlagSanity("MigrationCICD");
		login.changeEnv("uat");
		login.clickSideNavigationModule("Admin");
		login.clickSideNavigationModule("Migration CI/CD");
		login.isHeaderPresent("h5", "Migration Pipelines");
		migrate=new MigrationCICD(getDriver());
		migrate.selectMigrationPipelineType("Ingestion");
		migrate.enterInSearchbox(IGPipelineName);
		login.clickCheckBox(IGPipelineName);
		migrate.selectMigrationPipelineType("Transformation");
		migrate.enterInSearchbox(pipelineName);
		login.clickCheckBox(pipelineName);
		migrate.selectMigrationPipelineType("OCR");
		migrate.enterInSearchbox(processName);
		login.clickCheckBox(processName);
		login.clickSpanText("Migrate changes from dev");
		login.clickSpanText("Move Pipeline");
		login.isObjectExist(migrate.pipelineMovementMsg);
		Thread.sleep(5000);
		login.clickSpanText("Deployment Logs");
		login.isDialogHeaderPresent("h2", "Deployment Logs");
		migrate.clickTransactionId();
		login.isDialogHeaderPresent("h2", "Deployment History");
		migrate.getDeployedPipelineStatus(IGPipelineName);
		migrate.getDeployedPipelineStatus(pipelineName);
		//migrate.getDeployedPipelineStatus(OrchestratorName);
		migrate.getDeployedPipelineStatus(processName);
		login.clickClose();
		migrate.selectMigrationPipelineType("Orchestrator");
		migrate.enterInSearchbox(OrchestratorName);
		login.clickCheckBox(OrchestratorName);
		login.clickSpanText("Migrate changes from dev");
		login.clickSpanText("Move Pipeline");
		login.isObjectExist(migrate.pipelineMovementMsg);
		Thread.sleep(5000);
		login.clickSpanText("Deployment Logs");
		login.isDialogHeaderPresent("h2", "Deployment Logs");
		migrate.clickTransactionId();
		migrate.getDeployedPipelineStatus(OrchestratorName);
		login.clickSideNavigationModule("Transformation Pipeline v2");
		login.clickSideNavCollapse();
		v2.searchbox(pipelineName);
		login.clickDelete();
		login.clickYesButton();
		login.isObjectExist(v2.piplineDeletedMsg);
		login.clickSideNavCollapse();
		login.clickSideNavigationModule("Orchestrator");
		login.clickDelete();
		login.clickYesButton();
		login.isObjectExist(orch.pipelineDeletedMessage);
		login.clickSideNavigationModule("Ingestion Pipeline");
		login.enterInSearchbox(IGPipelineName);
		login.clickDelete();
		login.clickSpanText("Yes");
		login.isObjectExist(ig.deletePipelineMsg);
		login.clickSideNavigationModule("Document Parser");
		parse.enterInSearchbox(processName);
		login.clickDelete();
		login.clickYesButton();
		login.isObjectExist(parse.deleteProcessMsg);
		login.changeEnv("dev");
		Thread.sleep(5000);
		login.clickSideNavigationModule("Transformation Pipeline v2");
		login.clickSideNavCollapse();
		v2.searchbox(pipelineName);
		login.clickDelete();
		login.clickYesButton();
		login.isObjectExist(v2.piplineDeletedMsg);
		login.clickSideNavCollapse();
		login.clickSideNavigationModule("Orchestrator");
		login.clickDelete();
		login.clickYesButton();
		login.isObjectExist(orch.pipelineDeletedMessage);
		login.clickSideNavigationModule("Ingestion Pipeline");
		login.enterInSearchbox(IGPipelineName);
		login.clickDelete();
		login.clickSpanText("Yes");
		login.isObjectExist(ig.deletePipelineMsg);
		login.clickSideNavigationModule("Document Parser");
		parse.enterInSearchbox(processName);
		login.clickDelete();
		login.clickYesButton();
		login.isObjectExist(parse.deleteProcessMsg);
	}
	@Test(priority=14)
	public void tenants() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("Tenants");
		login.clickSideNavigationModule("Admin");
		login.clickSideNavigationModule("Tenants");
		login.isHeaderPresent("h5","Tenants");
		login.clickSpanText("Add Tenant");
		login.isHeaderPresent("h4","Add New Tenant");
		login.clickClose();
	}@Test(priority=15)
	public void userManagement() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("UserManagement");
		login.clickSideNavigationModule("Lineage");
		login.clickSideNavigationModule("Admin");
		login.clickSideNavigationModule("User Management");
		UserManagement usermanage = new UserManagement(getDriver());
		login.isHeaderPresent("h5","User Management");
		login.clickSpanText("New Role");
		login.nonLinkText("New Role");
		login.clickLinkText("User Management");
		login.textClick("div", "Users");
		//login.standardClickButton(usermanage.usersTab, "Users");
		login.clickSpanText("New User");
		login.nonLinkText("Add User");
		login.topNavigationSection("Profile");
		login.isHeaderPresent("h5","My Info");
		/*login.topNavigationSection("Log out");
		login.isObjectExist(login.logoutUserMsg);
		login.isObjectExist(login.username_textbox);*/
		
	}@Test(priority=16)
	public void workspaceListMDM() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("MDM");
		login.checkAndNavigateWorkspaceListPage();
		ws.clickMDMButton();
		ws.searchBox("MDM-QA2");
		Thread.sleep(1000);
		ws.clickWorkSpace("MDM-QA2");
	}@Test(priority=17)
	public void MDMOverview() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("MDMOverview");
		MDMOverview overview = new MDMOverview(getDriver());
		home= new dataNexusHome(getDriver());
		//home= new dataNexusHome(driver);
		login.isHeaderPresent("h5", "Home");
		login.isObjectExist(overview.mDMHomeRecordIcon("Total Records"));
		login.isObjectExist(overview.mDMHomeRecordIcon("Unique Clean Records"));
		login.isObjectExist(overview.mDMHomeRecordIcon("Total Entities"));
		login.isObjectExist(overview.mDMHomeRecordIcon("Potential Duplicate %"));
		login.isObjectExist(overview.mDMHomeRecordIcon("Incomplete Records %"));
		overview.clickpotentialDuplicateViewDetail();
		login.isHeaderPresent("span", "Potential Duplicate Records");
		login.clickSpanText("Dashboard");
		overview.clickincompleteRecordsViewDetail();
		login.isHeaderPresent("span", "Incomplete Record Table");
		login.clickSpanText("Dashboard");
	}@Test(priority=18)
	public void MDMEntity() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("MDMEntity");
		login.clickSideNavigationModule("Entities");
		entities = new MDMEntities(getDriver());
		entities.clickFirstSideNavEntity();
		login.isHeaderPresent("h5", "Entity");
		login.clickSpanText("Add New");
		login.isHeaderPresent("h2", "New Parent");
		login.clickClose();
		login.clickYesButton();
		entities.clickEntityCard();
		login.isHeaderPresent("span", "Golden Record Details");
		login.clickBackButton();
		//login.enterInSearchbox("10");
		//login.compareStrings("10", entities.entityCard.getText(), "Card ");
	}@Test(priority=19)
	public void MDMTaxonomy() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("MDMTaxonomy");
		login.clickSideNavigationModule("Taxonomy");
		login.isHeaderPresent("h5", "Taxonomy");
		login.clickSpanText("Add New");
		login.isHeaderPresent("span", "Add New Taxonomy");
		//entities.selectCreatedTaxonomyFor("Sample Data");
		entities.enterTaxonomyName("Taxonomy123");
	}@Test(priority=20)
	public void MDMMasters() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("MDMMasters");
		MDMMasters master = new MDMMasters(getDriver());
		login.clickSideNavigationModule("Masters");
		login.isHeaderPresent("h5", "Masters");
		login.waitForPageLoad(login.searchbox);
		login.clickSpanText("Add Master");
		login.isHeaderPresent("span", "New Master");
		login.textClick("div", "Masters");
		login.clickYesButton();
		master.clickEdit();
		login.textClick("div", "Masters");
		login.clickYesButton();
		master.clickConfigure();
		login.isHeaderPresent("span", "De-Duplication Configuration ");
		login.textClick("div", "MastersList");
		master.clickSchedule();
		login.isDialogHeaderPresent("div", "Schedule Deduplication");
		login.clickClose();
		//login.enterInSearchbox(login.columnTextLink("Name").getText());
		//login.clearTextbox(login.searchbox);
		//login.enterInSearchbox(login.columnTextLink("Name").getText());
		String s = login.columnTextLink("Name").getText();
		login.columnTextLink("Name").click();
		//login.waitForPageLoad(login.columnTextLink("id"));
		//login.clickSpanText("Data Entry");
		//login.isHeaderPresent("span", s);
	}@Test(priority=21)
	public void MDMIntegration() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("MDMIntegration");
		login.clickSideNavigationModule("Integration");
		login.clickSideNavigationModule("Connectors");
		login.isHeaderPresent("h6", "Data Sources");
		login.clickSpanText("Add New");
		login.isHeaderPresent("h2", "New Connection");	
		login.clickSideNavigationModule("Pipelines");
		login.waitForPageLoad(home.totalPipelines);
		login.clickSpanText("New Pipeline");
		login.isHeaderPresent("div", "Choose Transformation Type");
		MDMIntegrationPipeline pipeline = new MDMIntegrationPipeline(getDriver());
		pipeline.clickTransformationTypeDialogCloseButton();
		login.enterInSearchbox(pipeline.firstPipelineName.getText());
		pipeline.clickEditPipeline();
		login.textClick("a", "Pipelines");
		login.clickYesButton();
		login.enterInSearchbox(pipeline.firstPipelineName.getText());
		pipeline.clickViewPipeline();
		login.textClick("a", "Pipelines");
		//login.clickYesButton();
		login.enterInSearchbox(pipeline.firstPipelineName.getText());
		pipeline.clickScheculedPipeline();
		login.isHeaderPresent("div", "Schedule Pipeline");
		pipeline.schedulePipelineDialogCloseButton();
	}@Test(priority=22)
	public void MDMDataQuality() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("MDMDataQuality");
		overview = new MDMOverview(getDriver());
		login.clickSideNavigationModule("Data Quality");
		login.isHeaderPresent("h5", "Data Quality Dashboard");
		login.isObjectExist(overview.mDMHomeRecordIcon("Total Records"));
		login.isObjectExist(overview.mDMHomeRecordIcon("Unique Clean Records"));
		login.isObjectExist(overview.mDMHomeRecordIcon("Total Entities"));
		login.isObjectExist(overview.mDMHomeRecordIcon("Potential Duplicate %"));
		login.isObjectExist(overview.mDMHomeRecordIcon("Incomplete Records %"));
		overview.clickpotentialDuplicateViewDetail();
		login.isHeaderPresent("span", "Potential Duplicate Records");
		/*login.enterInSearchbox("Product");
		overview.clickViewTableButton();
		login.isHeaderPresent("span","Product Report");
		login.clickSpanText("Dashboard");*/
		login.clickSpanText("Dashboard");
		overview.clickincompleteRecordsViewDetail();
		login.isHeaderPresent("span", "Incomplete Record Table");
		/*login.enterInSearchbox("Student");
		overview.clickViewTableButton();
		login.isHeaderPresent("span", "Student");
		login.textClick("a", "Incomplete Records Table");
		login.clickSpanText("Dashboard");*/	
	}
		@Test(priority=23)
		public void AuditLogs() throws InterruptedException,UnexpectedTagNameException, IOException{
			checkRunFlagSanity("AuditLogs");
		login.clickSideNavigationModule("Settings");
		login.clickSideNavigationModule("Audit Logs");
		login.isHeaderPresent("h5", "Audit Logs");
		}@Test(priority=24)
		public void UserManagement() throws InterruptedException,UnexpectedTagNameException, IOException{
			checkRunFlagSanity("UserManagement");
		//login.clickSideNavigationModule("Settings");
		//login.clickSideNavigationModule("Audit Logs");
		login.isHeaderPresent("h5", "Audit Logs");
		login.clickSideNavigationModule("User Management");
		login.isHeaderPresent("h5", "User Management");
		login.clickSpanText("New Role");
		UserManagement user = new UserManagement(getDriver());
		login.textClick("a", "User Management");
		String roleName=user.firstRoleName.getText();
		login.enterInSearchbox(roleName);
		login.textClick("p", roleName);
		login.textClick("a", "User Management");
		login.enterInSearchbox(roleName);
		login.clickEdit();
		login.textClick("a", "User Management");
	}@Test(priority = 24)
	public void logout() throws IOException {
		checkRunFlagSanity("Logout");
		login.topNavigationSection("Log out");
		login.isObjectExist(login.logoutUserMsg);
		login.isObjectExist(login.username_textbox,"Username Textbox");
	}
}
