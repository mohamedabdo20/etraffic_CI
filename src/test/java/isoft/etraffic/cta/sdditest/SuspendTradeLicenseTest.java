package isoft.etraffic.cta.sdditest;

import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.cta.sddipages.DeliveryMethodPage;
import isoft.etraffic.cta.sddipages.SuspendTLPage;
import isoft.etraffic.cta.sddipages.NewTLNOCPage;
import isoft.etraffic.cta.sddipages.PaymentCreaditCard;
import isoft.etraffic.cta.sddipages.ReviewTLPage;
import isoft.etraffic.cta.sddipages.UpdateTLpage;
import isoft.etraffic.data.ExcelReader;
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

	@DataProvider(name = "SuspendTL")
	public Object[][] ActivityData(ITestContext context) throws IOException {
		String ExcelfileName = context.getCurrentXmlTest().getParameter("filename");
		// get data from Excel Reader class
		ExcelReader ER = new ExcelReader();
		System.out.println(ExcelfileName);
		int TotalNumberOfCols = 2;
		String sheetname = "SuspendTL";
		return ER.getExcelData(ExcelfileName, sheetname, TotalNumberOfCols);
	}

	@Test(dataProvider = "SuspendTL")
	public void suspendtradelicense(String Cat_Id, String srvFee)
			throws SQLException, InterruptedException, ClassNotFoundException {
		SuspendTLPage suspedTL = new SuspendTLPage(driver);
		System.out.println("----------------Suspend Trade License----------------");
		CtaDBQueries dbqueries = new CtaDBQueries();
		// dbqueries.getTFandTLforsuspend(Cat_Id);
		dbqueries.getTFandTLForModify(Cat_Id);

		String TrafficFile = dbqueries.TRF;
		String TradeLicense = dbqueries.TL;

		suspedTL.InitiateService();
		suspedTL.searchForCompany(TrafficFile, TradeLicense);
		suspedTL.filltransactionsdataforsuspend();

		String TrxID = suspedTL.TraxID();
		String AppNo = suspedTL.AppNo();

		System.out.println("Transaction ID" + TrxID);
		System.out.println("Application No" + AppNo);

		// Approve EPS
		System.out.println("----------------Approve EPS and Security permissions----------------");
		dbqueries.securityapproval(AppNo);
		dbqueries.EPSapproval(TrxID);
		dbqueries.TRXupdateStatus(AppNo, "3");

		Thread.sleep(5000);
		// Review TL
		System.out.println("----------------Review Trade License----------------");
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");

		ReviewTLPage ReviewObject = new ReviewTLPage(driver);
		ReviewObject.ReviewTL(TrxID, AppNo);

		DeliveryMethodPage DeliveryObject = new DeliveryMethodPage(driver);
		if (srvFee == "0") {
			DeliveryObject.delivermethodwithoutpay("0501234657", "04065858585", "test@test.com", "test@test.com");
		} else {
			DeliveryObject.delivermethod("0501234657", "04065858585", "test@test.com", "test@test.com");
			PaymentCreaditCard payment = new PaymentCreaditCard(driver);
			payment.paymentcreaditcard(driver);
			Thread.sleep(5000);
		}

		Thread.sleep(5000);
		// get certification No

		dbqueries.getcertificateno(AppNo);
		String certificatenumber = dbqueries.certno;

		System.out.println("Certificate No: " + certificatenumber);
		System.out.println("Application No: " + AppNo);
		// Update Trade License
		System.out.println("----------------Update Trade License----------------");
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");
		Thread.sleep(3000);
		UpdateTLpage UpdateTLobject = new UpdateTLpage(driver);
		UpdateTLobject.searchForComp(certificatenumber, AppNo);
		UpdateTLobject.updateTLforRenewTradeLicense();
		UpdateTLobject.submitTRX();

		NewTLNOCPage NOCpage = new NewTLNOCPage(driver);
		String UpdateTrxID = NOCpage.UpdateTraxID();
		System.out.println("New Transaction Number: " + UpdateTrxID);
		dbqueries.EPSapproval(UpdateTrxID);

		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");
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
