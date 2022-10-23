package ui.yandex;


import helpers.CustomUtils;
import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages_yandex.*;
import steps.Steps;


public class TestForLeague extends BaseTests{
    String yandexMarketUrl = "https://market.yandex.ru/";
    String brand1 = "Samsung";
    String brand2 = "LG";
    String price = "20000";

    @Feature("Проверка результатов поиска")
    @Test
    public void  leagueTestTask() throws InterruptedException {
        Allure.step("1. Зайти на Яндекс маркет", stepContext -> {
            CustomUtils.openPage(chromeDriver, yandexMarketUrl);
            //Необходимо рефакторить, создать класс Webdriver manager и в BeforeStepStop организовать скриншоты
            CustomUtils.getScreen(chromeDriver);
        });
        Allure.step("2. Выбрать раздел Электроника - > Телевизоры", stepContext -> {
            YandexMarket yandexMarket = new YandexMarket(chromeDriver);
            yandexMarket.clickOnCatalog();
            yandexMarket.clickOnItemInCatalog("Электроника");
            yandexMarket.clickOnInItemInElectronics("3");
            yandexMarket.clickOnItemInSubmenu("Телевизоры");
            CustomUtils.getScreen(chromeDriver);
        });
        Allure.step("3. Задать параметр поиска:\n" +
                "по цене: от 20000 рублей\n" +
                "по производителю: Samsung и LG", stepContext -> {
            YandexMarketSearchAndSpecify yandexMarketSearchAndSpecify = new YandexMarketSearchAndSpecify(chromeDriver);
            yandexMarketSearchAndSpecify.getMapOfBrands(yandexMarketSearchAndSpecify);
            yandexMarketSearchAndSpecify.setPrice(price);
            yandexMarketSearchAndSpecify.chooseBrand(brand1, yandexMarketSearchAndSpecify, chromeDriver);
            yandexMarketSearchAndSpecify.chooseBrand(brand2, yandexMarketSearchAndSpecify, chromeDriver);
            yandexMarketSearchAndSpecify.collectProducts(chromeDriver);
            yandexMarketSearchAndSpecify.openProduct(chromeDriver, 0);
            CustomUtils.getScreen(chromeDriver);
        });
        Allure.step("4. Открыть первый найденный телевизор из списка. Проверить что производитель и цена соответствуют выбранным параметрам поиска", stepContext -> {
            YandexMarketProductPage yandexMarketProductPage = new YandexMarketProductPage(chromeDriver);
            Assertions.assertTrue(yandexMarketProductPage.checkNameTitleContains(chromeDriver, brand1) ||
                    yandexMarketProductPage.checkNameTitleContains(chromeDriver, brand2), "Название содержит брэнд элемента");
            Assertions.assertTrue(yandexMarketProductPage.checkPriceIsHigherThan(chromeDriver, Integer.parseInt(price)),
                    "Цена выше чем " + price);
            CustomUtils.getScreen(chromeDriver);
        });

    }
}
