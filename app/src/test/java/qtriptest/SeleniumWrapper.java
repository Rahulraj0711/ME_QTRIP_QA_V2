package qtriptest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumWrapper {
    public static boolean click(WebElement elementToClick, WebDriver driver) {
        try {
            if(elementToClick.isDisplayed()) {
                JavascriptExecutor js=(JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView();", elementToClick);
                elementToClick.click();
            }
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public static boolean sendKeys(WebElement inputBox, String keysToSend) {
        try {
            inputBox.clear();
            inputBox.sendKeys(keysToSend);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public static boolean navigate(WebDriver driver, String url) {
        try {
            if(!driver.getCurrentUrl().equals(url)) {
                driver.get(url);
            }
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public static WebElement findElementWithRetry(WebDriver driver, By by, int retryCount) {
        WebElement element=null;
        try {
            element=driver.findElement(by);
            return element;
        } catch(Exception e) {
            for(int i=0;i<retryCount;i++) {
                element=driver.findElement(by);
            }
            return element;
        }
    }
}
