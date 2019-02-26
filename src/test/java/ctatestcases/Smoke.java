package ctatestcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class Smoke {
	WebDriver driver;
/*	@Test
	public void openchrome()
	{
	
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://www.googl.com");
		driver.quit();
		
	}*/
	/*@Test
	public void openFirefox()
	{
		System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
		driver = new FirefoxDriver();
		driver.get("https://tst12c:7791/traffic/faces/jsf/auth/login.jsf");
		driver.quit();
	}*/
	@Test
	public void openIE()
	{
		System.setProperty("webdriver.ie.driver", "./drivers/IEDriverServer.exe");
		driver = new InternetExplorerDriver();
		driver.get("https://tst12c:7791/traffic/faces/jsf/auth/login.jsf");
		driver.quit();
		
	}
	/*@Test
	public void FailedTC()
	{
		Assert.assertEquals("1","2");
		
	}
	*/
	
}
