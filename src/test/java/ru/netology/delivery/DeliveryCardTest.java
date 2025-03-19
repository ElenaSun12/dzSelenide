package ru.netology.delivery;

import org.junit.jupiter.api.Test;
import com.codeborne.selenide.*;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {

    @Test
    void shouldDeliveryCardWithActualData() {

        Selenide.open("http://127.0.0.1:9999/");
        // Выбираем город
        $("[data-test-id='city'] input").setValue("Воронеж");

        // Выбираем дату +3 от текущей
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").setValue(deliveryDate);

        // Заполненяем ФИ + номер + галочка согласия
        $("[data-test-id='name'] input").setValue("Васильев Василий");
        $("[data-test-id='phone'] input").setValue("+79001234567");
        $("[data-test-id='agreement']").click();

        // Отправляем форму
        $$("button").findBy(text("Забронировать")).click();

        // Проверка успешности
        $("[data-test-id='notification']").shouldHave(text("Встреча успешно забронирована на " + deliveryDate), Duration.ofSeconds(15)).shouldBe(visible);
        // Или поиск по тексту
        //$(Selectors.withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}
