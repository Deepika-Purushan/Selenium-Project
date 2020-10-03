package OrangeHRM;
//Goal:Login and apply for a leave on the HRM site

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.openqa.selenium.support.ui.Select;

@Test
public class Activity8 {
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
	public void apply_Leave() {
		//Navigate to the Dashboard page and click on the Apply Leave option
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//b[contains(text(),'Dashboard')]")));
		driver.findElement(By.xpath("//b[contains(text(),'Dashboard')]")).click();
		
		driver.findElement(By.xpath("//td[4]//div[1]//a[1]//img[1]")).click();
		
		//Select leave type and duration of the leave
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='applyleave_txtLeaveType']")));
		WebElement leaveType = driver.findElement(By.xpath("//select[@id='applyleave_txtLeaveType']"));
		Select selectLeave = new Select(leaveType);
		selectLeave.selectByVisibleText("Paid Leave");
		WebElement fromDate = driver.findElement(By.xpath("//input[@id='applyleave_txtFromDate']"));
		WebElement toDate = driver.findElement(By.xpath("//input[@id='applyleave_txtToDate']"));
		WebElement comment = driver.findElement(By.xpath("//textarea[@id='applyleave_txtComment']"));
		
		//get the current date
		DateTimeFormatter  dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		LocalDateTime now = LocalDateTime.now();  
		String todayDate = dtf.format(now);
		fromDate.clear();
		fromDate.sendKeys(todayDate);
		
		//Add 7 days to current date
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 7);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String newDate = sdf.format(c.getTime());
		toDate.clear();
		toDate.sendKeys(newDate);
		comment.sendKeys("Emergency  Leave");
		
		//Submit the Leave
		driver.findElement(By.xpath("//input[@id='applyBtn']")).click();
		Reporter.log("Leave applied successfully",true);
		
		//Navigate to the My Leave page to check the status of leave application
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//b[contains(text(),'Dashboard')]")));
		driver.findElement(By.xpath("//b[contains(text(),'Dashboard')]")).click();
		driver.findElement(By.xpath("//span[contains(text(),'My Leave')]")).click();
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(600,0)");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='calFromDate']")));
		WebElement calFromDate = driver.findElement(By.xpath("//input[@id='calFromDate']"));
		calFromDate.clear();
		calFromDate.sendKeys(todayDate);
		WebElement calToDate=driver.findElement(By.xpath("//input[@id='calToDate']"));
		calToDate.clear();
		calToDate.sendKeys(newDate);
		driver.findElement(By.xpath("//input[@id='btnSearch']")).click();
		String leaveStaus = driver.findElement(By.xpath("//table/tbody/tr[1]/td[6]/a[1]")).getText();
		Reporter.log("Leave status is: "+leaveStaus, true);
		
	}

    @AfterClass
    public void afterClass() {
        //Close browser
        driver.close();
    }
}