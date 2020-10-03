package OrangeHRM;
//Goal:Print the url of the header image to the console
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;

@Test
public class Activity2 {
    WebDriver driver;
    @BeforeMethod
    public void beforeMethod() {	
        //Create a new instance of the Firefox driver
    	System.setProperty("webdriver.geckodriver.driver","C:\\Windows\\System32\\geckodriver.exe");
    	driver = new FirefoxDriver();
    	 //Open browser
    	String url = "http://alchemy.hguy.co/orangehrm";
    	driver.get(url);
    	String title = driver.getTitle();
        //Assertion for page title
        AssertJUnit.assertEquals("OrangeHRM", title);
    }
    public void getHeaderURL(){
    	String img = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[1]/img")).getAttribute("src");
    	 //Print the URL in the login Page to the console.
        System.out.println("URL of the Header image is: " + img);
        }
    @AfterMethod
    public void afterMethod() {
        //Close the browser
        driver.quit();
    }
}
