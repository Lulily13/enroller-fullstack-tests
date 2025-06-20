package com.company.enroller.e2e;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ForFun {

    WebDriver driver;

    @BeforeEach
    void setup() {
        this.driver = new ChromeDriver();
    }

    @Test
    @DisplayName("Does all workers have an email?")
    void checkMails() throws InterruptedException {

        this.driver.get(Const.FOR_FUN_WWW);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));


        // CLose cookies
        wait.until(ExpectedConditions.elementToBeClickable(By.id("cn-accept-cookie"))).click();
//        WebElement cookiesCloseBtn = this.driver.findElement(By.id("cn-accept-cookie"));
//        cookiesCloseBtn.click();

        //Check emails

        List<WebElement> workers = this.driver.findElements(By.className("workers-item"));
        List<WebElement> mails = new ArrayList<>();

        for(WebElement worker : workers) {
            worker.findElements(By.className("workers-email"))
                    .stream()
                    .findFirst()
                    .ifPresent(mails::add);
        }

        assertThat(workers.size()).isEqualTo(mails.size());
//        Thread.sleep(1000 * 100);
    }

    @AfterEach
    void exit() {
        this.driver.quit();
    }

}
