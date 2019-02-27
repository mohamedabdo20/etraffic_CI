package isoft.etraffic.ctatestcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
		driver.quit();}
	
	@Test
	public void PassedTC()
	{
		Assert.assertEquals("1","1");}
	
}
