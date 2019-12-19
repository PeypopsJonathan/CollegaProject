package ucll.project.ui;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserProfileTest {
    private static WebDriver driver;

    @Before
    public void SetupDriver() {
        driver = DriverHelper.getDriver();
        driver.get(Config.BASE_URL);

    }

    /*@Test
    public void VisitUsersProfilePageTest() {
        UiSuite.loginUser(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Profile")));
        WebElement link = driver.findElement(By.linkText("Profile"));
        link.click();

        assertEquals(Config.BASE_URL + "/Controller?command=Profile", driver.getCurrentUrl());
    }

    @Test
    public void CheckNameIsRight(){
        UiSuite.loginUser(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Profile")));
        WebElement link = driver.findElement(By.linkText("Profile"));
        link.click();
        WebElement naam = driver.findElement(By.cssSelector(".profileOverview h1"));
        assertEquals("Daan Heivers", naam.getText().trim());
    }

    @Test
    public void EmailadresIsCorrectFormat(){
        UiSuite.loginUser(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Profile")));
        WebElement link = driver.findElement(By.linkText("Profile"));
        link.click();
        assertTrue(Pattern.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", driver.findElement(By.cssSelector("p.profileOverviewItem")).getText()));
    }




    @After
    public void CloseBrowser() {
        driver.close();
    }
*/

}
