package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataGenerator;

import java.time.Duration;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.DELETE;

public class DebitCardTest {
    @BeforeAll
    static void setUpAll(){
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll(){
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }


    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id=date] input").sendKeys(DELETE, DataGenerator.generateDate(3));
        $("[data-test-id=name] input").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id=phone] input").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification] .notification__content").shouldBe(visible,Duration.ofSeconds(15)).shouldHave(exactText("Встреча успешно запланирована на "  + DataGenerator.generateDate(3)));
        $("[data-test-id=date] input").doubleClick().sendKeys(DELETE, DataGenerator.generateDate(7));
        $(".button").click();
        $("[data-test-id='replan-notification'] .notification__content").shouldBe(visible).
                shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("[data-test-id='replan-notification'] button").click();
        $("[data-test-id=success-notification] .notification__content").shouldBe(visible,Duration.ofSeconds(15)).shouldHave(exactText("Встреча успешно запланирована на "  + DataGenerator.generateDate(7)));
    }

}