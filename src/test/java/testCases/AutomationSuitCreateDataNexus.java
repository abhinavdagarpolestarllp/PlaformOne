package testCases;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.DataNexusConnection;
import pageObjects.DataNexusOrchestrator;
import pageObjects.UserManagement;
import pageObjects.landingPage;
import pageObjects.loginPage;
import pageObjects.workspaceList;
import testBase.baseClass;

public class AutomationSuitCreateDataNexus  extends baseClass {
	loginPage login;// Declare here
	landingPage landing ;
	workspaceList wslist;
	workspaceList ws;
	DataNexusConnection conn =new DataNexusConnection(getDriver()) ;
	DataNexusOrchestrator orch=new DataNexusOrchestrator(getDriver());
	final String IngestionPipelineName ="TestingIgPipeline2025.08.04.11.49.52";
	final String TranformationPipelineName="Testing118291";
	final String OrchestratorName = orch.orchestratorName;
	final String ConnectionName=conn.connectionname;
	@BeforeMethod
	public void setupTestData() {
		login = new loginPage(getDriver()); // initialize after driver ready
		landing= new landingPage(getDriver());
		wslist = new workspaceList(getDriver());
		conn = new DataNexusConnection(getDriver());
		orch = new DataNexusOrchestrator(getDriver());
	}@Test(priority = 9)
	public void createNewRoleAndUser() throws InterruptedException, IOException {
		login.clickSideNavigationModule("Admin");
		login.clickSideNavigationModule("User Management");
		UserManagement user = new UserManagement(getDriver());
		login.isHeaderPresent("h5","User Management");
		login.clickSpanText("New Role");
		user.enterRoleName("Automation_Test_Role");
		user.enterRoleDescription("Role created for testing puropse");
		user.selectModuleUserPrivileges("Data Model", "All");
		user.selectModuleUserPrivileges("Connections", "Read");
		user.selectModuleUserPrivileges("Connections", "Create");
		user.selectModuleUserPrivileges("Home", "All");
		login.clickSpanText("Save Role");
		login.isObjectExist(user.roleCreatedMsg);
		login.textClick("div","Users");
		login.clickSpanText("New User");	
		user.enterFirstName("Test");
		user.enterLastName("User");
		user.enterEmail("testuser@polestartllp.com");
		user.enterPassword("TestUser@1234");
		user.enterconfirmPassword("TestUser@1234");
		login.clickCheckBox("Test123");
		login.clickSpanText("Save User");
	}@Test(priority=1)
	public void loginUser() throws IOException, InterruptedException {
		login = new loginPage(getDriver());
		FileReader file = new FileReader("./src//test//resources//config.properties");
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
		landingPage landing = new landingPage(getDriver());
		login.isObjectExist(landing.dataSolutions);
		landing.dataNexusClick();
	}
	@Test(priority=3)
	public void workspaceListDataNexus() throws InterruptedException,UnexpectedTagNameException, IOException{
		ws = new workspaceList(getDriver());
		login.isObjectExist(ws.onePlatformLogoWorkspaceListPage);
		login.isHeaderPresent("h1", "Workspaces");
		ws.clickDataNexus();
		ws.searchBox("Automation_Testing");
		Thread.sleep(1000);
		ws.clickWorkSpace("Automation_Testing");
	}@Test(priority=4)
	public void createConnection() throws InterruptedException, IOException {
		login.clickCreateNew();
		//conn.clickConnectionType("Database");
		login.isHeaderPresent("span","Connections");
		//conn.setConnectionName(conn.connectionname);
		conn.clickFirstConnection();
		login.clickReset();
		login.isTextPresentAt(conn.connectionNameTextbox, "");
		conn.newConnectionSearchbox("ms server");
		conn.setConnectionName("Test_Connection");
		conn.clickFirstConnection();
		login.clickNext();
		conn.setDatabase("AdventureWorks2022");
		conn.setPassword("sql@1234");
		conn.setUserName("sqladmin");
		conn.setServer("psslp-genai.database.windows.net");
		conn.setPort("1433");
		login.testConnection();
		login.isObjectExist(conn.connectionTestSuccessMsg);
		login.clickSubmitButton();
		login.isObjectExist(conn.connectionAddedMsg);
	}@Test(priority=5)
	public void createIngestionPipeline() {
		
	}

}
