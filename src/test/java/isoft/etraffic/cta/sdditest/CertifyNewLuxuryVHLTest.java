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

import isoft.etraffic.cta.sddipages.CertifyNewLuxuryVehicleModelPage;
import isoft.etraffic.cta.sddipages.DeliveryMethodPage;
import isoft.etraffic.cta.sddipages.ReviewTLPage;
import isoft.etraffic.db.CtaDBQueries;
import isoft.etraffic.testbase.TestBase;

public class CertifyNewLuxuryVHLTest  {
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
	public void certifyNewLuxuryVHLmodel() throws SQLException, InterruptedException, ClassNotFoundException {
		// CertifyNewLuxuryVehicleModel certifyPage = new
		// CertifyNewLuxuryVehicleModel(driver);
		
		CertifyNewLuxuryVehicleModelPage certifyPage = new CertifyNewLuxuryVehicleModelPage(driver);
		System.out.println("----------------Start certify new luxury service----------------");
		certifyPage.InitiateService();
		CtaDBQueries getTFAndTL = new CtaDBQueries();
		getTFAndTL.getTFandTLFordealer();
		String TrafficFile = getTFAndTL.TRF;
		String TradeLicense = getTFAndTL.TL;

		certifyPage.logintoservice(TrafficFile, TradeLicense);
		certifyPage.filltransactionsdata("KIA");

		String TrxID = certifyPage.TraxID();
		String AppNo = certifyPage.AppNo();
		System.out.println("Transaction ID" + certifyPage.TraxID());
		System.out.println("Appication No" + certifyPage.AppNo());

		// Approve for EPS
		System.out.println("----------------Approve EPS and Security permissions----------------");
		
		getTFAndTL.EPSapproval(TrxID);
		getTFAndTL.TRXupdateStatus(AppNo, "3");
		Thread.sleep(5000);
		// Review TL
		System.out.println("----------------Review Trade License----------------");
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");
		Thread.sleep(2000);
		ReviewTLPage ReviewObject = new ReviewTLPage(driver);
		ReviewObject.ReviewTL(TrxID, AppNo);

		DeliveryMethodPage DeliveryObject = new DeliveryMethodPage(driver);
		DeliveryObject.delivermethod("0501234657", "04065858585", "test@test.com", "test@test.com");
		ReviewObject.SubmitFees();
		
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, InterruptedException {
		TestBase testBase = new TestBase();
		testBase.tearDown(result);
		driver = testBase.driver;
	}

}
