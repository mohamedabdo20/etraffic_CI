package isoft.etraffic.ctatestcases;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.Test;


public class Smoke   {
	WebDriver driver;
	@Test
	public void IETest()
	{
		System.setProperty("webdriver.ie.driver", "./drivers/IEDriverServer.exe");
		driver = new InternetExplorerDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://tst12c:7791/traffic/faces/jsf/auth/login.jsf");
		driver.get("javascript:document.getElementById('overridelink').click();");
		driver.quit();
	}
	
	@Test
	public void PassedTC()
	{
		Assert.assertEquals("1","1");}
	
	/*@Test
	public void FailedTC()
	{
		Assert.assertEquals("1","2");}*/
	
}
