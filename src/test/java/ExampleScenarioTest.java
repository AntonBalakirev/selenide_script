import com.codeborne.selenide.SelenideElement;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ExampleScenarioTest {

    @Test
    public void exampleScenario() {
        open("https://www.sberbank.ru/ru/person");


        // выбрать пункт меню - "Страхование"
        $(By.xpath("//a[@aria-label='Страхование']")).click();

        // выбрать пункт подменю - "Перейти в каталог"
        $(By.xpath("//a[text()='Перейти в каталог' and contains(@class, 'link_second')]")).click();

        // проверка открытия страницы "Страхование"
        Assert.assertEquals("Заголовок отсутствует/не соответствует требуемому",
                "Оформить страховку в СберБанке — СберБанк", title());

        // выбрать пункт подменю - "Страхование путешественников"
        SelenideElement element = $(By.xpath("//h3[text()='Страхование для путешественников']"));
        element.scrollTo();

        // нажать кнопку "Оформить онлайн"
        element.$(By.xpath("../../following-sibling::div//b[text()='Оформить онлайн']/..")).click();

        // проверка открытия страницы "Страхование путешественников"
        $(By.xpath("//h2")).scrollTo().shouldBe(visible)
                .shouldHave(text("Страхование путешественников"));

        // выбрать тариф страхования "Минимальный"
        $(By.xpath("//h3[text()='Минимальная']"))
                .scrollTo().shouldBe(enabled).click();

        // кликнуть по кнопке "Оформить"
        $(By.xpath("//button[text()='Оформить']"))
                .scrollTo().shouldBe(enabled).click();

        // заполнить поля данными
        fillField("surname_vzr_ins_0", "Застрахованный");
        fillField("name_vzr_ins_0", "Степан");
        fillField("birthDate_vzr_ins_0", "01.01.1990");
        fillField("person_lastName", "Страхователь");
        fillField("person_firstName", "Иван");
        fillField("person_middleName", "Иванович");
        fillField("person_birthDate", "02.02.1992");
        fillField("passportSeries", "4444");
        fillField("passportNumber", "777666");
        fillField("documentDate", "09.09.2009");
        fillField("documentIssue", "Паспортный стол");

        // кликнуть по кнопке "Продолжить"
        $(By.xpath("//button[contains(text(),'Продолжить')]"))
                .scrollTo().shouldBe(enabled).click();

        // проверить сообщение об ошибке
        $(By.id("phone")).$(By.xpath("./..//span")).shouldHave(text("Поле не заполнено."));
        $(By.id("email")).$(By.xpath("./..//span")).shouldHave(text("Поле не заполнено."));
        $(By.id("repeatEmail")).$(By.xpath("./..//span")).shouldHave(text("Поле не заполнено."));

        // проверить сообщение об ошибке
        $(By.xpath("//div[@class='alert-form alert-form-error']"))
                .scrollTo().shouldBe(visible)
                .shouldHave(text("При заполнении данных произошла ошибка"));
    }

    private void fillField(String id, String text) {
        $(By.id(id)).scrollTo().shouldBe(enabled).click();
        $(By.id(id)).setValue(text).shouldHave(value(text));
    }
}