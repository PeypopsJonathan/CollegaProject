package ucll.project.ui;

import org.junit.*;
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

    @Before
    public void SetupDriver() {
        // Setup the Chrome driver for the whole class
        driver = DriverHelper.getDriver();
        driver.get(Config.BASE_URL);
    }

    @After
    public void CloseBrowser() {
        // close it in the end, comment this away to keep chrome open
        driver.close();
    }

    /**
     * This is a sample test, remove this test and write your own!
     */
    @Test
    public void CountParagraphsOnHomePage() {
        UiSuite.loginUser(driver);
        int numberOfP = driver.findElements(By.tagName("p")).size()-2;
        StarRepository db = new StarRepositoryDb();
        System.out.println(db.getAll().size());
        assertEquals(db.getAll().size(), numberOfP);

    }

    @Test
    public void TagsForAllStars(){
        UiSuite.loginUser(driver);
        boolean foundLi = true;
        for (WebElement ul :driver.findElements(By.tagName("ul"))) {
            if (ul.findElement(By.tagName("li")) == null) foundLi = false;
        }
        assertEquals(true, foundLi);
    }

}