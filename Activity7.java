package OrangeHRM;
//Goal:Add employee qualifications

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.Parameters;

@Test
public class Activity7 {
	WebDriver driver;
	WebDriverWait wait;
	
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
        AssertJUnit.assertEquals("Dashboard", header);
        System.out.println("New page title is: " + header);
    }
    
    //Find the My Info menu item and edit user information
    @Test(priority=1)
    public void addQualification() {
    	Actions action = new Actions(driver);
		WebElement myInfo = driver.findElement(By.xpath("//*[@id='menu_pim_viewMyDetails']/b"));
		action.moveToElement(myInfo).build().perform();
		myInfo.click();
		myInfo.click();
		//Scroll the page
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0,600)");
		driver.findElement(By.xpath("//ul[@id='sidenav']//a[contains(text(),'Qualifications')]")).click();
		js.executeScript("window.scrollBy(400,0)");
		driver.findElement(By.cssSelector("#addWorkExperience")).click();
		WebElement company = driver.findElement(By.cssSelector("#experience_employer"));
		company.clear();
		company.sendKeys("IBM");
		Reporter.log("Company details entered", true);
		WebElement jobTitle = driver.findElement(By.cssSelector("#experience_jobtitle"));
		jobTitle.clear();
		jobTitle.sendKeys("Test Lead Engineer");
		Reporter.log("Job title entered", true);
		driver.findElement(By.xpath("//input[@id='btnWorkExpSave']")).click();
    }

    @AfterClass
    public void afterClass() {
        //Close browser
        driver.close();
    }
}