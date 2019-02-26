package ctatestcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class Smoke {
	WebDriver driver;
	@Test
	public void openchrome()
	{
	
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://www.googl.com");
		driver.quit();
		
	}
	@Test
	public void FailedTC()
	{
		Assert.assertEquals("1","2");
		
	}
	
	
}
