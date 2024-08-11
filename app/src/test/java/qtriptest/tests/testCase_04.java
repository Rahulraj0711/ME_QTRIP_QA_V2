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
import java.util.ArrayList;
import java.util.List;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class testCase_04 {
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

    @Test(description = "Verify if the website is reliable or not", groups = "Reliability Flow", dataProvider = "testData", dataProviderClass = DP.class, priority = 4)
    public static void TestCase04(String userName, String password, String dataset1, String dataset2, String dataset3) throws InterruptedException, IOException {
        test=reportSingleton.startReporting("Verify if the application is reliable or not");
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
            List<String> detailsList=new ArrayList<String>();
            detailsList.add(dataset1);
            detailsList.add(dataset2);
            detailsList.add(dataset3);
            for(int i=0;i<detailsList.size();i++) {
                String[] details=detailsList.get(i).split(";");
                String city=details[0];
                String adventure=details[1];
                String guestName=details[2];
                String date=details[3];
                String personCount=details[4];
                homePage.searchCity(city);
                homePage.autocompleteCity(city);
                homePage.selectCity();
                ap.selectAdventure(adventure);
                adp.bookAdventure(guestName, date, personCount);
                if(adp.isBookingSuccessful()){
                    reportSingleton.logPass("Booking "+(i+1)+" Successful");
                }
                else {
                    reportSingleton.logFail(driver, "Booking "+(i+1)+" Failed!!!", testCaseName);
                }
                adp.navigateToHomePage();
            }
            adp.navigateToReservationsPage();
            // driver.navigate().refresh();
            Thread.sleep(5000);
            int reservationsCount=hp.getReservations();
            if(reservationsCount==3) {
                reportSingleton.logPass("Expected no. of reservations are present.");
            }
            else {
                reportSingleton.logFail(driver, "Unexpected no. of reservations!!!", testCaseName);
            }
            hp.logout();
        }

        test.addScreenCaptureFromPath(reportSingleton.getScreenshotAsFile(driver, testCaseName));
    }
    
    @AfterSuite
    public static void quitDriver() throws InterruptedException, IOException {
        ReportSingleton.getReportsInstance().endReporting();
        driver.quit();
    }
}
