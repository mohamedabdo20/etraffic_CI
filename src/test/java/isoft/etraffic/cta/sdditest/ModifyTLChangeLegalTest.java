package isoft.etraffic.cta.sdditest;

import java.awt.AWTException;
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

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import isoft.etraffic.cta.sddipages.AddPersonModifyPage;
import isoft.etraffic.cta.sddipages.CtaCommonPages;
import isoft.etraffic.cta.sddipages.DeliveryMethodPage;
import isoft.etraffic.cta.sddipages.ModifyTLNOCPage;
import isoft.etraffic.cta.sddipages.NewTLNOCPage;
import isoft.etraffic.cta.sddipages.PaymentCreaditCard;
import isoft.etraffic.cta.sddipages.ReviewTLPage;
import isoft.etraffic.cta.sddipages.UpdateTLpage;
import isoft.etraffic.data.ExcelReader;
import isoft.etraffic.db.CtaDBQueries;
import isoft.etraffic.testbase.TestBase;

@Epic("Master test cases Execution")
@Feature("Modify trade license full cycle")
public class ModifyTLChangeLegalTest  {
	WebDriver driver;
	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
	@Optional("CHROME") String browser, @Optional("en") String lang) throws Exception {
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;

	}
	

	@DataProvider(name = "modifydata")
	public Object[][] ActivityData(ITestContext context) throws IOException {
		String ExcelfileName = context.getCurrentXmlTest().getParameter("filename");
		// get data from Excel Reader class
		ExcelReader ER = new ExcelReader();
		System.out.println(ExcelfileName);
		int TotalNumberOfCols = 3;
		String sheetname = "ModifyLegalClass";
		return ER.getExcelData(ExcelfileName, sheetname, TotalNumberOfCols);
	}

	CtaCommonPages common = new CtaCommonPages(driver);
	
	@Description("Modify tarde license change legal class ")
	@Test(dataProvider = "modifydata")
	public void ModifyTLChangelegalclass(String act_code , String srvFee , String legalclass)
			throws ClassNotFoundException, SQLException, InterruptedException, AWTException {
		System.out.println("----------------ModifyTLChangelegalclass----------------");
		ModifyTLNOCPage modifyTLObject = new ModifyTLNOCPage(driver);
		NewTLNOCPage NOCpage = new NewTLNOCPage(driver);
		CtaDBQueries dbqueries = new CtaDBQueries();
		// get traffic file and trade license to modify
		dbqueries.getTFandTLForModify(act_code);

		String Traffic_file = dbqueries.TRF;
		String Trade_license = dbqueries.TL;
		
		modifyTLObject.ModifyTLInitiate(Traffic_file, Trade_license);

		// Modify legal class

		modifyTLObject.changelegalclass(legalclass);
		modifyTLObject.obligation();
		modifyTLObject.submitfees();
		String TrxID = NOCpage.TraxID();
		String AppNo = NOCpage.AppNo();
		System.out.println("Transaction ID" + TrxID);
		System.out.println("Appication No " + AppNo);

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
		if (srvFee=="0") {
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

		String UpdateTrxID = NOCpage.UpdateTraxID();
		System.out.println("New Transaction Number: " + UpdateTrxID);
		dbqueries.EPSapproval(UpdateTrxID);

		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");
		ReviewObject.ReviewTL(UpdateTrxID, AppNo);
		ReviewObject.SubmitFees();
	}

	@Description("Modify tarde license change legal class and add new member")
	@Test(dataProvider = "modifydata", groups = "legalClass")
	public void ModifyTLChangelegalclassWithAddMember( String act_code , String srvFee , String legalclass) throws ClassNotFoundException, SQLException, InterruptedException, AWTException {

		ModifyTLNOCPage modifyTLObject = new ModifyTLNOCPage(driver);
		NewTLNOCPage NOCpage = new NewTLNOCPage(driver);
		CtaDBQueries dbqueries = new CtaDBQueries();
		// get traffic file and trade license to modify
		dbqueries.getTFandTLForModify(act_code);

		String Traffic_file = dbqueries.TRF;
		String Trade_license = dbqueries.TL;
		modifyTLObject.ModifyTLInitiate(Traffic_file, Trade_license);

		// Modify legal class

		modifyTLObject.changelegalclass(legalclass);

		AddPersonModifyPage addnewpersonMODIFY = new AddPersonModifyPage(driver);
		addnewpersonMODIFY.addMember("شريك جديد", "New Member", "161616116", "01-01-1980", "1236547890321", "20");

		modifyTLObject.obligation();
		modifyTLObject.submitfees();
		String TrxID = NOCpage.TraxID();
		String AppNo = NOCpage.AppNo();
		System.out.println("Transaction ID" + TrxID);
		System.out.println("Appication No " + AppNo);

		// Approve EPS
		dbqueries.securityapproval(AppNo);
		dbqueries.EPSapproval(TrxID);
		dbqueries.TRXupdateStatus(AppNo, "3");

		// GP.closehappinies();
		Thread.sleep(3000);
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");
		// Review TL
		ReviewTLPage ReviewObject = new ReviewTLPage(driver);
		ReviewObject.ReviewTL(TrxID, AppNo);

		DeliveryMethodPage DeliveryObject = new DeliveryMethodPage(driver);
		DeliveryObject.delivermethod("0501234657", "04065858585", "test@test.com", "test@test.com");

		// Pay by credit CARD
		PaymentCreaditCard pay = new PaymentCreaditCard(driver);
		pay.paymentcreaditcard(driver);
		Thread.sleep(5000);

		// get certification No

		CtaDBQueries getorderno = new CtaDBQueries();
		String certificatenumber = getorderno.getcertificateno(AppNo);

		// Update Trade License
		UpdateTLpage UpdateTLobject = new UpdateTLpage(driver);
		UpdateTLobject.searchForComp(certificatenumber, AppNo);
		UpdateTLobject.updateTLforRenewTradeLicense();
		UpdateTLobject.submitTRX();

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
