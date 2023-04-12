package login;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.apache.commons.io.FileUtils;

public class NewTest {
	WebDriver driver;

	@BeforeSuite
	public void setUp() {
		driver = new EdgeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.saucedemo.com");
	}

	@DataProvider(name = "dp")
	public static Object[][] primeNumbers() {
		return new Object[][] { { "standard_user", "secret_sauce" } };
//	      ,{"locked_out_user", "secret_sauce"}, {"problem_user", "secret_sauce"}, {"performance_glitch_user", "secret_sauce"}};
	}

	@Test(dataProvider = "dp")
	public void testMethod(String strUN, String strPwd) throws Exception {
		driver.findElement(By.xpath("//*[@id='user-name']")).clear();
		driver.findElement(By.xpath("//*[@id='user-name']")).sendKeys(strUN);
		driver.findElement(By.xpath("//*[@id='password']")).clear();
		driver.findElement(By.xpath("//*[@id='password']")).sendKeys(strPwd);
		driver.findElement(By.xpath("//*[@id='login-button']")).click();
		Thread.sleep(2000);
		takeScreenshot(driver);
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

	public static void takeScreenshot(WebDriver Driver) throws Exception {
		String timeStamp;
		File screenShotName;
		File scrFile = ((TakesScreenshot) Driver).getScreenshotAs(OutputType.FILE);
		// The below method will save the screen shot in d drive with name
		// "screenshot.png"
		timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		screenShotName = new File(
				"C:\\Users\\Administrator\\eclipse-workspace\\login\\screenshots\\" + timeStamp + ".png");
		FileUtils.copyFile(scrFile, screenShotName);

		String filePath = screenShotName.toString();
//		String path = "<img src="\"filePath://"" alt="\"\"/" />";
		String path = filePath;
		path="This is the path";
		Reporter.log(path);

	}

	@AfterSuite
	public void tearDown() {
		driver.close();
		driver.quit();
	}
}
