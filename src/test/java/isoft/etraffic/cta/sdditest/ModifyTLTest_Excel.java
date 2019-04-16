package isoft.etraffic.cta.sdditest;

import java.awt.AWTException;
import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import isoft.etraffic.cta.sddipages.AddPersonPage;
import isoft.etraffic.cta.sddipages.CtaCommonPages;
import isoft.etraffic.cta.sddipages.DeliveryMethodPage;
import isoft.etraffic.cta.sddipages.ModifyTLNOCPage;
import isoft.etraffic.cta.sddipages.NewTLNOCPage;
import isoft.etraffic.cta.sddipages.PaymentCreaditCard;
import isoft.etraffic.cta.sddipages.RenewTLPage;
import isoft.etraffic.cta.sddipages.ReviewTLPage;
import isoft.etraffic.cta.sddipages.UpdateTLpage;
import isoft.etraffic.data.ExcelReader;
import isoft.etraffic.db.CtaDBQueries;
import isoft.etraffic.testbase.TestBase;

@Epic("Master test cases Execution")
@Feature("Modify trade license full cycle")
public class ModifyTLTest_Excel {


	WebDriver driver;
	String ExcelfileName ;

	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang) throws Exception {
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;

	}
	@DataProvider(name = "ModifyTL")
	public Object[][] ActivityData(ITestContext context) throws IOException {
		 String ExcelfileName = context.getCurrentXmlTest().getParameter("filename");
		// get data from Excel Reader class
		ExcelReader ER = new ExcelReader();
		System.out.println(ExcelfileName);
		int TotalNumberOfCols = 2;
		String sheetname = "ModifyTL";
		return ER.getExcelData(ExcelfileName, sheetname, TotalNumberOfCols);}

	CtaCommonPages common = new CtaCommonPages(driver);

	@Test(dataProvider="ModifyTL")
	public void ModifyTLChangeCompName(String TF , String TL)
			throws ClassNotFoundException, SQLException, InterruptedException, AWTException {

		ModifyTLNOCPage modifyTLObject = new ModifyTLNOCPage(driver);
		NewTLNOCPage NOCpage = new NewTLNOCPage(driver);
		// NewNocApprovals approve = new NewNocApprovals();
		CtaDBQueries dbqueries = new CtaDBQueries();

		System.out.println("----------------Modify Trade License----------------");
		dbqueries.getTFandTLForModify(TF);


		modifyTLObject.ModifyTLInitiate(TF, TL);

		// Modify company name

		modifyTLObject.changecompanyName("Automation EngName", "شركة اتوميشن");
		
		PaymentCreaditCard payment = new PaymentCreaditCard(driver);
		payment.paymentcreaditcard(driver);
		Thread.sleep(5000);

		// get certification No
/*
		dbqueries.getcertificateno(AppNo);
		String certificatenumber = GetTFAndTLObject.certno;
		
		System.out.println("Certificate No: " + certificatenumber);
		System.out.println("Application No: " + AppNo);
		
		String TrxID = NOCpage.TraxID();
		String AppNo = NOCpage.AppNo();
		System.out.println("Transaction ID" + TrxID);
		System.out.println("Appication No" + AppNo);

		// Approve EPS
		dbqueries.securityapproval(AppNo);
		dbqueries.EPSapproval(TrxID);
		dbqueries.TRXupdateStatus(AppNo, "3");
		Thread.sleep(1000);

		// Review TL
		System.out.println("----------------Review Trade License----------------");
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");

		ReviewTLPage ReviewObject = new ReviewTLPage(driver);
		ReviewObject.ReviewTL(TrxID, AppNo);

		DeliveryMethodPage DeliveryObject = new DeliveryMethodPage(driver);
		DeliveryObject.delivermethodwithoutpay("0501234657", "04065858585", "test@test.com", "test@test.com");



		// get certification No
		dbqueries.getcertificateno(AppNo);
		String certificatenumber = dbqueries.certno;

		System.out.println("Certificate No: " + certificatenumber);
		System.out.println("Application No: " + AppNo);

		// Update Trade License

		System.out.println("----------------Update Trade License----------------");
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");
		UpdateTLpage UpdateTLobject = new UpdateTLpage(driver);
		UpdateTLobject.searchForComp(certificatenumber, AppNo);
		UpdateTLobject.fillTLData(AppNo, "شركة جديدة", "New Company", "01-01-2018", "01-01-2020", "0506242628",
				"04065958684", "test.com", "15619849", "Company Address");
		UpdateTLobject.submitTRX();

		String UpdateTrxID = NOCpage.UpdateTraxID();
		System.out.println("New Transaction Number: " + UpdateTrxID);
		dbqueries.EPSapproval(UpdateTrxID);
		
		System.out.println("----------------Review Trade License After Update----------------");
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");
		ReviewObject.ReviewTL(UpdateTrxID, AppNo);
		ReviewObject.SubmitFees();
		*/
		System.out.println("----------------Modify Trade License cycle finished successfully----------------");
	}

	@Test(dataProvider = "modifydata")
	public void ModifyTLAddManager(String Traffic_file, String Trade_license)
			throws ClassNotFoundException, SQLException, InterruptedException, AWTException {

		ModifyTLNOCPage modifyTLObject = new ModifyTLNOCPage(driver);
		NewTLNOCPage NOCpage = new NewTLNOCPage(driver);
		CtaDBQueries dbqueries = new CtaDBQueries();
		AddPersonPage addperson = new AddPersonPage(driver);

		// get traffic file and trade license to modify

		modifyTLObject.ModifyTLInitiate(Traffic_file, Trade_license);

		// Modify address

		addperson.addOwnerOrManager("مدير تعديل", "Manager update", "161616116", "01-01-1980", "12365478903215",
				"manager");
		String TrxID = NOCpage.TraxID();
		String AppNo = NOCpage.AppNo();
		System.out.println("Transaction ID" + TrxID);
		System.out.println("Appication No" + AppNo);

		// Approve EPS
		dbqueries.securityapproval(AppNo);
		dbqueries.EPSapproval(TrxID);
		dbqueries.TRXupdateStatus(AppNo, "3");

		// GP.closehappinies();
		Thread.sleep(1000);
		NOCpage.backtoDashboard();
		// Review TL
		ReviewTLPage ReviewObject = new ReviewTLPage(driver);
		ReviewObject.ReviewTL(TrxID, AppNo);

		DeliveryMethodPage DeliveryObject = new DeliveryMethodPage(driver);
		DeliveryObject.delivermethod("0501234657", "04065858585", "test@test.com", "test@test.com");

		ReviewObject.SubmitFees();
		// GP.closehappinies();

		// get transaction ID and Application No

		RemoteWebElement orderNoElm = (RemoteWebElement) driver
				.findElement(By.xpath("//strong[contains(.,'Application Reference Number')]"));
		AppNo = orderNoElm.getText().trim().split(":")[1];

		// get certification No

		String certificatenumber = dbqueries.getcertificateno(AppNo);
		// Pay by credit CARD

		PaymentCreaditCard pay = new PaymentCreaditCard(driver);
		pay.paymentcreaditcard(driver);
		Thread.sleep(2000);

		String UpdateTrxID = NOCpage.UpdateTraxID();
		System.out.println("New Transaction Number : " + UpdateTrxID);
		dbqueries.EPSapproval(UpdateTrxID);
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");
		ReviewObject.ReviewTL(UpdateTrxID, AppNo);
		ReviewObject.SubmitFees();

	}

	@Test(dataProvider = "modifydata")
	public void ModifyTLDeleteMember(String Traffic_file, String Trade_license)
			throws ClassNotFoundException, SQLException, InterruptedException, AWTException {

		ModifyTLNOCPage modifyTLObject = new ModifyTLNOCPage(driver);
		NewTLNOCPage NOCpage = new NewTLNOCPage(driver);

		CtaDBQueries dbqueries = new CtaDBQueries();

		// get traffic file and trade license to modify

		modifyTLObject.ModifyTLInitiate(Traffic_file, Trade_license);
		modifyTLObject.deleteMember();

		String TrxID = NOCpage.TraxID();
		String AppNo = NOCpage.AppNo();

		System.out.println("Transaction ID" + TrxID);
		System.out.println("Appication No" + AppNo);

		// Approve EPS
		dbqueries.securityapproval(AppNo);
		dbqueries.EPSapproval(TrxID);
		dbqueries.TRXupdateStatus(AppNo, "3");

		ReviewTLPage ReviewObject = new ReviewTLPage(driver);
		ReviewObject.ReviewTL(TrxID, AppNo);

		DeliveryMethodPage DeliveryObject = new DeliveryMethodPage(driver);
		DeliveryObject.delivermethod("0501234657", "04065858585", "test@test.com", "test@test.com");

		ReviewObject.SubmitFees();

		PaymentCreaditCard pay = new PaymentCreaditCard(driver);
		pay.paymentcreaditcard(driver);
		Thread.sleep(2000);

	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, InterruptedException {
		TestBase testBase = new TestBase();
		testBase.tearDown(result);
		driver = testBase.driver;
	}
}
