package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.io.IOException;
import java.lang.reflect.Method;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class testCase_03 {
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

    @Test(description = "Verify that Booking and Cancellation is working fine", groups = "Booking and Cancellation Flow", dataProvider = "testData", dataProviderClass = DP.class, priority = 3)
    public static void TestCase03(String userName, String password, String city, String adventure, String guestName, String date, String personCount) throws InterruptedException, IOException {
        test=reportSingleton.startReporting("Verify that Booking and Cancellation is working fine");
        String url="https://qtripdynamic-qa-frontend.vercel.app/";
        driver.get(url);

        HomePage homePage=new HomePage(driver);
        RegisterPage rp=new RegisterPage(driver);
        LoginPage lp=new LoginPage(driver);
        AdventurePage ap=new AdventurePage(driver);
        AdventureDetailsPage adp=new AdventureDetailsPage(driver);
        HistoryPage hp=new HistoryPage(driver);

        homePage.navigateToRegistrationPage();
        rp.registerNewUser(userName, password, password, true);
        Thread.sleep(6000);
        lp.performLogin(rp.lastGeneratedUsername, password);
        Thread.sleep(5000);
        if(homePage.isUserLoggedIn()) {
            homePage.searchCity(city);
            homePage.autocompleteCity(city);
            homePage.selectCity();
            ap.selectAdventure(adventure);
            adp.bookAdventure(guestName, date, personCount);
            if(adp.isBookingSuccessful()) {
                reportSingleton.logPass("Booking Successful");
            }
            else {
                reportSingleton.logFail(driver, "Booking Failed!!!", testCaseName);
            }
            adp.navigateToReservationsPage();
            if(hp.getReservations()>0) {
                System.out.println("Transaction ID: "+hp.getTransactionID());
                hp.cancelReservation();
            }
            driver.navigate().refresh();  
            if(hp.isCancellationSuccessful()) {
                reportSingleton.logPass("Cancellation Successful");
            }
            else {
                reportSingleton.logFail(driver, "Cancellation failed!!!", testCaseName);
            }
            homePage.logOutUser();
            Thread.sleep(3000);
        }

        test.addScreenCaptureFromPath(reportSingleton.getScreenshotAsFile(driver, testCaseName));
    }
    
}
