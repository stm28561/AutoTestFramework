package pages_yandex;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YandexMarketGoods extends YandexMarket{

    private String selectorColumnTypeOfGood = "//*[@class='sxrtt a6Vij']";
    private String selectorTypes = "//li/div[@data-zone-data]";
    private String selectorTypesLinks = "./a[@href]";
    private String selectorTypeName = "./*[@data-tid]";


    private List<WebElement> sourceOfTypes = new ArrayList<>();

    private List<Map<String, Object>> listOfMapOfTypes = new ArrayList<>();

    public YandexMarketGoods(WebDriver driver) {
        super(driver);
        sourceOfTypes = driver.findElements(By.xpath(selectorTypes));
    }

    public List<Map<String, Object>> getListOfMapOfTypes(YandexMarket object) {
        object.getCollectResult(listOfMapOfTypes, sourceOfTypes, selectorTypeName, selectorTypesLinks);
        return listOfMapOfTypes;
    }

   public void chooseType(String name, YandexMarketGoods object) {
        object.choose(name, listOfMapOfTypes, selectorTypesLinks);
   }

   public void clickOnGood() {

   }

}
