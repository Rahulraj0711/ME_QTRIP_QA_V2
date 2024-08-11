package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class HomePage {
    RemoteWebDriver driver;

    @FindBy(xpath = "//a[contains(text(),'Register')]")
    WebElement registerLink;
    @FindBy(css = "ul.navbar-nav div.login")
    WebElement logoutButton;
    @FindBy(xpath = "//input[contains(@placeholder, 'Search')]")
    WebElement searchBox;
    WebElement availableCity;

    public HomePage(RemoteWebDriver driver) {
        this.driver=driver;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    public void navigateToRegistrationPage() {
        SeleniumWrapper.click(registerLink, driver);
    }

    public boolean isUserLoggedIn() {
        return logoutButton.isDisplayed();
    }

    public void logOutUser() {
        SeleniumWrapper.click(logoutButton, driver);
    }

    public void searchCity(String city) throws InterruptedException {
        searchBox.clear();
        for(int i=0;i<city.length();i++) {
            searchBox.sendKeys(city.charAt(i)+"");
            Thread.sleep(500);
        }
    }

    public boolean noCityFoundText() {
        WebElement noCityFound=SeleniumWrapper.findElementWithRetry(driver, By.cssSelector("#results h5"), 2);
        if(noCityFound.getText().equals("No City found")) {
            return true;
        }
        return false;
    }

    public boolean autocompleteCity(String cityName) {
        availableCity=SeleniumWrapper.findElementWithRetry(driver, By.cssSelector("#results a"), 2);
        String cityText=availableCity.getText();
        if(cityText.contains(cityName)) {
            return true;
        }
        else {
            return false;
        }
    }

    public void selectCity() throws InterruptedException {
        SeleniumWrapper.click(availableCity, driver);
        Thread.sleep(3000);
    }
}
