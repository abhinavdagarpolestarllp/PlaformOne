package Utilities;
import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
//import org.apache.logging.log4j.LogManager;
import org.testng. ITestContext;
import org.testng. ITestListener;
import org.testng. ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter. ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.baseClass;

public class ExtentReportManager_OLD implements ITestListener {
    public ExtentSparkReporter sparkReporter;
    public static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    String repName;
    public Properties p;

    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";
        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);

        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "OnePlatform");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "UAT");

        try {
            FileReader file = new FileReader("./src/test/resources/config.properties");
            p = new Properties();
            p.load(file);
            String br = p.getProperty("browser").toLowerCase();
            extent.setSystemInfo("Browser", br);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onTestStart(ITestResult result) {
        // Get description from @Test annotation
        String description = result.getMethod().getDescription();
        
        // If description is empty, fallback to method name
        if (description == null || description.isEmpty()) {
            description = result.getMethod().getMethodName();
        }
        
        ExtentTest test = extent.createTest(description);
        extentTest.set(test);
    }

    public void onTestSuccess(ITestResult result) {
        extentTest.get().assignCategory(result.getMethod().getGroups());
        extentTest.get().log(Status.PASS, result.getName() + " got successfully executed");
    }

    public void onTestFailure(ITestResult result) {
        extentTest.get().assignCategory(result.getMethod().getGroups());
        extentTest.get().log(Status.FAIL, result.getName() + " got failed");
        extentTest.get().log(Status.INFO, result.getThrowable().getMessage());

        try {
            WebDriver driver = (WebDriver) result.getTestContext().getAttribute("WebDriver");
            String imgPath = baseClass.captureScreen(driver, result.getName()); // ✅ Now static and safe
            extentTest.get().addScreenCaptureFromPath(imgPath);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void onTestSkipped(ITestResult result) {
        extentTest.get().assignCategory(result.getMethod().getGroups());
        extentTest.get().log(Status.SKIP, result.getName() + " got skipped");
        extentTest.get().log(Status.INFO, result.getThrowable().getMessage());
    }

    public void onFinish(ITestContext testContext) {
        extent.flush();

        String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
        File extentReport = new File(pathOfExtentReport);

        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ✅ Add this to allow access to ExtentTest anywhere
    public static ExtentTest getTest() {
        return extentTest.get();
    }
}
