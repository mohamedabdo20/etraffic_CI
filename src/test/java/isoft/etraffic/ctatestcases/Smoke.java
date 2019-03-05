package isoft.etraffic.ctatestcases;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import isoft.etraffic.data.ExcelReader;


public class Smoke   {
	@DataProvider(name = "CTA_Activities")
	public static Object[][] ActivityData() throws IOException {
		// get data from Excel Reader class
		ExcelReader ER = new ExcelReader();
		ER.TotalNumberOfCols = 2;
		ER.sheetname = "cta_activities";
		return ER.getExcelData();
		}
	
	WebDriver driver;
	@Test(dataProvider = "CTA_Activities")
	public void IETest(String Activity, String Obligation)
	{
		System.setProperty("webdriver.ie.driver", "./drivers/IEDriverServer.exe");
		driver = new InternetExplorerDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://tst12c:7791/traffic/faces/jsf/auth/login.jsf");
		driver.get("javascript:document.getElementById('overridelink').click();");
		System.out.println(Activity + " " + Obligation);
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
