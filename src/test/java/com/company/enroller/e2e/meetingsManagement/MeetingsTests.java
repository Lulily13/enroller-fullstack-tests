package com.company.enroller.e2e.meetingsManagement;

import com.company.enroller.e2e.BaseTests;
import com.company.enroller.e2e.Const;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class MeetingsTests extends BaseTests {

    WebDriver driver;

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("[SPOTKANIA.1] The meeting should be added to your meeting list.")
    void addNewMeeting() {
        driver.get(Const.HOME_PAGE);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement loginInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//label[contains(text(),'Zaloguj')]/following-sibling::input"))
        );
        loginInput.sendKeys(Const.USER_I_NAME);

        WebElement loginBtn = driver.findElement(
                By.xpath("//button[normalize-space()='Wchodzę']")
        );
        loginBtn.click();

        WebElement addMeetingBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[normalize-space()='Dodaj nowe spotkanie']")
                )
        );
        addMeetingBtn.click();

        String title = "Test-" + UUID.randomUUID();
        String description = "Opis testowy";

        WebElement titleInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//form//input[@type='text']")
                )
        );
        titleInput.sendKeys(title);

        WebElement descriptionInput = driver.findElement(
                By.xpath("//form//textarea")
        );
        descriptionInput.sendKeys(description);

        WebElement submitBtn = driver.findElement(
                By.xpath("//form//button[normalize-space()='Dodaj']")
        );
        submitBtn.click();

        boolean found = wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.tagName("body"), title
        ));

        assertThat(found)
                .as("Nie znaleziono tytułu '%s' w widocznej treści strony", title)
                .isTrue();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }
}
