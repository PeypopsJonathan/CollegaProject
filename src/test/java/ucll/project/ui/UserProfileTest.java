package ucll.project.ui;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserProfileTest {
    private static WebDriver driver;

    @Before
    public void SetupDriver() {
        driver.get(Config.BASE_URL);
    }

    @Test
    public void VisitUsersProfilePageTest() {
        WebElement link = driver.findElement(By.linkText("Profile"));
        link.click();

        assertEquals("http://localhost:8080/Controller?command=Profile", driver.getCurrentUrl());
    }

    @Test
    public void CheckNameIsRight(){
        driver.get("http://localhost:8080/Controller?command=Profile");
        WebElement naam = driver.findElement(By.cssSelector(".profileOverview h1"));
        assertEquals("Daan Heivers", naam.getText().trim());
    }

    @Test
    public void EmailadresIsCorrectFormat(){
        driver.get("http://localhost:8080/Controller?command=Profile");
        assertTrue(Pattern.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", driver.findElement(By.cssSelector("p.profileOverviewItem")).getText()));
    }




    @After
    public void CloseBrowser() {
        driver.close();
    }


}
