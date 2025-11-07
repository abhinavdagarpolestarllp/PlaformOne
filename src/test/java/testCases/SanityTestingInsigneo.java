package testCases;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.DataNexusConnection;
import pageObjects.DataNexusDataDictionary;
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
import pageObjects.UserManagement;
import pageObjects.dataNexusDataLake;
import pageObjects.dataNexusHome;
import pageObjects.landingPage;
import pageObjects.loginPage;
import pageObjects.MDMMasters;
import pageObjects.workspaceList;
import testBase.baseClass;
public class SanityTestingInsigneo extends baseClass{
	loginPage login;
	workspaceList ws;
	dataNexusHome home ;
	MDMEntities entities;
	MDMOverview overview;
	DataNexusIngestionPipeline ig;
	DataNexusOrchestrator orch;
	DataNexusTransformationV2 v2;
	
	String IGPipelineName;
	String pipelineName;
	String OrchestratorName;
	
	@Test(priority=1)
	public void LoginPage() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("LoginSetup");
		
		login = new loginPage(getDriver());
		login.startingClose();
		FileReader file = new FileReader(
			    System.getProperty("user.dir") + File.separator +
			    "src" + File.separator +
			    "test" + File.separator +
			    "resources" + File.separator +
			    "config.properties"
			);
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
		login.isObjectExist(landing.dataSolutions);
		landing.dataNexusClick();
	}
	@Test(priority=3)
	public void workspaceListDataNexus() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("DataNexus");
		ws = new workspaceList(getDriver());
		login.isObjectExist(ws.onePlatformLogoWorkspaceListPage);
		login.isHeaderPresent("h1", "Workspaces");
		ws.clickMDMButton();
		login.isObjectExist(ws.firstMDMWorkspace);
		ws.clickPAIButton();
		//login.isObjectExist(ws.firstPAIWorkspace);
		ws.clickDataNexus();
		//String secondWorkspace = login.getText(ws.secondWorkspace);
		ws.searchBox("performance manag");
		Thread.sleep(1000);
		ws.clickWorkSpace("performance manag...");
	}
	@Test(priority=4)
	public void dataNexusHome() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("DataNexusHome");
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
		dataNexusDataLake dataLake = new dataNexusDataLake(getDriver());
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
		DataNexusConnection conn =new DataNexusConnection(getDriver()) ;
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
		ig = new DataNexusIngestionPipeline(getDriver());
		IGPipelineName=ig.iGPipelineName;
		login.clickSideNavigationModule("Ingestion Pipeline");
		login.isHeaderPresent("h5","Ingestion Pipelines");
		login.clickCreateNew();
		ig.enterPipelineName(IGPipelineName);
		ig.selectLakehouseName("silver");
		ig.selectDataLakePath("Bronze/test");
		//ig.selectDatalkeName("BDS_SAMPLE_DATA");
		ig.selectSource("sql_server_pershing");
		ig.selectSchema("dbo");
		ig.clickCheckBox("ACCT_ACCF_4");
		ig.selectLoadType("ACCT_ACCF_4", "Incremental Load");
		ig.selectLoadTypeColumn("ACCT_ACCF_4", "IBDNumber");
		ig.selectLoadTypeCondition("ACCT_ACCF_4", "Equal To");
		ig.selectLoadTypeIncValue("ACCT_ACCF_4", "1");
		ig.columnSelector("ACCT_ACCF_4");
		ig.clickColumnSelectorSelectAll();
		ig.clickCustomSelectorCheckBox("TransactionCode");
		ig.clickCustomSelectorCheckBox("RecordIndicatorValue");
		ig.clickCustomSelectorCheckBox("RecordIDSequenceNumber");
		ig.clickCustomSelectorCheckBox("AccountNumber");
		ig.clickCustomSelectorCheckBox("IBDNumber");
		login.clickSaveButton();
		ig.clickSpanText("Save Pipeline");
		ig.isObjectExist(ig.pipelineCreatedMsg);
		login.enterInSearchbox(IGPipelineName);
		login.clickTrigger();
		login.clickSpanText("Yes");
		login.isObjectExist(ig.pipelineTriggerMsg);
		login.enterInSearchbox("Insigneo_Ingestion_Automation_Pipeline");
		login.clickEdit();
		login.isHeaderPresent("span", "Edit");
		login.clickSpanText("Update Pipeline");
		ig.isObjectExist(ig.pipelineupdatedMsg);
		login.clickSideNavigationModule("Monitoring");
		ig.waitForIngestionPipelineExecution(IGPipelineName);
		//login.clickSideNavigationModule("Ingestion Pipeline");
		//login.enterInSearchbox(IGPipelineName);
		//login.clickDelete();
		//login.clickYesButton();
		//login.isObjectExist(ig.deletePipelineMsg);
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
		login.clickSpanText("Upload Excel");
		login.isDialogHeaderPresent("span", "Upload Excel File");
		login.clickSpanText("Click to Upload Excel File");
		login.uploadFile("C:\\Users\\anirudh.shekhawat\\Downloads\\DataModel\\column_template.xlsx");
		login.clickSpanText("Upload");
		login.isObjectExist(model.fileUploadedMsg);
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
		login.clickSideNavigationModule("Transformation Pipeline");
		login.isHeaderPresent("h5", "Transformation Pipeline");
		login.clickSpanText("Create New");
		login.isDialogHeaderPresent("div", "Create New Transformation Pipeline");
		v2.enterPipelineName(pipelineName);
		v2.enterPipelineDescription("Automation Testing");
		login.clickSpanText("Create & Proceed");
		//v2.openTransformationPannel();
		v2.selectSourceDataLake();
		login.isDialogHeaderPresent("div", "Configure Delta Lake Source");
		login.standardDropdownElement("Select Lakehouse", "silver");
		login.standardDropdownElement("Select Lakehouse Type", "Files");
		Thread.sleep(3000);
		login.typeAheadWebElement("Select Directory Path", "TestMDM/mdm_development_test_1k/processed");
		login.standardDropdownElement("Select File Type (to filter files)", "Parquet");
		login.typeAheadWebElement("Source File", "2025/09/30/mdm_development_test_1k_2025_09_30_10_30_38.parquet");
		login.clickSpanText("Proceed");
		Thread.sleep(5000);
		//v2.getPipelineNode("customertestscd2");
		//login.waitForPageLoad(v2.getPipelineNode("customertestscd2"));
		v2.clickZoomOutButton();
		v2.clickZoomOutButton();
		v2.tranfomrationInputSearchbox("Drop duplicates");
		v2.drangNDropInputType();
		v2.clickZoomOutButton();
		v2.clickZoomOutButton();
		v2.connectSourceTarget("2025/09/30/mdm_development_test_1k_2025_09_30_10_30_38.parquet", "drop_duplicates");
		login.doubleClick(v2.getPipelineNode("drop_duplicates"), "Drop Duplicates");
		login.isDialogHeaderPresent("h2", "Drop Duplicates Configuration");
		login.clickSelectAllCheckbox();
		login.clickSpanText("Apply Configuration");
		login.isObjectExist(v2.removeDuplicateMsg);
		v2.dialogCloseButton("h2","Drop Duplicates Configuration");
		//v2.selectSourceCustomScript();
		//login.isObjectExist(v2.pipelineNode);
		v2.addTargetDataLake();
		login.isDialogHeaderPresent("div", "Select Target Type");
		v2.clicksourceSelectionModel();
		login.isDialogHeaderPresent("div", "Configure Delta Lake Target");
		login.standardDropdownElement("Select Lakehouse", "silver");
		login.standardDropdownElement("Select Lakehouse Type", "Tables");
		Thread.sleep(3000);
		login.typeAheadWebElement("Target Table", "automation_test_transform");
		login.clickSpanText("Proceed");
		login.waitForPageLoad(v2.getPipelineNode("Target_Operation - automation_test_transform"));
		v2.connectSourceTarget("drop_duplicates", "Target_Operation - automation_test_transform");
		v2.clickSpanText("Save");
		login.isObjectExist(v2.pipelinesaved);
		login.doubleClick(v2.getPipelineNode("Target_Operation - automation_test_transform"), "Target_Operation - automation_test_transform");
		login.isDialogHeaderPresent("h2", "Target Configuration: Target_Operation");
		login.textClick("div", "Column Mapping");
		login.clickSpanText("Auto Map");
		//v2.selectTransformationOutput("incremental_id");
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
		v2.searchbox("Automation_Test_Transformation");
		v2.clickTrigger();
		login.isObjectExist(v2.piplineTriggeredMsg);
		login.clickSideNavigationModule("Monitoring");
		login.textClick("div", "Transformation And Data Quality");
		Thread.sleep(5000);
		v2.waitForTransfomationPipelineExecution(pipelineName);
		v2.waitForTransfomationPipelineExecution("Automation_Test_Transformation");
		//login.clickSideNavigationModule("Ingestion Pipeline");
		//login.isHeaderPresent("h5", "Transformation Pipeline v2");
		//login.clickSideNavCollapse();
		//login.enterInSearchbox(pipelineName);
		//login.clickDelete();
		//login.clickYesButton();
		//login.isObjectExist(ig.deletePipelineMsg);
		//login.clickSideNavCollapse();
	}@Test(priority=11)
	public void dataNexusOrchestrator() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("DataNexusOrchestrator");
		login.clickSideNavigationModule("Orchestrator");
		login.isHeaderPresent("h5","Process Scheduler");
		orch = new DataNexusOrchestrator(getDriver());
		OrchestratorName = orch.orchestratorName;
		orch.clickCreateNewProcess();
		orch.enterPipelineName(OrchestratorName);
		orch.enterPipelineDescription("Automation Testing");
		orch.clickAddPipelineButton();
		orch.selectPipelineType("Ingestion");
		orch.selectPipeline(IGPipelineName);
		orch.clickSpanText("Done");
		orch.clickAddPipelineButton();
		orch.selectPipelineType("MDM Pipelines");
		orch.standardDropdownElement("Select MDM Workspace", "Performance_Management_MDM");
		orch.selectMDMFIleType("Import");
		orch.standardDropdownElement("Select MDM Pipeline", "sample_test_pipeline");
		orch.selectPipelineDependentUpon(IGPipelineName);
		orch.clickSpanText("Done");
		orch.clickAddPipelineButton();
		orch.selectPipelineType("Ingestion");
		//orch.standardDropdownElement("Select MDM Workspace", "MDM");
		orch.selectPipeline("Automation_test1k_mdm_Ingestion");
		orch.selectPipelineDependentUpon("sample_test_pipeline");
		orch.clickSpanText("Done");
		orch.clickAddPipelineButton();
		orch.selectPipelineType("Transformation");
		orch.selectPipeline("Automation_Test_Transformation");
		orch.selectPipelineDependentUpon("Automation_test1k_mdm_Ingestion");
		orch.clickSpanText("Done");
		orch.clickSpanText("Save");
		login.isObjectExist(orch.scheduleCreatedMsg);
		login.enterInSearchbox(OrchestratorName);
		login.clickTrigger();
		login.clickYesButton();
		login.isObjectExist(orch.pipelineTriggeredMessage);
		login.clickViewLogsButton();
		login.isHeaderPresent("span", "Process Scheduler Logs");
		//login.enterInSearchbox("Manual");
		//orch.clickProcessId();
		orch.waitForProcessCompleted();
		orch.clickProcessId();
		orch.captureScreenshot();
		//login.getPipelineStatus();
		login.clickSideNavigationModule("Ingestion Pipeline");
	    login.enterInSearchbox(IGPipelineName);
		login.clickDelete();
		login.clickSpanText("Yes");
		login.isObjectExist(ig.deletePipelineMsg);
		login.clickSideNavigationModule("Transformation Pipeline");
		login.isHeaderPresent("h5", "Transformation Pipeline");
		login.clickSideNavCollapse();
		v2.searchbox(pipelineName);
		login.clickDelete();
		login.clickYesButton();
		login.isObjectExist(v2.piplineDeletedMsg);
		login.clickSideNavCollapse();
		login.clickSideNavigationModule("Orchestrator");
		login.isHeaderPresent("h5","Process Scheduler");
		login.enterInSearchbox(OrchestratorName);
		login.clickDelete();
		login.clickYesButton();
		login.isObjectExist(orch.pipelineDeletedMessage);
		//login.nonLinkText("New Process");
	}@Test(priority=12)
	public void dataNexusDocumentParser() throws InterruptedException,UnexpectedTagNameException, IOException{
		checkRunFlagSanity("DataNexusDocumentParser");
		login.clickSideNavigationModule("Document Parser");
		login.isHeaderPresent("h5", "Document Parser");
		DataNexusDocumentParser parse = new DataNexusDocumentParser(getDriver());
		String processName = parse.pname;
		String updatedProcessName = parse.pname+"1";
		parse.clickAddProcess();
		login.isDialogHeaderPresent("span", "New Process");
		parse.enterProcessName(processName);
		parse.chooseLakehouse("bronze");
		parse.choosedefinePath("OCR/");
		Thread.sleep(2000);
		parse.chooseTargetPath("bronze/test/");
		parse.textClick("button", "Save");
		login.isObjectExist(parse.createProcessMsg);
		parse.enterInSearchbox(processName);
		login.clickEdit();
		login.isDialogHeaderPresent("span", "Edit Process");
		parse.enterProcessName(updatedProcessName);
		parse.textClick("button", "Update");
		login.isObjectExist(parse.updateProcessMsg);
		parse.enterInSearchbox(updatedProcessName);
		login.clickViewButton();
		login.isDialogHeaderPresent("span", "View Process");
		parse.textClick("button", "Close");
		parse.enterInSearchbox(updatedProcessName);
		login.clickDelete();
		login.clickYesButton();
		login.isObjectExist(parse.deleteProcessMsg);
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
		login.clickViewLogsButton();
		//login.isObjectExist(login.logsIcon);
	}@Test(priority=14)
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
		login.clickSideNavigationModule("Ingestion Pipeline");
		login.clickSideNavigationModule("Admin");
		login.clickSideNavigationModule("User Management");
		UserManagement usermanage = new UserManagement(getDriver());
		login.isHeaderPresent("h5","User Management");
		//login.textClick("a", "User Management");
		String roleName=usermanage.firstRoleName.getText();
		login.enterInSearchbox(roleName);
		login.textClick("p", roleName);
		login.textClick("a", "User Management");
		login.enterInSearchbox(roleName);
		login.clickEdit();
		login.textClick("a", "User Management");
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
		ws.searchBox("Performance_Manage");
		Thread.sleep(1000);
		ws.clickWorkSpace("Performance_Manag...");
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
		entities.clickEntityCard();
		entities.clickEntityCard();
		entities.clickEntityCard();
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
		/*login.enterInSearchbox("Automation_Test_Import");
		overview.clickViewTableButton();
		login.isHeaderPresent("span","Automation_Test_Import Report");
		login.clickSpanText("Dashboard");*/
		login.clickSpanText("Dashboard");
		overview.clickincompleteRecordsViewDetail();
		login.isHeaderPresent("span", "Incomplete Record Table");
		/*login.enterInSearchbox("Automation_Test_Import");
		overview.clickViewTableButton();
		login.isHeaderPresent("span", "Automation_Test_Import");
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
		
	}@Test(priority = 24)
	public void logout() throws IOException {
		checkRunFlagSanity("Logout");
		login.topNavigationSection("Log out");
		login.isObjectExist(login.logoutUserMsg);
		login.isObjectExist(login.username_textbox,"Username Textbox");
	}
}
