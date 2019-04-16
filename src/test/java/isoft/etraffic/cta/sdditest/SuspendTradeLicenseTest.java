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

import isoft.etraffic.cta.sddipages.NOCForSuspendTradeLicense;
import isoft.etraffic.cta.sddipages.UpdateTLpage;
import isoft.etraffic.db.CtaDBQueries;
import isoft.etraffic.testbase.TestBase;

public class SuspendTradeLicenseTest {

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
		NOCForSuspendTradeLicense suspedTL = new NOCForSuspendTradeLicense(driver);

		suspedTL.InitiateService();
		CtaDBQueries dbqueries = new CtaDBQueries();
		dbqueries.getTFandTLforsuspend();
		/*
		String TrafficFile = dbqueries.TRF;
		String TradeLicense = dbqueries.TL;
*/
		
		suspedTL.searchForCompany("50195356", "61156");
		suspedTL.filltransactionsdataforsuspend();

		String TrxID = suspedTL.TraxID();
		String AppNo = suspedTL.AppNo();

		System.out.println("Transaction ID" + TrxID);
		System.out.println("Appication No" + AppNo);

		// Approve EPS
		dbqueries.EPSapproval(TrxID);
		dbqueries.TRXupdateStatus(AppNo, "3");

		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");
		ReviewTLTest reviewTL = new ReviewTLTest();
		reviewTL.ReviewTLwithoutpay(TrxID, AppNo);
		CtaDBQueries GetTFAndTLObject = new CtaDBQueries();
		String certificatenumber = GetTFAndTLObject.getcertificateno(AppNo);
		Thread.sleep(5000);
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");
		UpdateTLpage updateTL = new UpdateTLpage(driver);
		updateTL.searchForComp(certificatenumber, AppNo);
		updateTL.submitTRX();

	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, InterruptedException {
		TestBase testBase = new TestBase();
		testBase.tearDown(result);
		driver = testBase.driver;
	}

}
