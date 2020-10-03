package OrangeHRM;
//Goal:Add an employee and their details to the site

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import junit.framework.Assert;

@Test
public class Activity4 {
	WebDriver driver;
	WebDriverWait wait;
	String firstName="Deepika";
	DateFormat df = new SimpleDateFormat( "hh-mm-ss" ) ;
	String userName = "UN"+ df;
	String lastName="SP";

    @BeforeClass
    public void beforeClass(){	
        //Create a new instance of the Firefox driver
    	System.setProperty("webdriver.geckodriver.driver","C:\\Windows\\System32\\geckodriver.exe");
    	driver = new FirefoxDriver();
    	wait = new WebDriverWait(driver,20);
		driver.manage().window().maximize();
		Reporter.log("Test Case Execution Started",true);
		Reporter.log("Opening the URL",true);
		//Open browser   	
    	String url = "http://alchemy.hguy.co/orangehrm";
    	driver.get(url);
    	String title = driver.getTitle();
        //Assertion for page title
        AssertJUnit.assertEquals("OrangeHRM", title);
    }
    @Test(priority=0)
    @Parameters({"username", "password"})
    public void loginHRM(String username, String password) {
        //Find username and pasword fields
    	WebElement usernamefield = driver.findElement(By.id("txtUsername"));
        WebElement passwordfield = driver.findElement(By.id("txtPassword"));
        //Enter values
        usernamefield.sendKeys(username);
        passwordfield.sendKeys(password);
        //Click login
        driver.findElement(By.id("btnLogin")).click();
        //Verify home page is displayed
        String header = driver.findElement(By.xpath("/html/body/div/div[3]/div/div/h1")).getText();
        Assert.assertEquals("Dashboard", header);
        System.out.println("New page title is: " + header);
    }
    @Test(priority=1)
    public void addNewEmployee() {
    	//find and click the PIM menu
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[contains(text(),'PIM')]")));
    	WebElement PIMMenu = driver.findElement(By.xpath("//b[contains(text(),'PIM')]"));
    	PIMMenu.click();

    	//find and click the Add button to add employee details
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("menu_pim_addEmployee")));
    	driver.findElement(By.id("menu_pim_addEmployee")).click();
    	
    	//Fill the employee details
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'head']/h1")));
    	driver.findElement(By.id("firstName")).sendKeys(firstName);
    	driver.findElement(By.id("lastName")).sendKeys(lastName);
    	driver.findElement(By.xpath("//input[@id='chkLogin']")).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//label[contains(text(),'User Name')]")));
		driver.findElement(By.xpath("//input[@id='user_name']")).sendKeys(userName);
    	driver.findElement(By.id("btnSave")).click();
    	
    	//Navigate back to the Admin page and verify the creation of your employee
    	driver.findElement(By.xpath("//b[contains(text(),'Admin')]")).click();
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='toggle tiptip']")));
    	driver.findElement(By.id("searchSystemUser_userName")).sendKeys(userName);
    	driver.findElement(By.id("searchBtn")).click();
    	String element = driver.findElement(By.xpath("//a[contains(text(),'"+userName+"')]")).getText();
    	Assert.assertEquals(element, userName);
    	Reporter.log("User Created for: "+element, true);
    }
    @AfterClass
    public void afterClass() {
        //Close browser
        driver.close();
    }
}