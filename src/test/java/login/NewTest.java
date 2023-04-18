package login;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class NewTest {
	WebDriver driver;

	//This is to launch URL
	@BeforeSuite
	public void setUp() {
		driver = new EdgeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.saucedemo.com");
	}
	
	//Verify Title
	@Test
	public void verifyTitle() {
		String strTitle = driver.getTitle();
		Assert.assertEquals("Swag Labs", strTitle);
	}

	//This is the actual test.
	@Test(dataProvider = "dp", priority=1)
	public void verifyLogin(String strUN, String strPwd) throws Exception {
		driver.findElement(By.xpath("//*[@id='user-name']")).clear();
		driver.findElement(By.xpath("//*[@id='user-name']")).sendKeys(strUN);
		driver.findElement(By.xpath("//*[@id='password']")).clear();
		driver.findElement(By.xpath("//*[@id='password']")).sendKeys(strPwd);
		driver.findElement(By.xpath("//*[@id='login-button']")).click();
		Thread.sleep(2000);
		if (driver.findElement(By.xpath("//*[@id='react-burger-menu-btn']")).isDisplayed()) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}

		driver.findElement(By.xpath("//*[@id='react-burger-menu-btn']")).click();
		WebElement logout = driver.findElement(By.xpath("//a[@id='logout_sidebar_link']"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		JavascriptExecutor j = (JavascriptExecutor) driver;
		j.executeScript("arguments[0].click();", logout);
	}
	
	//Below Data Provider will provide the username and password to Test method. 
		@DataProvider(name = "dp")
		public static Object[][] primeNumbers() {
			return new Object[][] { { "standard_user", "secret_sauce" } };
            // ,{"locked_out_user", "secret_sauce"}, {"problem_user", "secret_sauce"}, {"performance_glitch_user", "secret_sauce"}};
		}
	
	//This method is added intentionally just to check if screenshot is captured in report during Test Case failing.
	@Test(priority=2)
	public void failedMethodToCheckScreenShot() throws InterruptedException {
		Assert.fail();
		Thread.sleep(2000);
	}
	
	//Below method will add the screenshot in report during Test Case Pass or Failing. 
	@AfterMethod
	public void ScreenshotCapture(ITestResult result) throws IOException  {
		Reporter.setCurrentTestResult(result);
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		File img = new File("C:\\Users\\Administrator\\eclipse-workspace\\login\\screenshots\\" + timeStamp + ".png");
		if (result.getStatus()== 1 || result.getStatus()== 2) {
			Reporter.log("This is the screenshot",true);
			FileOutputStream screenshotStream=new FileOutputStream(img);								
			screenshotStream.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));																																																												
			screenshotStream.close();
			Reporter.log("<a href='"+img.getAbsolutePath()+"'><img src='"+ img.getAbsolutePath()+"' height='200' width='200'/> </a>");
		}
	}

	//This method will close all browsers. 
	@AfterSuite
	public void tearDown() {
		driver.close();
		driver.quit();
	}
}
