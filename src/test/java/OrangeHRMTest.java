import config.Configuration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.TimesheetPage;

import java.util.concurrent.TimeUnit;

import static org.testng.AssertJUnit.assertEquals;

public class OrangeHRMTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;
    private TimesheetPage timesheetPage;
    private final Configuration configuration = new Configuration();
    private final String user2 = configuration.getProperties().getProperty("user2");
    private final String passwordUser2 = configuration.getProperties().getProperty("passwordUser2");

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://opensource-demo.orangehrmlive.com/");

        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        timesheetPage = new TimesheetPage(driver);
    }

    @Test
    public void testExample() {
        loginPage.enterUsername(user2);
        loginPage.enterPassword(passwordUser2);
        loginPage.clickLoginButton();
        homePage.clickTimeMenu();
        timesheetPage.clickTimesheetPeriodView();
        timesheetPage.clickFirstRowCommentButton();
        String commentText = timesheetPage.getCommentText();
        assertEquals("Leadership Development", commentText);
    }

    @Test
    public void invalidLoginTest() {
        loginPage.enterUsername("invalidUsername");
        loginPage.enterPassword("invalidPassword");
        loginPage.clickLoginButton();

        assertEquals(loginPage.getErrorMessage(), "Invalid credentials");
    }

    @AfterTest
    public void teardown() {
        driver.quit();
    }
}
