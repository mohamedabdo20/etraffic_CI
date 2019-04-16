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
import isoft.etraffic.cta.sddipages.DeliveryMethodPage;
import isoft.etraffic.cta.sddipages.PaymentCreaditCard;
import isoft.etraffic.cta.sddipages.ReviewTLPage;
import isoft.etraffic.cta.sddipages.TemporaryEventPermitPage;
import isoft.etraffic.db.CtaDBQueries;
import isoft.etraffic.testbase.TestBase;

public class TemporaryEventPermitTest  {
	
	WebDriver driver;
	
	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
	@Optional("CHROME") String browser, @Optional("en") String lang) throws Exception {
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;

	}
	
	@Description("Make new Temporary Event Permit (Intertanment) cycle ")
	@Step("This is new intertanment permit")
	@Test
	public void temporaryEventPermit() throws InterruptedException, ClassNotFoundException, SQLException {
		System.out.println("----------------Start Service Temporary Event Permit----------------");
		TemporaryEventPermitPage EventPermitobject = new TemporaryEventPermitPage(driver);
		CtaDBQueries dbqueries = new CtaDBQueries(); 
		EventPermitobject.temporaryEventPermitPage();
		
		String TrxID = EventPermitobject.TraxID();
		String AppNo = EventPermitobject.AppNo();
		System.out.println("Transaction ID: " + TrxID);
		System.out.println("Application No: " + AppNo);
		
		//Update Status For EPS
		System.out.println("----------------Approve EPS ----------------");
		dbqueries.EPSapproval(TrxID);
		dbqueries.TRXupdateStatus(AppNo, "3");
		Thread.sleep(5000);
		
		//Review Request
		System.out.println("----------------Review Trade License----------------");
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");
		ReviewTLPage ReviewObject = new ReviewTLPage(driver);
		ReviewObject.ReviewTL(TrxID, AppNo);

		//Delivery Method
		DeliveryMethodPage DeliveryObject = new DeliveryMethodPage(driver);
		DeliveryObject.delivermethod("0501234657", "04065858585", "test@test.com", "test@test.com");

		//Payment By Credit Card
		System.out.println("----------------Credit Card Payment----------------");
		PaymentCreaditCard payment = new PaymentCreaditCard(driver);
		payment.paymentcreaditcard(driver);
		

	}
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, InterruptedException {
		TestBase testBase = new TestBase();
		testBase.tearDown(result);
		driver = testBase.driver;
	}
	
}
