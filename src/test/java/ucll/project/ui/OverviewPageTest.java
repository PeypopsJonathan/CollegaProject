package ucll.project.ui;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ucll.project.domain.star.StarRepository;
import ucll.project.domain.star.StarRepositoryDb;
import ucll.project.domain.user.UserRepositoryDb;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class OverviewPageTest {

    private static WebDriver driver;

    @BeforeClass
    public static void SetupDriver() {
        // Setup the Chrome driver for the whole class
        driver = DriverHelper.getDriver();
    }

    @AfterClass
    public static void CloseBrowser() {
        // close it in the end, comment this away to keep chrome open
        driver.close();
    }

    /**
     * This is a sample test, remove this test and write your own!
     */
    @Test
    public void CountParagraphsOnGomePage() {
        driver.get(Config.BASE_URL);
        int numberOfP = driver.findElements(By.tagName("p")).size();
        StarRepository db = new StarRepositoryDb();
        System.out.println(db.getAll().size());
        assertEquals(db.getAll().size(), numberOfP);

    }

    @Test
    public void TagsForAllStars(){
        driver.get(Config.BASE_URL);
        boolean foundLi = true;
        for (WebElement ul :driver.findElements(By.tagName("ul"))) {
            if (ul.findElement(By.tagName("li")) == null) foundLi = false;
        }
        assertEquals(true, foundLi);
    }

    @Test
    public void AllStarsOrdered(){

    }

}