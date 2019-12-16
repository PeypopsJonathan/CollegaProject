package ucll.project.ui;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;

public class HomePageTest {

    private static WebDriver driver;

    @BeforeClass
    public static void SetupDriver() {
        // Setup the Chrome driver for the whole class
        driver = FirefoxDriverHelper.getDriver();
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
    public void VisitHomePageTest() {

        driver.get(Config.BASE_URL);
        assertEquals("Hello world!", driver.getTitle());

    }

    @Test
    public void VisitUsersPageTest() {

        driver.get(Config.BASE_URL);
        driver.get(Config.USERS_PAGE_URL);
        assertEquals("All users", driver.getTitle());

    }

}
