package com.company.enroller.e2e.meetingsManagement;

import com.company.enroller.e2e.BaseTests;
import com.company.enroller.e2e.Const;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;
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

    @Test
    @DisplayName("[SPOTKANIA.2] The user should be able to sign up for Meeting A.")
    void userCanSignUpForMeeting() {
        driver.get(Const.HOME_PAGE);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement loginInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//label[contains(text(),'Zaloguj')]/following-sibling::input"))
        );
        loginInput.sendKeys(Const.USER_II_NAME);
        driver.findElement(By.xpath("//button[normalize-space()='Wchodzę']")).click();

        String title = Const.MEETING_I_TITLE;
        By rowLocator = By.xpath("//tr[td[normalize-space()='" + title + "']]");
        WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(rowLocator));

        List<WebElement> signInButtons = row.findElements(
                By.xpath(".//button[normalize-space()='Zapisz się']")
        );

        if (signInButtons.isEmpty()) {
            System.out.println("Użytkownik jest już zapisany lub brak przycisku.");
        } else {
            signInButtons.get(0).click();
        }

        boolean isVisible = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//tr[td[normalize-space()='" + title + "']]//li[normalize-space()='" + Const.USER_II_NAME + "']"))
        ) != null;

        assertThat(isVisible)
                .as("Użytkownik powinien być widoczny na liście uczestników Meeting A.")
                .isTrue();
    }

    @Test
    @DisplayName("[SPOTKANIA.3] The user should be able to delete an empty meeting.")
    void userCanDeleteEmptyMeeting() {
        driver.get(Const.HOME_PAGE);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement loginInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//label[contains(text(),'Zaloguj')]/following-sibling::input"))
        );
        loginInput.sendKeys(Const.USER_I_NAME);
        driver.findElement(By.xpath("//button[normalize-space()='Wchodzę']")).click();

        WebElement addBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Dodaj nowe spotkanie']"))
        );
        addBtn.click();

        String title = "Auto-Test-" + UUID.randomUUID();
        String description = "Do usunięcia";

        WebElement titleInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//form//input[@type='text']"))
        );
        titleInput.sendKeys(title);

        WebElement descriptionInput = driver.findElement(By.xpath("//form//textarea"));
        descriptionInput.sendKeys(description);

        WebElement submitBtn = driver.findElement(By.xpath("//form//button[normalize-space()='Dodaj']"));
        submitBtn.click();

        By rowLocator = By.xpath("//tr[td[normalize-space()='" + title + "']]");
        WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(rowLocator));

        WebElement deleteBtn = row.findElement(
                By.xpath(".//button[normalize-space()='Usuń puste spotkanie']")
        );
        deleteBtn.click();


        boolean deleted = wait.until(ExpectedConditions.invisibilityOfElementLocated(rowLocator));

        assertThat(deleted)
                .as("Spotkanie '" + title + "' powinno zostać usunięte z listy.")
                .isTrue();
    }

    @Test
    @DisplayName("[SPOTKANIA.4] The 'Dodaj' button should be disabled when meeting title is empty.")
    void userCannotAddMeetingWithoutTitle() {
        driver.get(Const.HOME_PAGE);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement loginInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//label[contains(text(),'Zaloguj')]/following-sibling::input"))
        );
        loginInput.sendKeys(Const.USER_I_NAME);
        driver.findElement(By.xpath("//button[normalize-space()='Wchodzę']")).click();

        WebElement addBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Dodaj nowe spotkanie']"))
        );
        addBtn.click();

        WebElement descriptionInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//form//textarea"))
        );
        descriptionInput.sendKeys("Opis bez tytułu");


        WebElement submitBtn = driver.findElement(
                By.xpath("//form//button[normalize-space()='Dodaj']")
        );

        boolean disabled = !submitBtn.isEnabled();
        assertThat(disabled)
                .as("Przycisk 'Dodaj' powinien być nieaktywny, jeśli pole tytułu jest puste.")
                .isTrue();
    }


    @AfterEach
    void teardown() {
        driver.quit();
    }
}
