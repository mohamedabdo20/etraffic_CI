package isoft.etraffic.cta.sdditest;

import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
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
import io.qameta.allure.Step;
import isoft.etraffic.cta.sddipages.AddActivityPage;
import isoft.etraffic.cta.sddipages.AddPersonPage;
import isoft.etraffic.cta.sddipages.DeliveryMethodPage;
import isoft.etraffic.cta.sddipages.NewTLNOCPage;
import isoft.etraffic.cta.sddipages.PaymentCreaditCard;
import isoft.etraffic.cta.sddipages.ReviewTLPage;
import isoft.etraffic.cta.sddipages.UpdateTLpage;
import isoft.etraffic.data.ExcelReader;
import isoft.etraffic.db.CtaDBQueries;
import isoft.etraffic.testbase.TestBase;

@Epic("Master test cases Execution")
@Feature("New trade license full cycle")
public class NewTLFullCycleTest  {

	WebDriver driver;
	String ExcelfileName ;
	//GeneralPages GP = new GeneralPages(driver);
 
	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
	@Optional("CHROME") String browser, @Optional("en") String lang) throws Exception {
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;

	}
	
	@DataProvider(name = "CTA_Activities")
	public Object[][] ActivityData(ITestContext context) throws IOException {
		 String ExcelfileName = context.getCurrentXmlTest().getParameter("filename");
		// get data from Excel Reader class
		ExcelReader ER = new ExcelReader();
		System.out.println(ExcelfileName);
		int TotalNumberOfCols = 3;
		String sheetname = "cta_activities";
		return ER.getExcelData(ExcelfileName, sheetname, TotalNumberOfCols);}

	@Description("Make new trade license cycle for each activity")
	@Step("This is new trade license for activity {0}, and with obligation flag {1}")
	@Test(dataProvider = "CTA_Activities")
	public void NewNoc(String Activity, String Obligation,String srvFee)
			throws InterruptedException, ClassNotFoundException, SQLException {
		// Create NOC for New Trade License
		System.out.println("----------------New NOC Trade License----------------");
		NewTLNOCPage NOCpage = new NewTLNOCPage(driver);
		NOCpage.NewNOCFill("Kareem", "12345679", "22-07-2018");
		AddActivityPage addactiv = new AddActivityPage(driver);
		addactiv.addactivity(Activity);
		// Add members
		AddPersonPage addnewperson = new AddPersonPage(driver);

		addnewperson.addOwnerOrManager("مالك ", "Owner", "161616116", "01-01-1980", "12365478903215", "owner");
		addnewperson.addMember("شريك ", "Member", "161616116", "01-01-1980", "1236547890321", "20");
		addnewperson.addOwnerOrManager("مدير ", "Manager", "161616116", "01-01-1980", "12365478903214", "manager");
		
		// Submit Transaction
		NOCpage.submitTransaction(Obligation);
		Thread.sleep(5000);
		// GP.closehappinies();
		CtaDBQueries GetTFAndTLObject = new CtaDBQueries();
		String TrxID = NOCpage.TraxID();
		String AppNo = NOCpage.AppNo();
		System.out.println("Activity: " + Activity);
		System.out.println("Obligation: " + Obligation);
		System.out.println("Transaction ID: " + TrxID);
		System.out.println("Application No: " + AppNo);

		// Approve EPS
		System.out.println("----------------Approve EPS and Security permissions----------------");
		
		GetTFAndTLObject.securityapproval(AppNo);
		GetTFAndTLObject.EPSapproval(TrxID);
		GetTFAndTLObject.TRXupdateStatus(AppNo, "3");

		Thread.sleep(5000);

		// Review TL
		System.out.println("----------------Review Trade License----------------");
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");
		Thread.sleep(5000);
		ReviewTLPage ReviewObject = new ReviewTLPage(driver);
		ReviewObject.ReviewTL(TrxID, AppNo);

		DeliveryMethodPage DeliveryObject = new DeliveryMethodPage(driver);
		DeliveryObject.delivermethod("0501234657", "04065858585", "test@test.com", "test@test.com");

		PaymentCreaditCard payment = new PaymentCreaditCard(driver);
		payment.paymentcreaditcard(driver);
		Thread.sleep(5000);

		// get certification No

		GetTFAndTLObject.getcertificateno(AppNo);
		String certificatenumber = GetTFAndTLObject.certno;
		
		System.out.println("Certificate No: " + certificatenumber);
		System.out.println("Application No: " + AppNo);

		// Update TL
		System.out.println("----------------Update Trade License----------------");
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");
		Thread.sleep(2000);
		UpdateTLpage UpdateTLobject = new UpdateTLpage(driver);
		UpdateTLobject.searchForComp(certificatenumber, AppNo);
		UpdateTLobject.fillTLData(AppNo, "شركة جديدة", "New Company", "01-01-2018", "01-01-2020", "0506242628",
				"04065958684", "test.com", "15619849", "Company Address");
		UpdateTLobject.submitTRX();

		String UpdateTrxID = NOCpage.UpdateTraxID();
		System.out.println("New Transaction Number: " + UpdateTrxID);
		GetTFAndTLObject.EPSapproval(UpdateTrxID);
		System.out.println("----------------Review Trade License After Update----------------");
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");
		
		ReviewObject.ReviewTL(UpdateTrxID, AppNo);
		ReviewObject.SubmitFees();
		// GP.closehappinies();

		System.out.println("New Trade license created successfuly");
		Thread.sleep(2000);
		GetTFAndTLObject.getnewTF(AppNo);
		String TF = GetTFAndTLObject.TRF;
		System.out.println("New Traffic No: " + TF);
		System.out.println("New Trade license No: " + AppNo);
		System.out.println("Actual Amout: " + payment.Amount() + " And Expected Amount: Total Amount: " + srvFee);
		Assert.assertEquals(payment.Amount(), "Total Amount: "+srvFee);
		System.out.println("----------------New Trade License cycle finished successfully----------------");
		
	}
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, InterruptedException {
		TestBase testBase = new TestBase();
		testBase.tearDown(result);
		driver = testBase.driver;
	}

}
