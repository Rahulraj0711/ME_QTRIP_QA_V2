package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;
import java.io.IOException;
import java.lang.reflect.Method;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class testCase_02 {
    static RemoteWebDriver driver;
    static ReportSingleton reportSingleton;
    static ExtentReports reports;
    static ExtentTest test;
    static String testCaseName;
    
    @BeforeClass(alwaysRun = true, enabled = true)
	public static void createDriver() throws IOException {
		DriverSingleton driverSingleton=DriverSingleton.getInstanceOfBrowserClass();
        driver=driverSingleton.getWebDriver();
        reportSingleton=ReportSingleton.getReportsInstance();
        reports=reportSingleton.getReports();
	}

    @BeforeMethod
    public static void getMethodName(Method method) {
        testCaseName=method.getName();
    }

    @Test(description = "Verify that Search and Filters work fine", groups = "Search and Filter flow", dataProvider = "testData", dataProviderClass = DP.class, priority = 2)
    public static void TestCase02(String cityName, String categoryFilter, String durationFilter, String expectedFilteredResults, String expectedUnfilteredResults) throws InterruptedException, IOException {
        test=reportSingleton.startReporting("Verify that Search and Filters work fine");
        String url="https://qtripdynamic-qa-frontend.vercel.app/";
        driver.get(url);

        HomePage hp=new HomePage(driver);
        AdventurePage ap=new AdventurePage(driver);

        // hp.searchCity("nocityPune");
        // Assert.assertTrue(hp.noCityFoundText());

        hp.searchCity(cityName);
        if(hp.autocompleteCity(cityName)) {
            reportSingleton.logPass("Found the city");
        }
        else {
            reportSingleton.logFail(driver, "Failed to find the city!!!", testCaseName);
        }
        hp.selectCity();
        
        ap.setFilterValue(durationFilter);
        ap.setCategoryValue(categoryFilter);
        if(ap.getResultCount()==Integer.parseInt(expectedFilteredResults)) {
            reportSingleton.logPass("Expected no. of results are present for duration and category filter");
        }
        else {
            reportSingleton.logFail(driver, "Unexpected no. of results for duration and category filter!!!", testCaseName);
        }

        ap.clearAllFilters();
        if(ap.getResultCount()==Integer.parseInt(expectedUnfilteredResults)) {
            reportSingleton.logPass("Expected no. of results are present when no filters applied");
        }
        else {
            reportSingleton.logFail(driver, "Unexpected no. of results for no filters!!!", testCaseName);
        }

        test.addScreenCaptureFromPath(reportSingleton.getScreenshotAsFile(driver, testCaseName));
    }

}
