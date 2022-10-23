package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class BellAfterSearch extends BellBeforeSearch {

    private List<WebElement> results;

    WebDriverWait wait = new WebDriverWait(chromeDriver, 120);

    public BellAfterSearch(WebDriver chromeDriver) {
        super(chromeDriver);
    }

    public List<WebElement> getResults() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='product-layout product-list col-xs-12']//*[@class='short__desc']")));
        results = chromeDriver.findElements(By.xpath("//*[@class='product-layout product-list col-xs-12']//p"));
        return results;
    }
}
