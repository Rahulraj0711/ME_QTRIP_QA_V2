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
import org.openqa.selenium.support.ui.Select;

public class AdventurePage {
    RemoteWebDriver driver;

    @FindBy(id = "duration-select")
    WebElement durationFilter;
    @FindBy(id = "category-select")
    WebElement categoryFilter;
    @FindBy(id = "search-adventures")
    WebElement adventureSearchBox;
    @FindBy(xpath = "//select[@id='duration-select']//following-sibling::div")
    WebElement filterClear;
    @FindBy(xpath = "//select[@id='category-select']//following-sibling::div")
    WebElement categoryClear;
    @FindBy(xpath = "//input[@id='search-adventures']//following-sibling::div")
    WebElement adventureClear;

    List<WebElement> resultsList;

    public AdventurePage(RemoteWebDriver driver) {
        this.driver=driver;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    public void setFilterValue(String filterValue) throws InterruptedException {
        SeleniumWrapper.click(durationFilter, driver);
        Select filterDropdown=new Select(durationFilter);
        filterDropdown.selectByVisibleText(filterValue);
        Thread.sleep(3000);
    }

    public void setCategoryValue(String categoryValue) throws InterruptedException {
        SeleniumWrapper.click(categoryFilter, driver);
        Select categoryDropdown=new Select(categoryFilter);
        categoryDropdown.selectByVisibleText(categoryValue);
        Thread.sleep(3000);
    }

    public int getResultCount() {
        resultsList=driver.findElements(By.cssSelector("#data a"));
        return resultsList.size();
    }

    public void selectAdventure(String adventure) throws InterruptedException {
        SeleniumWrapper.sendKeys(adventureSearchBox, adventure);
        Thread.sleep(3000);
        getResultCount();
        SeleniumWrapper.click(resultsList.get(0), driver);
        Thread.sleep(3000);
    }

    public void clearAllFilters() throws InterruptedException {
        SeleniumWrapper.click(filterClear, driver);
        SeleniumWrapper.click(categoryClear, driver);
        SeleniumWrapper.click(adventureClear, driver);
        Thread.sleep(2000);
    }
}