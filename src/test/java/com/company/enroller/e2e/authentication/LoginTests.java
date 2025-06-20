package com.company.enroller.e2e.authentication;

import com.company.enroller.e2e.BaseTests;
import com.company.enroller.e2e.Const;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTests extends BaseTests {

    WebDriver driver;
    LoginPage loginPage;
    @BeforeEach
    void setup() {
        this.driver = new ChromeDriver();
        this.loginPage = new LoginPage(this.driver);
        this.loginPage.get(Const.HOME_PAGE);
    }

    @Test
    @DisplayName("[LOGOWANIE.1] No login, system should not confirm the user")
    void emptyLoginName() {
        this.loginPage.loginAs("");

        assertThat(this.loginPage.loginBtnIsPresent()).isTrue();
    }


    @Test
    @DisplayName("[LOGOWANIE.2] The system should accept the login and display the meetings view. " +
            "The user should be able to see all meetings")
    void correctLoginName() {
        this.loginPage.loginAs(Const.USER_II_NAME);
    }


}
