package Utilities;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class AllureReportManager implements ITestListener {

    private String resultsDir;
    private String reportDir;

    @Override
    public void onStart(ITestContext context) {
        String repNam = "MyReport_" + System.currentTimeMillis();  // Customize report folder name here
        resultsDir = System.getProperty("user.dir") + "\\reports\\" + repNam;
        reportDir = System.getProperty("user.dir") + "\\reports\\" + repNam + "_html";

        // Set Allure results directory dynamically
        System.setProperty("allure.results.directory", resultsDir);

        System.out.println("==== Test Suite Started ====");
        System.out.println("Allure results directory set to: " + resultsDir);
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("== Test Started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logInfo(result.getName() + " passed successfully.");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logInfo(result.getName() + " failed.");
        logInfo("Failure reason: " + result.getThrowable().getMessage());

        WebDriver driver = (WebDriver) result.getTestContext().getAttribute("WebDriver");
        if (driver != null) {
            saveScreenshot(driver);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logInfo(result.getName() + " was skipped.");
        if (result.getThrowable() != null) {
            logInfo("Skip reason: " + result.getThrowable().getMessage());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("==== Test Suite Finished ====");
        generateAndOpenReport();
    }

    @Step("{0}")
    public void logInfo(String message) {
        System.out.println("[INFO] " + message);
    }

    @Attachment(value = "Screenshot on Failure", type = "image/png")
    public byte[] saveScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    private void generateAndOpenReport() {
        try {
            System.out.println("Generating Allure HTML report...");

            // Run allure generate <resultsDir> --clean -o <reportDir>
            ProcessBuilder pb = new ProcessBuilder("allure", "generate", resultsDir, "--clean", "-o", reportDir);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor();

            System.out.println("Allure report generated at: " + reportDir);

            // Open the report folder or index.html in default browser
            openReport(reportDir);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void openReport(String reportPath) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();

        String indexPath = reportPath + "\\index.html";

        if (os.contains("win")) {
            // Windows
            runtime.exec("cmd /c start " + indexPath);
        } else if (os.contains("mac")) {
            // macOS
            runtime.exec("open " + indexPath);
        } else if (os.contains("nix") || os.contains("nux")) {
            // Linux
            runtime.exec("xdg-open " + indexPath);
        } else {
            System.out.println("Automatic opening of report not supported for this OS: " + os);
        }
    }
}
