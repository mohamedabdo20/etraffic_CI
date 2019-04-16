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

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import isoft.etraffic.cta.sddipages.NewTLNOCPage;
import isoft.etraffic.cta.sddipages.PaymentCreaditCardforRenew;
import isoft.etraffic.cta.sddipages.RenewTLPage;
import isoft.etraffic.cta.sddipages.ReviewTLPage;
import isoft.etraffic.cta.sddipages.UpdateTLpage;
import isoft.etraffic.db.CtaDBQueries;
import isoft.etraffic.testbase.TestBase;

public class RenewTLTest  {
	WebDriver driver;
	
	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
	@Optional("CHROME") String browser, @Optional("en") String lang) throws Exception {
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;

	}

	@Description("RenewTrade License cycle for each activity")
	@Step("This is Renew trade license for activity {0}, and with obligation flag {1}")
	@Test
	public void renewTLCML() throws InterruptedException, ClassNotFoundException, SQLException {

		
		CtaDBQueries dbqueries = new CtaDBQueries();
		RenewTLPage renewObject = new RenewTLPage(driver);
		System.out.println("----------------Renew Trade License----------------");
		dbqueries.getTFandTLForRenew();

		String Traffic_file = dbqueries.TRF;
		String Trade_license = dbqueries.TL;

		renewObject.RenewTLInitiate("50195616", "61419");
		renewObject.viewandacceptObligation();
		renewObject.confirmtodelivery();

		renewObject.confirmandproceed();
		PaymentCreaditCardforRenew payment = new PaymentCreaditCardforRenew(driver);
		payment.paymentcreaditcardforrenew(driver);
		Thread.sleep(30000);

		// get certification No
		NewTLNOCPage NOCpage = new NewTLNOCPage(driver);
		String TrxID = renewObject.TraxIDForRenew();
		String AppNo = NOCpage.AppNo();
		CtaDBQueries getorderno = new CtaDBQueries();
		String certificatenumber = getorderno.getcertificateno(AppNo);

		System.out.println("Certificate No: " + certificatenumber);
		System.out.println("Transaction ID : " + TrxID);
		System.out.println("Application No : " + AppNo);

		// Update Trade License
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");
		UpdateTLpage updateTLobject = new UpdateTLpage(driver);
		updateTLobject.searchForComp(certificatenumber, AppNo);
		updateTLobject.updateTLforRenewTradeLicense();
		updateTLobject.submitTRX();

		// Review After Update
		// Approve EPS
		
		dbqueries.EPSapproval(TrxID);
		dbqueries.TRXupdateStatus(AppNo, "3");
		
		String UpdateTrxID = NOCpage.UpdateTraxID();
		System.out.println("New Transaction Number: " + UpdateTrxID);
		dbqueries.EPSapproval(UpdateTrxID);
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");
		ReviewTLPage ReviewObject = new ReviewTLPage(driver);
		ReviewObject.ReviewTL(UpdateTrxID, AppNo);
		ReviewObject.SubmitFees();

	}
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, InterruptedException {
		TestBase testBase = new TestBase();
		testBase.tearDown(result);
		driver = testBase.driver;
	}

}
