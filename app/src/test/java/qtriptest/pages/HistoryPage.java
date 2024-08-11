
package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class HistoryPage {
    RemoteWebDriver driver;

    @FindBy(css = "#reservation-table")
    WebElement reservationBody; 
    @FindBy(css = ".cancel-button")
    WebElement cancelButton;
    @FindBy(xpath = "//div[text()='Logout']")
    WebElement logoutButton;

    public HistoryPage(RemoteWebDriver driver) {
        this.driver=driver;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    public int getReservations() {
        List<WebElement> reservations=reservationBody.findElements(By.tagName("tr"));
        // ReservationDetails rd=null;
        // for(int i=0;i<reservations.size();i++) {
        //     String transactionID=driver.findElement(By.cssSelector("th")).getText();
        //     String bookingName=driver.findElement(By.cssSelector("td:nth-of-type(1)")).getText();
        //     String adventure=driver.findElement(By.cssSelector("td:nth-of-type(2)")).getText();
        //     String persons=driver.findElement(By.cssSelector("td:nth-of-type(3)")).getText();
        //     String date=driver.findElement(By.cssSelector("td:nth-of-type(4)")).getText();
        //     String price=driver.findElement(By.cssSelector("td:nth-of-type(5)")).getText();
        //     String bookingTime=driver.findElement(By.cssSelector("td:nth-of-type(6)")).getText();
        //     rd=new ReservationDetails(transactionID, bookingName, adventure, persons, date, price, bookingTime);
        // }
        return reservations.size();
    }

    public String getTransactionID() {
        String transactionID=reservationBody.findElement(By.cssSelector(" th")).getText();
        return transactionID;
    }

    public void cancelReservation() throws InterruptedException {
        SeleniumWrapper.click(cancelButton, driver);
        Thread.sleep(3000);
    }

    public boolean isCancellationSuccessful() {
        String message=SeleniumWrapper.findElementWithRetry(driver, By.id("no-reservation-banner"), 1).getText();
        if(message.contains("Oops!"))
            return true;
        return false;
    }

    public void logout() throws InterruptedException {
        SeleniumWrapper.click(logoutButton, driver);
        Thread.sleep(3000);
    }

    // public class ReservationDetails {
    //     private String transactionID;
    //     private String bookingName;
    //     private String adventure;
    //     private String persons;
    //     private String date;
    //     private String price;
    //     private String bookingTime;

    //     public ReservationDetails(String transactionID, String bookingName, String adventure, String persons, String date, String price, String bookingTime) {
    //         this.transactionID=transactionID;
    //         this.bookingName=bookingName;
    //         this.adventure=adventure;
    //         this.persons=persons;
    //         this.date=date;
    //         this.price=price;
    //         this.bookingTime=bookingTime;
    //     }

    //     public String getTransactionID() {
    //         return this.transactionID;
    //     }
    //     public String getBookingName() {
    //         return this.bookingName;
    //     }
    //     public String getAdventure() {
    //         return this.adventure;
    //     }
    //     public String getPersons() {
    //         return this.persons;
    //     }
    //     public String getDate() {
    //         return this.date;
    //     }
    //     public String getPrice() {
    //         return this.price;
    //     }
    //     public String getBookingTime() {
    //         return this.bookingTime;
    //     }
    // }
}