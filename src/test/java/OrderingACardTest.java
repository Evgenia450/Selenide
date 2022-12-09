import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class OrderingACardTest {

    @BeforeEach
    void openURL() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");

    }

    @Test
    void shouldSendARequest() {
        String planningDate = generateDate(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Санкт-Петербург");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        form.$("[data-test-id=date] input").setValue(planningDate);
        form.$("[data-test-id=name] input").setValue("Сергей Перов");
        form.$("[data-test-id=phone] input").setValue("+79002073131");
        form.$("[data-test-id=agreement]").click();
        form.$("[class=button__text]").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

    }

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}