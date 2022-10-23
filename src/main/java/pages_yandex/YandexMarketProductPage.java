package pages_yandex;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class YandexMarketProductPage extends YandexMarket{

    private String titleNameOfProduct = "//h1[@data-tid = '723082e']";

    private String priceOfProduct = "(//span[@data-auto = 'mainPrice'])[1]";

    public YandexMarketProductPage(WebDriver driver) {
        super(driver);
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
    }

    public boolean checkNameTitleContains(WebDriver driver, String contains) {
        WebElement title = driver.findElement(By.xpath(titleNameOfProduct));

        return title.getText().contains(contains);
    }

    public boolean checkPriceIsHigherThan(WebDriver driver, int price) {
        //Метод костыльный, так как не удаётся получить значение из getText
        String strPrice = driver.findElement(By.xpath(priceOfProduct)).getAttribute("data-autotest-value");
        int priceOnPage = Integer.parseInt(strPrice + "000");
        return priceOnPage > price;
    }

}
