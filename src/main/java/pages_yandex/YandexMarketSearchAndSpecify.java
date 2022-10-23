package pages_yandex;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YandexMarketSearchAndSpecify extends YandexMarket{

    private String selectorBlockWithPrices = "//*[@class='_38Pay']";
    //private String selectorStartPrice = "//*[@name='Цена от']";
    //private String selectorEndPrice = "//*[@name='Цена до']";

//    private String selectorBlockWithBrands = "//*[@class='_2y67x']";
//    private String selectorEachBrand = "//li[@class='_1-l0X']";
    private String selectorBrands = "//span[contains(.,'Производитель')]/../..//span[@class = '_1ZDAA']";

    private String selectorCheckBox = "//span[contains(.,'Производитель')]/../..//*[@class='_2XaWK']";
//    private String selectorNameOfBrand = ".//span";
//    private String selectorLinkOfBrand = ".//*[@href]";

    private String selectorDropDownList = "//*[@data-apiary-widget-name='@MarketNode/SearchPager']//div[@class='_3JNss _1BSH6 v3cFc']";
    private String selectorTwelveElements = "//*[@class='_1KpjX _35Paz']";


    private String selectorBlockWithSearchResults = "//*[@data-zone-name='SearchResults']";
    private String selectorSourceToProduct = ".//article//h3/a[@href]";

    private String selectorEachProduct = "//div[@class = '_3Fff3']//span[@data-tid = '2e5bde87']";
    private String selectorNameOfProduct = "//article//h3/*[@title]";


    private String selectorSearchField = "//div[@class='G4KLq']//*[@placeholder='Искать товары']";
    private String selectorSearchButton = "//span[@class='JqPid']";

    private String selectorNextPage = "//a[@aria-label='Следующая страница']";

    private String selectorStartPrice = "(//*[@class = '_3qxDp'])[1]";

    private String selectorEndPrice = "(//*[@class = '_3qxDp'])[2]";



    private List<WebElement> products = new ArrayList<>();

    private List<WebElement> brands = new ArrayList<>();

    private List<WebElement> prices = new ArrayList<>();

    private List<Map<String, Object>> listOfMapOfBrands = new ArrayList<>();

    private WebElement dropdown;

    public YandexMarketSearchAndSpecify(WebDriver driver) {
        super(driver);
        brands = driver.findElements(By.xpath(selectorBrands));
        prices.add(driver.findElement(By.xpath(selectorStartPrice)));
        prices.add(driver.findElement(By.xpath(selectorEndPrice)));
        //dropdown = driver.findElement(By.xpath(selectorDropDownList));
    }

    private List<Map<String, Object>> collectMapOfBrands(List<Map<String, Object>> map, List<WebElement> listToIterate, String nameOf, String checkBox) {
        for(WebElement result : listToIterate)
            map.add(Map.of(
                    "WEB_ELEMENT", result,
                    "NAME_PAGE", result.getText(),
                    "CHECKBOX", result.findElement(By.xpath("//span[contains(.,'Производитель')]/../..//span[contains(.,'" +
                            result.getText() + "')]//preceding-sibling::span[@class='_2XaWK']"))
            ));
        return map;
    }

    public List<Map<String, Object>> getMapOfBrands(YandexMarketSearchAndSpecify object) {
        object.collectMapOfBrands(listOfMapOfBrands, brands, selectorBrands, selectorCheckBox);
        return listOfMapOfBrands;
    }

    public void chooseBrand(String name, YandexMarketSearchAndSpecify object, WebDriver driver) throws InterruptedException {
        object.choose(name, listOfMapOfBrands, selectorCheckBox);
        waitingTillRefresh();
    }

    public void setPrice(String start) {
        prices.get(0).sendKeys(start);
    }
    public void setPrice(String start, String end) {
        prices.get(0).sendKeys(start);
        prices.get(1).sendKeys(end);
    }

    public void setTwelveLaptops(WebDriver driver) throws InterruptedException {
        Actions actions = new Actions(driver);
        waitingTillRefresh();
        actions.moveToElement(dropdown).click().build().perform();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selectorDropDownList + selectorTwelveElements)));
        actions.moveToElement(driver.findElement(By.xpath(selectorDropDownList + selectorTwelveElements))).click().build().perform();

    }

    public boolean ifThereTwelveElements(WebDriver driver) {
        collectProducts(driver);
        if (products.size() == 12) {
            return true;
        } else {
            return false;
        }
    }

    public void collectProducts(WebDriver driver) {
        products = (driver.findElements(By.xpath(selectorEachProduct)));
        //System.out.println(products.get(0).getText());
    }

    public void openProduct(WebDriver driver, Integer pos) {
        products.get(pos).click();
    }
    public String getProductName(int pos) {
        return products.get(pos).getText();
    }

    public void searchForNewProduct(String productName, WebDriver driver) {
        driver.findElement(By.xpath(selectorSearchField)).sendKeys(productName);
        driver.findElement(By.xpath(selectorSearchButton)).click();
    }

    public boolean compareProducts(WebDriver driver, String previousValue) {
        collectProducts(driver);
        String currentValue = products.get(0).getText();
        if (previousValue.contains(currentValue)) {
            return true;
        }
        return false;
    }

    public boolean ifThereOnlySelectedBrand(WebDriver driver, String productName) throws InterruptedException {
        Actions actions = new Actions(driver);
        WebElement button = driver.findElement(By.xpath(selectorNextPage));
        while (!driver.findElements(By.xpath(selectorNextPage)).isEmpty()) {
            collectProducts(driver);
            if (!products.stream().allMatch(o -> o.getText().contains(productName))) {
                return false;
            }
            actions.moveToElement(button).click().build().perform();
            waitingTillRefresh();
        }
        return true;
    }
}
