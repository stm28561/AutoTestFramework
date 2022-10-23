package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BellBeforeSearch {

    protected WebDriver chromeDriver;

    private WebElement searchField;
    private WebElement searchButton;

    public BellBeforeSearch(WebDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
        searchField = chromeDriver.findElement(By.id("input-search"));
        searchButton = chromeDriver.findElement(By.id("button-search"));
    }

    public void find(String keysFind){
        searchField.click();
        searchField.sendKeys(keysFind);
        searchButton.click();
    }
}
