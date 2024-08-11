package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
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

public class testCase_01 {
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
    
    @Test(description = "Verify User Registration- Login-Logout", groups = "Login Flow", dataProvider = "testData", dataProviderClass = DP.class, priority = 1)
    public static void TestCase01(String email, String password) throws InterruptedException, IOException {
        test=reportSingleton.startReporting("Verify User Registration- Login-Logout");
        String url="https://qtripdynamic-qa-frontend.vercel.app/";
        driver.get(url);

        HomePage hp=new HomePage(driver);
        RegisterPage rp=new RegisterPage(driver);
        LoginPage lp=new LoginPage(driver);

        hp.navigateToRegistrationPage();
        if(driver.getCurrentUrl().endsWith("register/")) {
            reportSingleton.logPass("Registration page is displayed");
        }
        else {
            reportSingleton.logFail(driver, "Failed to display registration page!!!", testCaseName);
        }
        rp.registerNewUser(email, password , password, true);
        Thread.sleep(6000);
        
        if(driver.getCurrentUrl().endsWith("login")) {
            reportSingleton.logPass("Login page is displayed");
        }
        else {
            reportSingleton.logFail(driver, "Failed to display login page!!!", testCaseName);
        }
        lp.performLogin(rp.lastGeneratedUsername, password);
        Thread.sleep(5000);
        if(hp.isUserLoggedIn()){
            reportSingleton.logPass("Successfully logged in with the registered user");
        }
        else {
            reportSingleton.logFail(driver, "Failed to log in with the registered user!!!", testCaseName);
        }
        
        hp.logOutUser();
        Thread.sleep(3000);
        if(driver.getCurrentUrl().equals("https://qtripdynamic-qa-frontend.vercel.app/")) {
            reportSingleton.logPass("Successfully verified that the user is logged out");
        }
        else {
            reportSingleton.logFail(driver, "The user failed to log out!!!", testCaseName);
        }
        
        test.addScreenCaptureFromPath(reportSingleton.getScreenshotAsFile(driver, testCaseName));
    }
    
}
