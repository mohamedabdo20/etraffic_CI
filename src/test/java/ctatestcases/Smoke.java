package ctatestcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
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
		//System.setProperty("webdriver.ie.driver", "./drivers/IEDriverServer.exe");
		System.setProperty("webdriver.ie.driver", "C:\\Windows\\System32\\IEDriverServer.exe");
		/*InternetExplorerOptions options =new InternetExplorerOptions();
		options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
		options.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING,true);
		options.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL,"https://tst12c:7791/traffic/faces/jsf/auth/login.jsf");*/
		driver = new InternetExplorerDriver();
		//driver.navigate().to("https://tst12c:7791/traffic/faces/jsf/auth/login.jsf");
		//driver.quit();
		
	}
	/*@Test
	public void FailedTC()
	{
		Assert.assertEquals("1","2");
		
	}
	*/
	
}
