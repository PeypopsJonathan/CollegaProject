package ucll.project.ui;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.sql.Driver;
import java.util.concurrent.TimeUnit;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        OverviewPageTest.class,
        UserProfileTest.class
})

public class UiSuite {
    public static void loginUser(WebDriver driver){
        //TYPE USERNAME
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement loginUsername = driver.findElement(By.name("login"));
        loginUsername.sendKeys("daan.heivers@student.ucll.be");
        //TYPE PASSWORD
        WebElement loginPassword = driver.findElement(By.name("password"));
        loginPassword.sendKeys("test");
        //CLICK SUBMIT
        loginPassword.submit();
    }
}
