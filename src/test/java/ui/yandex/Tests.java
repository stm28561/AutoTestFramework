package ui.yandex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.GooglePageWithSearch;
import pages.OpenPage;
import pages_yandex.YandexMainPage;
import pages_yandex.YandexMarket;
import pages_yandex.YandexMarketGoods;
import pages_yandex.YandexMarketSearchAndSpecify;

import java.util.List;
import java.util.Map;

public class Tests extends BaseTests {

    @Test
    public void testYandexFirst() throws InterruptedException {
        YandexMainPage yandexMainPage = new YandexMainPage(chromeDriver);
        yandexMainPage.getCollectResult();

        yandexMainPage.chooseButton("Маркет");

        YandexMarket yandexMarket = new YandexMarket(chromeDriver);
        yandexMarket.getListOfMapOfGoods();
        yandexMarket.chooseGood("Электроника");

        YandexMarketGoods yandexMarketGoods = new YandexMarketGoods(chromeDriver);
        yandexMarketGoods.getListOfMapOfTypes(yandexMarketGoods);
        yandexMarketGoods.chooseType("Ноутбуки", yandexMarketGoods);

        YandexMarketSearchAndSpecify yandexMarketSearchAndSpecify = new YandexMarketSearchAndSpecify(chromeDriver);
        yandexMarketSearchAndSpecify.getMapOfBrands(yandexMarketSearchAndSpecify);
        yandexMarketSearchAndSpecify.setPrice("10000", "30000");
        yandexMarketSearchAndSpecify.chooseBrand("Lenovo", yandexMarketSearchAndSpecify, chromeDriver);
        yandexMarketSearchAndSpecify.chooseBrand("HP", yandexMarketSearchAndSpecify, chromeDriver);

        //yandexMarketSearchAndSpecify.setTwelveLaptops(chromeDriver);
        Assertions.assertTrue(yandexMarketSearchAndSpecify.ifThereTwelveElements(chromeDriver), "На странице не 12 элементов");

        String firstProduct = yandexMarketSearchAndSpecify.getProductName(0);
        Assertions.assertTrue(yandexMarketSearchAndSpecify.compareProducts(chromeDriver, firstProduct),"Первый продукт до поиска не равен первому продукту после поиска");
    }

    @Test
    public void testYandexSecond() throws InterruptedException {
        YandexMainPage yandexMainPage = new YandexMainPage(chromeDriver);
        yandexMainPage.getCollectResult();

        yandexMainPage.chooseButton("Маркет");

        YandexMarket yandexMarket = new YandexMarket(chromeDriver);
        yandexMarket.getListOfMapOfGoods();

        yandexMarket.chooseGood("Электроника");

        YandexMarketGoods yandexMarketGoods = new YandexMarketGoods(chromeDriver);
        yandexMarketGoods.getListOfMapOfTypes(yandexMarketGoods);
        yandexMarketGoods.chooseType("Смартфоны", yandexMarketGoods);

        YandexMarketSearchAndSpecify yandexMarketSearchAndSpecify = new YandexMarketSearchAndSpecify(chromeDriver);
        yandexMarketSearchAndSpecify.getMapOfBrands(yandexMarketSearchAndSpecify);

        yandexMarketSearchAndSpecify.chooseBrand("Apple", yandexMarketSearchAndSpecify, chromeDriver);

        //yandexMarketSearchAndSpecify.setTwelveLaptops(chromeDriver);
        Assertions.assertTrue(yandexMarketSearchAndSpecify.ifThereTwelveElements(chromeDriver), "На странице не 12 элементов");
        Assertions.assertTrue(yandexMarketSearchAndSpecify.ifThereOnlySelectedBrand(chromeDriver, "Apple"), "Не все телефона бренда Apple");
    }



    public void testOpen() {
        GooglePageWithSearch googlePageWithSearch = new GooglePageWithSearch(chromeDriver, "открытие");
        List<Map<String,Object>> resultSearch = googlePageWithSearch.getCollectResult();
        resultSearch.forEach(x-> System.out.println(x.get("NAME_PAGE").toString()));
        googlePageWithSearch.goPage("Частным клиентам");
        OpenPage openPage = new OpenPage(chromeDriver);
        List<Map<String,String>> collectExchangeRates = openPage.getCollectExchangeRates();
        System.out.println(collectExchangeRates);
        Assertions.assertTrue(
                Double.parseDouble(
                        collectExchangeRates.stream()
                                .filter(x->x.get("Валюта обмена").contains("USD"))
                                .findFirst()
                                .get().get("Банк покупает").replace(",",".")
                )
                        <
                        Double.parseDouble(
                                collectExchangeRates.stream()
                                        .filter(x->x.get("Валюта обмена").contains("USD"))
                                        .findFirst()
                                        .get().get("Банк продает").replace(",",".")
                        )
        );
    }
}
