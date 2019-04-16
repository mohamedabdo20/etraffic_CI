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

import isoft.etraffic.cta.sddipages.NOCForResumeTradeLicenses;
import isoft.etraffic.cta.sddipages.PaymentCreaditCard;
import isoft.etraffic.db.CtaDBQueries;
import isoft.etraffic.testbase.TestBase;

public class ResumeTLTest  {
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
	public void suspendtradelicense() throws SQLException, InterruptedException, ClassNotFoundException {
		NOCForResumeTradeLicenses suspedTL = new NOCForResumeTradeLicenses(driver);

		suspedTL.InitiateService();
		CtaDBQueries getTFAndTL = new CtaDBQueries();
		getTFAndTL.getTFandTLforsuspend();
		/*String TrafficFile = getTFAndTL.TRF;
		String TradeLicense = getTFAndTL.TL;*/

		suspedTL.logintoservice("50195356", "61156");
		suspedTL.filltransactionsdataforsuspend();
		
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
