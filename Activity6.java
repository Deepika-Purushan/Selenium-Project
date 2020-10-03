package OrangeHRM;
//Goal:Add an employee and their details to the site

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.AssertJUnit;
import org.testng.annotations.Parameters;

public class Activity6 {
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
    @Test(priority=1)
	public void verify_Directory_Menu() {
		//locate the navigation menu
    	java.util.List<WebElement> navigationMenu = driver.findElements(By.xpath("//div[@class='menu']"));
		for(WebElement menuItem:navigationMenu) {
			Reporter.log("Menu items are",true);
			Reporter.log("----------------",true);
			Reporter.log(menuItem.getText(),true);
			
			//Verify that the Directory menu item is visible and clickable.
			if(menuItem.getText().contains("Directory")) {
				WebElement directoryMenu = driver.findElement(By.xpath("//b[contains(text(),'Directory')]"));
				Reporter.log("Directory menu item is displayed: "+directoryMenu.isDisplayed(),true);
				Reporter.log("Directory menu item is clickable: "+directoryMenu.isEnabled(),true);	
				Actions action = new Actions(driver);
				action.moveToElement(directoryMenu).build().perform();
				directoryMenu.click();
				directoryMenu.click();
				}
		}
		
		//Verify that the heading of the page matches Search Directory
		String Header = driver.findElement(By.xpath("//h1[contains(text(),'Search Directory')]")).getText();
		AssertJUnit.assertEquals("Search Directory",Header);
		Reporter.log("Heading of the page matches: "+Header, true);
	}

    @AfterClass
    public void afterClass() {
        //Close browser
        driver.close();
    }
}