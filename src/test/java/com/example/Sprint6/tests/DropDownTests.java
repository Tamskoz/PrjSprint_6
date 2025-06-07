package com.example.Sprint6.tests;

import com.example.sprint6.pages.HomePage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

// Класс с общим набором тестов, совместимых с chrome и firefox
class DropDownTests {

    private WebDriver driver;
    private HomePage mainPage;

    // Параметризованный тест принимает название браузера как аргумент
    @ParameterizedTest(name = "{index}: Test for browser={0}")
    @ValueSource(strings = {"chrome", "firefox"})
    public void testFaqDropdownOpensCorrectly(String browserName) {

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
            mainPage = new HomePage(driver);
            mainPage.openPage(); // Открываем стартовую страницу
            mainPage.scrollToFAQSection();  // Прокручиваемся вниз до блока FAQ
            mainPage.firstDropDown(); // Раскрываем первый вопрос
            Assertions.assertTrue(mainPage.firstIsDropDownVisible(), "Ответ на первый вопрос не раскрылся"); // Проверяем открытие ответа на первый вопрос
            mainPage.secondDropDown(); // Раскрываем второй вопрос
            Assertions.assertTrue(mainPage.secondIsDropDownVisible(), "Ответ на второй вопрос не раскрылся"); // Проверяем открытие ответа на второй вопрос
        } finally {
            if (driver != null) {
                driver.quit(); // Закрытие драйвера
            }
        }
    }
}
