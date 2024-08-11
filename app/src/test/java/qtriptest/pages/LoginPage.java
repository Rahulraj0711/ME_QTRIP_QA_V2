package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class LoginPage {
    RemoteWebDriver driver;

    @FindBy(id = "floatingInput")
    static WebElement emailElement;
    @FindBy(name = "password")
    static WebElement passwordElement;
    @FindBy(xpath = "//button[contains(text(), 'Login')]")
    static WebElement loginButton;

    public LoginPage(RemoteWebDriver driver) {
        this.driver=driver;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    public void performLogin(String email, String password) {
        SeleniumWrapper.sendKeys(emailElement, email);
        SeleniumWrapper.sendKeys(passwordElement, password);
        SeleniumWrapper.click(loginButton, driver);
    }
}
