package OrangeHRM;
//Goal:Read the title of the website and verify the text
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

public class Activity1 {
    WebDriver driver;
    @BeforeMethod
    public void beforeMethod() {	
        //Create a new instance of the Firefox driver
    	System.setProperty("webdriver.geckodriver.driver","C:\\Windows\\System32\\geckodriver.exe");
        driver = new FirefoxDriver();
        //Open browser
        driver.get("http://alchemy.hguy.co/orangehrm");
    }
    @Test
    public void LaunchURL() {
        // Check the title of the page
        String title = driver.getTitle();
        //Print the title of the page
        System.out.println("Page title is: " + title);
        //Assertion for page title
        AssertJUnit.assertEquals("OrangeHRM", title);
        }
    @AfterMethod
    public void afterMethod() {
        //Close the browser
        driver.quit();
    }
}
