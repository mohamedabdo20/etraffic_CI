package isoft.etraffic.cta.sdditest;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.cta.sddipages.CtaCommonPages;
import isoft.etraffic.vhl.sddipages.*;
import isoft.etraffic.testbase.TestBase;

public class CompanyDashboard{
	String TRF = "50195341";
	
	WebDriver driver;
	
	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
	@Optional("CHROME") String browser, @Optional("en") String lang) throws Exception {
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;

	}
	
	@Test
	public void navigateToCompanyDashboard() throws InterruptedException {
		
		
		CommonPageOnline onlinelogin = new CommonPageOnline(driver);
		onlinelogin.loginByTrafficFile(TRF);

		
		CtaCommonPages companydashboard = new CtaCommonPages(driver);
		companydashboard.tradeLicensePage();
		String pagename1 = driver.findElement(By.xpath("//*[@id=\"readMe\"]/div[2]/div[1]/div/div/div[1]/div/h2/strong")).getText();
		Assert.assertEquals("Trade License Details",driver.findElement(By.xpath("//*[@id=\"readMe\"]/div[2]/div[1]/div/div/div[1]/div/h2/strong")).getText());
		System.out.println("Page Name :" +pagename1);
		Thread.sleep(3000);
		driver.navigate().back();
		Thread.sleep(3000);
		
		companydashboard.vehiclesInformationPage();
		String pagename2 = driver.findElement(By.xpath("//*[@id=\"readMe\"]/div[2]/div[1]/div/div/div[1]/div/h2/strong")).getText();
		Assert.assertEquals("My Vehicles",driver.findElement(By.xpath("//*[@id=\"readMe\"]/div[2]/div[1]/div/div/div[1]/div/h2/strong")).getText());
		System.out.println("Page Name :" +pagename2);
		Thread.sleep(3000);
		driver.navigate().back();
		Thread.sleep(3000);
		
		companydashboard.driversPermitPage();
		String pagename3 = driver.findElement(By.xpath("//*[@id=\"readMe\"]/div[2]/div[1]/div/div/div[1]/div/h2/strong")).getText();
		Assert.assertEquals("Commercial Drivers",driver.findElement(By.xpath("//*[@id=\"readMe\"]/div[2]/div/div/div/div[1]/div/h2/strong")).getText());
		System.out.println("Page Name :" +pagename3);
		Thread.sleep(3000);
		driver.navigate().back();
		Thread.sleep(3000);
		
		companydashboard.employeeDetailsPage();
		String pagename4 = driver.findElement(By.xpath("//*[@id=\"readMe\"]/div[1]/div[2]/div/div/div[1]/h2/strong")).getText();
		Assert.assertEquals("Search Result",driver.findElement(By.xpath("//*[@id=\"readMe\"]/div[1]/div[2]/div/div/div[1]/h2/strong")).getText());
		System.out.println("Page Name :" +pagename4);
		Thread.sleep(3000);
		driver.navigate().back();
		Thread.sleep(3000);
		
		companydashboard.rtaNotificationPage();
		String pagename5 = driver.findElement(By.xpath("/html/body/form/div[1]/h1")).getText();
		Assert.assertEquals("RTA Notification",driver.findElement(By.xpath("/html/body/form/div[1]/h1")).getText());
		System.out.println("Page Name :" +pagename5);
		Thread.sleep(3000);
		driver.navigate().back();
		Thread.sleep(3000);
		
		companydashboard.moreServicesPage();
		String pagename6 = driver.findElement(By.xpath("//*[@id=\"largboxId\"]/div/ul[1]/li[1]/h2")).getText();
		Assert.assertEquals("My RTA eServices Account",driver.findElement(By.xpath("//*[@id=\"largboxId\"]/div/ul[1]/li[1]/h2")).getText());
		System.out.println("Page Name :" +pagename6);
	}
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, InterruptedException {
		TestBase testBase = new TestBase();
		testBase.tearDown(result);
		driver = testBase.driver;
	}

}
