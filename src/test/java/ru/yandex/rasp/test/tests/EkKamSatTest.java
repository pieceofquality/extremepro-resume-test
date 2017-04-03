//        1.       Открыть страницу yandex.ru.
//        2.       Перейти на страницу сервиса «Расписания».
//        3.       Найти электрички из Екатеринбурга в Каменск-Уральский на ближайшую субботу.
//        4.       Проверить, что произведен поиск и название таблицы результатов соответствует параметрам поиска.
//        5.       Сохранить данные о самом раннем рейсе, который отправляется не ранее 12:00 и билет на который стоит не более 200 р.
//        6.       Вывести на консоль данные о рейсе, а именно:
//        ·         Время отправления
//        ·         Цена в рублях
//        ·         Цена в долларах
//
//        В случае отсутствия таких рейсов, вывести соответствующее сообщение.
//        7.       Открыть страницу информации о рейсе.
//        8.       Проверить, что данные о рейсе на странице информации соответствуют данным из пункта 5, а именно:
//        ·         Название таблицы
//        ·         Время и пункт отправления
//        ·         Время и пункт прибытия
//        ·         Время в пути

package ru.yandex.rasp.test.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

public class EkKamSatTest {
        ChromeDriver wd;

        @BeforeMethod
        public void setUp() throws Exception {
            wd = new ChromeDriver();
            wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        }

        @Test
        public void testEkKamSat() {
            wd.get("https://yandex.ru/");
            wd.findElement(By.linkText("ещё")).click();
            wd.findElement(By.xpath("//div[@class='services-more']//a[.='Расписания']")).click();
            selectTransport();
            selectFrom("Екатеринбург");
            selectTo("Каменск-Уральский");

            selectDateByInput("8 апреля");
            searchSubmit();
            String tableName = wd.findElement(By.cssSelector("h1")).getText();
            assertEquals("Расписание электричек из Екатеринбурга-Пасс. в Каменск-Уральский", tableName);
        }

        private void selectDateByInput(String date) {
            wd.findElement(By.cssSelector(".date-input_search__input")).click();
            wd.findElement(By.cssSelector(".date-input_search__input")).clear();
            wd.findElement(By.cssSelector(".date-input_search__input")).sendKeys(date);

        }

        private void selectDateByCalendar() {
            wd.findElement(By.cssSelector("label[class=datepicker_search__icon]")).click();
            wd.findElement(By.cssSelector("body > div.y-popup_islet._init._animate._position_bottom > div > div > div.calendar__container > div.calendar__days > div.calendar__days-container > div:nth-child(2) > div:nth-child(9)")).click();
        }

        private void searchSubmit() {
            wd.findElement(By.xpath("//div[@class='search-form__submit']//button[.='Найти']")).click();
        }

        private void selectTo(String destination) {
            wd.findElement(By.name("toName")).click();
            wd.findElement(By.name("toName")).clear();
            wd.findElement(By.name("toName")).sendKeys(destination);
        }

        private void selectFrom(String destination) {
            wd.findElement(By.name("fromName")).click();
            wd.findElement(By.name("fromName")).clear();
            wd.findElement(By.name("fromName")).sendKeys(destination);
        }

        private void selectTransport() {
            wd.findElement(By.cssSelector("body > div.page__header > header > div.header__content > div.header__transport-selector > div > span > label:nth-child(4)")).click();
        }

        @AfterMethod
        public void tearDown() {
            wd.quit();
        }

        public static boolean isAlertPresent(FirefoxDriver wd) {
            try {
                wd.switchTo().alert();
                return true;
            } catch (NoAlertPresentException e) {
                return false;
            }
        }
    }
