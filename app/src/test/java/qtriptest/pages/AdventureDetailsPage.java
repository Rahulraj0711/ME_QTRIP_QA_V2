package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class AdventureDetailsPage {
    RemoteWebDriver driver;

    @FindBy(name = "name")
    WebElement nameElement;
    @FindBy(name = "date")
    WebElement dateElement;
    @FindBy(name = "person")
    WebElement personCountElement;
    @FindBy(className = "reserve-button")
    WebElement reserveButton;

    public AdventureDetailsPage(RemoteWebDriver driver) {
        this.driver=driver;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    public void bookAdventure(String personName, String date, String personCount) throws InterruptedException {
        SeleniumWrapper.sendKeys(nameElement, personName);
        SeleniumWrapper.sendKeys(dateElement, date);
        SeleniumWrapper.sendKeys(personCountElement, personCount);
        Thread.sleep(2000);
        SeleniumWrapper.click(reserveButton, driver);
        Thread.sleep(5000);
    }

    public boolean isBookingSuccessful() {
        String message=SeleniumWrapper.findElementWithRetry(driver, By.id("reserved-banner"), 2).getText();
        if(message.contains("successful."))
            return true;
        return false;
    }

    public void navigateToReservationsPage() throws InterruptedException {
        WebElement reservationsElement=SeleniumWrapper.findElementWithRetry(driver, By.xpath("//a[text()='Reservations']"), 2);
        SeleniumWrapper.click(reservationsElement, driver);
        Thread.sleep(5000);
    }

    public void navigateToHomePage() throws InterruptedException {
        WebElement homepageElement=SeleniumWrapper.findElementWithRetry(driver, By.xpath("//a[text()='Home']"), 2);
        SeleniumWrapper.click(homepageElement, driver);
        Thread.sleep(5000);
    }
}