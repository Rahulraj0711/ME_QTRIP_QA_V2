package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class RegisterPage {
    RemoteWebDriver driver;
    public String lastGeneratedUsername;

    @FindBy(id = "floatingInput")
    static WebElement emailElement;
    @FindBy(name = "password")
    static WebElement passwordElement;
    @FindBy(name = "confirmpassword")
    static WebElement confirmPasswordElement;
    @FindBy(xpath = "//button[contains(text(), 'Register')]")
    static WebElement registerButton;



    public RegisterPage(RemoteWebDriver driver) {
        this.driver=driver;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    public void registerNewUser(String email, String password, String confirmPassword, boolean generateRandomUsername) {
        String testUserName=email;
        if(generateRandomUsername==true) {
            testUserName=email.split("@")[0]+UUID.randomUUID().toString()+"@gmail.com";
        }
        SeleniumWrapper.sendKeys(emailElement, testUserName);
        SeleniumWrapper.sendKeys(passwordElement, password);
        SeleniumWrapper.sendKeys(confirmPasswordElement, confirmPassword);
        SeleniumWrapper.click(registerButton, driver);
        this.lastGeneratedUsername=testUserName;
    }
}
