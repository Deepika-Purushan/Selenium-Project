package OrangeHRM;
//Goal: Login and retrieve the emergency contacts for the user

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.Parameters;

public class Activity9 {
	WebDriver driver;
	WebDriverWait wait;
	String name = "TestDeep1";
	String DOB = "1982-02-11";


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
    
    @Test(priority=1)
	public void emergency_contacts() {
		//Navigate to the My Info page
    	Actions action = new Actions(driver);
		WebElement myInfo = driver.findElement(By.xpath("//*[@id='menu_pim_viewMyDetails']/b"));
		action.moveToElement(myInfo).build().perform();
		myInfo.click();
		myInfo.click();
		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//b[contains(text(),'My Info')]")));
		//driver.findElement(By.xpath("//b[contains(text(),'My Info')]")).click();
		
		//Click on the Emergency Contacts menu item
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Emergency Contacts')]")));
		driver.findElement(By.xpath("//a[contains(text(),'Emergency Contacts')]")).click();
		
		//Retrieve information about all the contacts listed in the table
		java.util.List<WebElement> column = driver.findElements(By.xpath("//table/thead/tr/th"));
		java.util.List<WebElement> row = driver.findElements(By.xpath("//table/tbody/tr"));
		Reporter.log("Emergency contact details", true);
		Reporter.log("---------------------------------------------------------------", true);
		for(WebElement columnData:column) {
			System.out.print(" |"+columnData.getText());
			
		}
		Reporter.log("| Name | Relationship | Home Telephone | Mobile | Work Telephone |");
		Reporter.log("\n---------------------------------------------------------------",true);
		for(int i =1;i<=row.size();i++)
		{
			WebElement rows =driver.findElement(By.xpath("//table/tbody/tr[" +i+"]"));
			Reporter.log("  |"+ rows.getText()+ "  |",true);
		}
	}

    @AfterClass
    public void afterClass() {
        //Close browser
        driver.close();
    }
}