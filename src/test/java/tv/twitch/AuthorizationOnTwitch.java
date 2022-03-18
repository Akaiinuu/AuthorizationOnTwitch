package tv.twitch;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.Assert.assertEquals;

public class AuthorizationOnTwitch {

    public OperaDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.opera.driver","/WebDrivers/operadriver_win64/operadriver.exe");
        driver = new OperaDriver();
    }

    @Test
    public void successAuthorizationOnTwitch() {
        driver.get("https://www.twitch.tv/");
        WebElement AnonUserLoginButtonFirstClick = driver.findElement(By.cssSelector("[data-test-selector='anon-user-menu__login-button']"));
        AnonUserLoginButtonFirstClick.click();
        WebElement userName = (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@autocomplete='username']")));
        userName.sendKeys("TestUserrr2");
        WebElement userPassword = driver.findElement(By.xpath("//input[@autocomplete='current-password']"));
        userPassword.sendKeys("321654987Qwer!-!");
        WebElement AnonUserLoginButtonSecondClick = driver.findElement(By.xpath("//button[@data-a-target='passport-login-button']"));
        AnonUserLoginButtonSecondClick.click();
        WebElement WelcomeText = (WebElement) new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h3[@id='modal-root-header']")));
        String expectedText = driver.findElement(By.xpath("//h3[@id='modal-root-header']")).getText();
        String actualText = "Добро пожаловать на вечеринку, testuserrr2!";
        assertEquals(expectedText, actualText.strip());

    }

    @Test
    public void failedAuthorizationOnTwitchUnknownUser() {
        driver.get("https://www.twitch.tv/");
        WebElement AnonUserLoginButtonFirstClick = driver.findElement(By.cssSelector("[data-test-selector='anon-user-menu__login-button']"));
        AnonUserLoginButtonFirstClick.click();
        WebElement userName = (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@autocomplete='username']")));
        userName.sendKeys("T");
        WebElement userPassword = driver.findElement(By.xpath("//input[@autocomplete='current-password']"));
        userPassword.sendKeys("321654987Qwer!-!");
        WebElement AnonUserLoginButtonSecondClick = driver.findElement(By.xpath("//button[@data-a-target='passport-login-button']"));
        AnonUserLoginButtonSecondClick.click();
        WebElement errorText = (WebElement) new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//Strong[@class='CoreText-sc-cpl358-0 kUHrUR']")));
        String actualText = "Такого имени пользователя не существует.";
        String expectedText = driver.findElement(By.xpath("//Strong[@class='CoreText-sc-cpl358-0 kUHrUR']")).getText();
        assertEquals(expectedText, actualText.strip());
    }

    @After
    public void finish() {
        driver.quit();
    }
}
