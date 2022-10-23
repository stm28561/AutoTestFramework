package pages_yandex;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YandexMainPage {

    private String selectorRowWithButtons = "//*[@class='services-new__list']";
    private String selectorForEveryButton = "//*[@class='services-new__list-item']";
    private String selectorNameOfButton = ".//div[@class='services-new__item-title']";
    private String selectorSourceOfButton = "./a[@href]";

    private String selectorMarketButton = "//div[@class='services-new__promo-bubble']";

    private List<WebElement> sourceOfButtons = new ArrayList<>();
    private WebDriver driver;

    private String urlOfThePage = "https://yandex.ru/";

    private List<Map<String, Object>> listOfMapOfButtons = new ArrayList<>();

    WebDriverWait wait;

    public YandexMainPage(WebDriver driver) {
        this.driver = driver;
        driver.get(urlOfThePage);
        wait = new WebDriverWait(driver, 20);

        sourceOfButtons = driver.findElements(By.xpath(selectorRowWithButtons + selectorForEveryButton));
    }


    public WebDriver getDriver() {
        return driver;
    }

    public List<Map<String, Object>> getCollectResult() {
        for(WebElement result : sourceOfButtons)
            listOfMapOfButtons.add(Map.of(
                    "WEB_ELEMENT", result,
                    "NAME_PAGE", result.findElement(By.xpath(selectorNameOfButton)).getText(),
                    "URL", result.findElement(By.xpath(selectorSourceOfButton)).getAttribute("href")
            ));
        return listOfMapOfButtons;
    }

    public boolean chooseButton(String nameButton){
        WebElement pageLink = (WebElement) listOfMapOfButtons.stream()
                .filter(x->x.get("NAME_PAGE").toString().contains(nameButton))
                .findFirst()
                .get().get("WEB_ELEMENT");
        pageLink.findElement(By.xpath(selectorSourceOfButton)).click();
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        for (String tab : tabs){
            driver.switchTo().window(tab);
            if(driver.getTitle().contains(nameButton))
                return true;
        }
        Assertions.assertTrue(false,"Не удалось найти кнопку с именем "+ nameButton);
        return false;
    }
}
