package pages_yandex;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YandexMarket {

    private WebDriver driver;

    private String selectorRowWithTypesOfGoods = "//div[@data-zone-name='menu']";
    private String selectorEveryTypeOfGood = "//div[@data-zone-name='category-link']";
    private String selectorNameOfGood = ".//span";
    private String selectorLinkToGood = ".//a[@href]";

    private String selectorForCatalogButton = "//span[contains(.,'Каталог')]";

    private String selectorForCatalog = "//span[@class = '_3krX4' and contains(.,'toChange')]";

    private String selectorForElectrical = "(//div[@class = '_2jsCq _3DHTH'])";

    private String selectorForSubmenu = "//div[@class = '_1FXJE' and contains(.,'toChange')]";


    private List<WebElement> sourceOfGoods = new ArrayList<>();

    private List<Map<String, Object>> listOfMapOfGoods = new ArrayList<>();

    WebDriverWait wait;

    public YandexMarket(WebDriver driver) {
        this.driver = driver;
        if (!driver.findElements(By.xpath("//div[@data-apiary-widget-name='@MarketNode/DailyBonusesPopup']//div[@role='dialog']//*[@class='_2r9Vv']")).isEmpty()) {
            WebElement cross = driver.findElement(By.xpath("//div[@data-apiary-widget-name='@MarketNode/DailyBonusesPopup']//div[@role='dialog']//*[@class='_2r9Vv']"));
            Actions actions = new Actions(driver);
            actions.click(cross).build().perform();
        }
        sourceOfGoods = driver.findElements(By.xpath(selectorRowWithTypesOfGoods + selectorEveryTypeOfGood));
        wait = new WebDriverWait(driver, 120);
    }

    public List<Map<String, Object>> getCollectResult(List<Map<String, Object>> map, List<WebElement> listToIterate, String nameOf, String linkOf) {
        for(WebElement result : listToIterate)
            map.add(Map.of(
                    "WEB_ELEMENT", result,
                    "NAME_PAGE", result.findElement(By.xpath(nameOf)).getText(),
                    "URL", result.findElement(By.xpath(linkOf)).getAttribute("href")
            ));
        return map;
    }

    public List<Map<String, Object>> getListOfMapOfGoods() {
        getCollectResult(listOfMapOfGoods,sourceOfGoods, selectorNameOfGood, selectorLinkToGood);
        return listOfMapOfGoods;
    }

    protected void choose(String nameOfSearchingElement, List<Map<String, Object>> listOf, String selectorLink){
        WebElement pageLink = (WebElement) listOf.stream()
                .filter(x->x.get("NAME_PAGE").toString().contains(nameOfSearchingElement))
                .findFirst()
                .get().get("WEB_ELEMENT");
        pageLink.click();
    }

    public void chooseGood (String nameButton) {
        choose(nameButton, listOfMapOfGoods, selectorLinkToGood);
    }

    public void waitingTillRefresh() throws InterruptedException {
         Thread.sleep(5000);
    }

    public void clickOnCatalog() {
        WebElement button = driver.findElement(By.xpath(selectorForCatalogButton));
        button.click();
    }

    public void clickOnItemInCatalog(String itemToClickInCatalog){
        String itemToClick = selectorForCatalog.replace("toChange", itemToClickInCatalog);
        WebElement toClick = driver.findElement(By.xpath(itemToClick));
        toClick.click();
    }

    public void clickOnItemInSubmenu(String itemToClickInCatalog){
        String itemToClick = selectorForSubmenu.replace("toChange", itemToClickInCatalog);
        WebElement toClick = driver.findElement(By.xpath(itemToClick));
        toClick.click();
    }

    public void clickOnInItemInElectronics(String numberInMenu) {
        WebElement button = driver.findElement(By.xpath(selectorForElectrical + "[" + numberInMenu + "]"));
        button.click();
    }
}
