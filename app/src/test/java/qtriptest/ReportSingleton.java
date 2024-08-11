package qtriptest;

import java.io.File;
import java.io.IOException;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ReportSingleton {
    private static ReportSingleton reportSingleton;
    private ExtentSparkReporter sparkReporter;
    private ExtentReports reports;
    private ExtentTest test;

    private ReportSingleton() throws IOException {
        sparkReporter=new ExtentSparkReporter(System.getProperty("user.dir")+"/ExtentReport.html");
        sparkReporter.loadXMLConfig(new File("extent_customization_configs.xml"));
        reports=new ExtentReports();
        reports.attachReporter(sparkReporter);
    }

    public static ReportSingleton getReportsInstance() throws IOException {
        if(reportSingleton==null) {
            reportSingleton=new ReportSingleton();
        }
        return reportSingleton;
    }

    public ExtentReports getReports() {
        return this.reports;
    }

    public ExtentTest startReporting(String name) {
        test=reports.createTest(name);
        return test;
    }

    public void logPass(String message) {
        test.log(Status.PASS, message);
    }

    public void logFail(RemoteWebDriver driver, String message, String testCaseName) throws IOException {
        test.log(Status.FAIL, message+" with the screenshot: "+test.addScreenCaptureFromPath(getScreenshotAsFile(driver, testCaseName)));
    }

    public void logSkip(String message) {
        test.log(Status.SKIP, message);
    }

    public void endReporting() {
        reports.flush();
    }

    public String getScreenshotAsFile(RemoteWebDriver driver, String screenshotName) throws IOException {
        File screenshotFile=((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String destination=System.getProperty("user.dir")+"/screenshots/"+screenshotName+"_"+System.currentTimeMillis()+".png";
        File destFile=new File(destination);
        FileUtils.copyFile(screenshotFile, destFile);
        return destination;
    }

}