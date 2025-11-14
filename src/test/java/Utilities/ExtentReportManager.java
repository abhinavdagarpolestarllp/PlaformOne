package Utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.baseClass;

public class ExtentReportManager implements ITestListener {
    public ExtentSparkReporter sparkReporter;
    public static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static String runFolderPath;
    public static String reportFolderPath;
    public static String screenshotFolderPath;
    public static String timeStamp;
    public static String repName;
    public static String filesFolderPath;

    @Override
    public void onStart(ITestContext testContext) {
        timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        // Base path
        String parentFolder = System.getProperty("user.dir") + File.separator + "Run Results";
        runFolderPath = parentFolder + File.separator + "Run-" + timeStamp;

        // Define subfolders
        reportFolderPath = runFolderPath + File.separator + "Report";
        screenshotFolderPath = runFolderPath + File.separator + "Screenshot";
        filesFolderPath=runFolderPath + File.separator + "Files";

        try {
            Files.createDirectories(Paths.get(reportFolderPath));
            Files.createDirectories(Paths.get(screenshotFolderPath));
            Files.createDirectories(Paths.get(filesFolderPath));
        } catch (IOException e) {
            System.err.println("⚠️ Failed to create directories: " + e.getMessage());
        }

        repName = "Test-Report-" + timeStamp + ".html";
        String reportFile = reportFolderPath + File.separator + repName;
        //String filesFolder= filesFolderPath +File.separator;
        sparkReporter = new ExtentSparkReporter(reportFile);
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Functional Test Execution");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "OnePlatform");
        extent.setSystemInfo("Environment", "UAT");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));

        try {
            FileReader file = new FileReader("./src/test/resources/config.properties");
            Properties p = new Properties();
            p.load(file);
            String br = p.getProperty("browser") != null ? p.getProperty("browser") : "Unknown";
            extent.setSystemInfo("Browser", br);
        } catch (Exception e) {
            System.out.println("⚠️ Could not read config.properties: " + e.getMessage());
        }

       /* System.out.println("===== Extent Report Initialized =====");
        System.out.println("Run Folder        : " + runFolderPath);
        System.out.println("Report Folder     : " + reportFolderPath);
        System.out.println("Screenshot Folder : " + screenshotFolderPath);
        System.out.println("Report HTML       : " + reportFile);*/
    }

    @Override
    public void onTestStart(ITestResult result) {
        String description = result.getMethod().getDescription();
        if (description == null || description.isEmpty()) description = result.getMethod().getMethodName();
        ExtentTest test = extent.createTest(description);
        test.assignCategory(result.getTestClass().getName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, result.getName() + " passed successfully.");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().log(Status.FAIL, result.getName() + " failed.");
        if (result.getThrowable() != null)
            extentTest.get().log(Status.INFO, result.getThrowable().toString());

        try {
            WebDriver driver = (WebDriver) result.getTestContext().getAttribute("WebDriver");
            if (driver == null) driver = baseClass.getDriver();

            if (driver != null) {
                String imgPath = baseClass.captureScreen(driver, "Failed_" + result.getName());
                if (imgPath != null)
                    extentTest.get().addScreenCaptureFromPath(imgPath);
            } else {
                extentTest.get().log(Status.WARNING, "⚠️ WebDriver instance not found. Screenshot not captured.");
            }
        } catch (Exception e) {
            extentTest.get().log(Status.WARNING, "⚠️ Screenshot capture failed: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, result.getName() + " was skipped.");
    }

    @Override
    public void onFinish(ITestContext testContext) {
        extent.flush();

        try {
            zipRunFolder(runFolderPath);
        } catch (Exception e) {
            System.err.println("⚠️ Zip failed: " + e.getMessage());
        }

        // IMPORTANT: Remove Desktop.getDesktop().browse(...)
        // Never attempt to open desktop GUI on Jenkins
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    private void zipRunFolder(String sourceDir) throws IOException {
        Path zipFilePath = Paths.get(sourceDir + ".zip");
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(zipFilePath))) {
            Path pp = Paths.get(sourceDir);
            Files.walk(pp)
                .filter(path -> !Files.isDirectory(path))
                .forEach(path -> {
                    ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
                    try {
                        zs.putNextEntry(zipEntry);
                        Files.copy(path, zs);
                        zs.closeEntry();
                    } catch (IOException e) {
                        System.err.println("Zip error: " + e.getMessage());
                    }
                });
        }
    }
}
