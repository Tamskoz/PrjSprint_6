package com.example.Sprint6.tests;

import com.example.sprint6.pages.HomePage;
import com.example.sprint6.pages.AboutRentFormPage;
import com.example.sprint6.pages.ScooterForFormPage;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import io.github.bonigarcia.wdm.WebDriverManager;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.io.IOException;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

class OrderScooterTests{

    private WebDriver driver;
    private WebDriverWait wait;
    private HomePage homePage;
    private ScooterForFormPage scooterPage;
    private AboutRentFormPage aboutRentPage;

    //  Параметризованный тест принимает название браузера как аргумент
    @ParameterizedTest(name = "{index}: Test for browser={0}")
    @ValueSource(strings = {"chrome", "firefox"})

     public void testSubmitOrderTopScooter(String browserName) {

        switch(browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

        default:
            throw new IllegalArgumentException("Данный тип браузера не поддерживается");
        }

        try {
            homePage = new HomePage(driver);
            homePage.openPage(); // Открываем стартовую страницу
            homePage.clickOrderButtonTop();// кликаем по верхней кнопке заказа

            scooterPage = new ScooterForFormPage(driver);
            // Проверяем, появилась ли первач форма заказа
            assertTrue(scooterPage.isOrderForm(), "Первая форма заказа не отображается");

            scooterPage.fillOrderForm("Иван", "Иванов", "Ростов ул.Вокзальная д.1", "+79876543210"); // Заполним данные о покупателе на первой форме заказа
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

        } finally {
            if (driver != null) {
                driver.quit(); // Закрытие драйвера
            }
        }
    }

    // Параметризованный тест принимает название браузера как аргумент
    @ParameterizedTest(name = "{index}: Test for browser={0}")
    @ValueSource(strings = {"chrome", "firefox"})

    public void testSubmitOrderBottomScooter(String browserName) {

        switch(browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            default:
                throw new IllegalArgumentException("Данный тип браузера не поддерживается");
        }

        try {
            homePage = new HomePage(driver);
            homePage.openPage(); // Открываем стартовую страницу
            //  homePage.scrollToFAQSection();  // Прокручиваемся вниз
            homePage.clickOrderButtonBottom();// кликаем по нижней кнопке заказа

            scooterPage = new ScooterForFormPage(driver);
            // Проверяем, появилась ли первач форма заказа
            assertTrue(scooterPage.isOrderForm(), "Первая форма заказа не отображается");

            scooterPage.fillOrderForm("Наталья", "Николаева", "СПб Невский проспект дом 91", "+79123456789"); // Заполним данные о покупатере на первой форме заказа
            scooterPage.selectMetroStation("Комсомольская"); // Выбираем станцию метро
            scooterPage.furtherButtonClick(); // кликаем на кнопку Далее


            aboutRentPage = new AboutRentFormPage(driver);
            // Проверяем, появилась ли вторая форма заказа
            assertTrue(aboutRentPage.isRentalForm(), "Вторая форма заказа не отображается");

            aboutRentPage.selectGreyColor(); // Кликаем на чек-бокс серого
            assertTrue(aboutRentPage.isGreyChecked(), "Серый цвет не отмечен"); // Проверим что черный чек-бокс отжат

            aboutRentPage.setDeliveryDate("2025-06-10"); // Вручную заполняем дату доставки
            aboutRentPage.rentalPeriod(new String[]{"два дня"});
            //aboutRentPage.rentalPeriod("два дня");// Заполняем срок аренды

//        Thread.sleep(5000); // Ждем 5 секунд
            aboutRentPage.enterComment("ОЧЕНЬ проОЧЕНЬ Жду заказ"); // Заполняем комментарий

            aboutRentPage.orderСontinuationStep(); // Завершаем оформление заказа
            aboutRentPage.placeOrder(); // Подтверждаем оформление заказа
            // aboutRentPage.isSuccessMessageDisplayed(); // Проверяем всплывающее окно об успешном заказе

            assertTrue(aboutRentPage.isSuccessMessageDisplayed(), "Сообщение об успешной отправке заказа не появилось.");

        } finally {
            if (driver != null) {
                driver.quit(); // Закрытие драйвера
            }
        }
    }

}