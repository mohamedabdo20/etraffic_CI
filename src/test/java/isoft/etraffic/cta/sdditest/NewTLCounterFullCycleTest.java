package isoft.etraffic.cta.sdditest;

import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

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

public class NewTLCounterFullCycleTest{

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
		int TotalNumberOfCols = 2;
		String sheetname = "cta_activities";
		return ER.getExcelData(ExcelfileName, sheetname, TotalNumberOfCols);}

	@Test
	public void NewNocForCounter()
			throws InterruptedException, ClassNotFoundException, SQLException {
		//String Activity , String Obligation , String TF, String TL
		// Create NOC for New Trade License
		NewTLNOCPage NOCpage = new NewTLNOCPage(driver); 
		// get traffic file and trade license 
		CtaDBQueries dbqueries = new CtaDBQueries();
		dbqueries.getTFandTLForModify();
		
		/*String Traffic_file = dbqueries.TRF;
		String Trade_license = dbqueries.TL;
*/
		

		String Traffic_file = "50195332";
		String Trade_license = "61134";

		
		
		NOCpage.NewNOCCounterFill(Traffic_file, Trade_license, "Kareem", "12345679", "22-07-2018");
		AddActivityPage addactiv = new AddActivityPage(driver);
		addactiv.addactivity("5");

		// Add members
		AddPersonPage addnewperson = new AddPersonPage(driver);
		addnewperson.addOwnerOrManager("مدير ", "Manager", "161616116", "01-01-1980", "12365478903215", "manager");
		// Submit Transaction
		NOCpage.submitTransaction("No");
		String TrxID = NOCpage.TraxID();
		String AppNo = NOCpage.AppNo();
		System.out.println("Activity: " + "5");
		System.out.println("Obligation: " + "No");
		System.out.println("Transaction ID" + TrxID);
		System.out.println("Appication No" + AppNo);

		// Approve EPS
		dbqueries.securityapproval(AppNo);
		dbqueries.EPSapproval(TrxID);
		dbqueries.TRXupdateStatus(AppNo, "3");
		
		Thread.sleep(1000);
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");
		// Review TL
		ReviewTLPage ReviewObject = new ReviewTLPage(driver);
		ReviewObject.ReviewTL(TrxID, AppNo);

		DeliveryMethodPage DeliveryObject = new DeliveryMethodPage(driver);
		DeliveryObject.delivermethod("0501234657", "04065858585", "test@test.com", "test@test.com");

		PaymentCreaditCard payment = new PaymentCreaditCard(driver);
		payment.paymentcreaditcard(driver);
		Thread.sleep(3000);

		// get certification No
		String certificatenumber = dbqueries.getcertificateno(AppNo);
		System.out.println("Certificate No " + certificatenumber);
		System.out.println("Order No " + AppNo); 

		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");

		// Update TL
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");
		UpdateTLpage UpdateTLobject = new UpdateTLpage(driver);
		UpdateTLobject.searchForComp(certificatenumber, AppNo);
		UpdateTLobject.fillTLData(TrxID, "شركة جديدة", "New Counter", "01-01-2018", "01-01-2020", "0506242628", "04065958684", "test@test.com", "15619849", "Company Address");
		UpdateTLobject.submitTRX();

		String UpdateTrxID = NOCpage.UpdateTraxID();
		System.out.println("New Transaction Number : " + UpdateTrxID);
		dbqueries.EPSapproval(UpdateTrxID);
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");
		ReviewObject.ReviewTL(UpdateTrxID, AppNo);
		ReviewObject.SubmitFees();


	}

}
