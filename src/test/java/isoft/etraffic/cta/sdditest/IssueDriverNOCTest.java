package isoft.etraffic.cta.sdditest;


import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import isoft.etraffic.cta.sddipages.DeliveryMethodPage;
import isoft.etraffic.cta.sddipages.IssueDriverNOCPage;
import isoft.etraffic.cta.sddipages.PaymentCreaditCard;
import isoft.etraffic.db.*;
import isoft.etraffic.testbase.TestBase;

public class IssueDriverNOCTest{
	String TrafficFile;
	String TradeLicense;

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
	public void driverNOC() throws SQLException, InterruptedException, ClassNotFoundException {
		
		CtaDBQueries getTL = new CtaDBQueries();
		getTL.getTFandTLForIssueDriverL();
		TrafficFile = "50195341";
		TradeLicense = "61140";
		
		IssueDriverNOCPage DriverNOC = new IssueDriverNOCPage(driver);
		DriverNOC.issuedrivernoc(TrafficFile,TradeLicense );

		DeliveryMethodPage DeliveryObject = new DeliveryMethodPage(driver);
		DeliveryObject.delivermethod("0501234657", "04065858585", "test@test.com", "test@test.com");

		PaymentCreaditCard payment = new PaymentCreaditCard(driver);
		payment.paymentcreaditcard(driver);
		Thread.sleep(30000);
	}
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, InterruptedException {
		TestBase testBase = new TestBase();
		testBase.tearDown(result);
		driver = testBase.driver;
	}
}
