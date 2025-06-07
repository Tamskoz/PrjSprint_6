package com.example.Sprint6.tests;

import com.example.sprint6.pages.HomePage;
import com.example.sprint6.pages.AboutRentFormPage;
import com.example.sprint6.pages.ScooterForFormPage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import java.util.Collection;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderScooterChromeTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private HomePage homePage;
    private ScooterForFormPage scooterPage;
    private AboutRentFormPage aboutRentPage;


    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup(); //  устанавливаем нужный драйвер
    }

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
       driver.manage().window().maximize(); // Максимизируем окно браузера
       wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Создаем единый таймаут
    }

    // Тестовые данные для заказа самоката
    @ParameterizedTest
    @CsvSource({
            "Иван, Иванов, Ростов ул.Вокзальная д.1, +79876543210",
            "Анна, Петрова, СПб Невский проспект дом 91, +79123456789"
    })

    // проверяем работу верхней кнопки заказа
    public void testSubmitOrderTopScooter(String name, String surname, String address, String phoneNumber) throws InterruptedException{

        homePage = new HomePage(driver);
        homePage.openPage(); // Открываем стартовую страницу
        homePage.clickOrderButtonTop();// кликаем по верхней кнопке заказа

        scooterPage = new ScooterForFormPage(driver);
        // Проверяем, появилась ли первач форма заказа
        assertTrue(scooterPage.isOrderForm(), "Первая форма заказа не отображается");

        scooterPage.fillOrderForm(name, surname, address, phoneNumber); // Заполним данные о покупатере на первой форме заказа
        scooterPage.selectMetroStation("Комсомольская"); // Выбираем станцию метро
        scooterPage.furtherButtonClick(); // кликаем на кнопку Далее


        aboutRentPage = new AboutRentFormPage(driver);
        // Проверяем, появилась ли вторая форма заказа
        assertTrue(aboutRentPage.isRentalForm(), "Вторая форма заказа не отображается");

        aboutRentPage.setDeliveryDate("2025-06-10"); // Вручную заполняем дату доставки
        aboutRentPage.rentalPeriod(new String[]{"два дня"});
        //aboutRentPage.rentalPeriod("два дня");// Заполняем срок аренды
        aboutRentPage.selectBlackColor(); // Кликаем на чек-бокс черного

        assertTrue(aboutRentPage.isBlackChecked(), "Черный цвет не отмечен"); // Проверим что черный чек-бокс отжат

        aboutRentPage.enterComment("Жду заказ"); // Заполняем комментарий
        aboutRentPage.orderСontinuationStep(); // Завершаем оформление заказа
        aboutRentPage.placeOrder(); // Подтверждаем оформление заказа
        // aboutRentPage.isSuccessMessageDisplayed(); // Проверяем всплывающее окно об успешном заказе

        assertTrue(aboutRentPage.isSuccessMessageDisplayed(), "Сообщение об успешной отправке заказа не появилось.");
    }
        // Завершаем работу с браузером
        @AfterEach
        public void teardown() {
            if (driver != null) {
                driver.quit();
            }
        }
}
