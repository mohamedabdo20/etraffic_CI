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
import isoft.etraffic.cta.sddipages.PaymentCreaditCard;
import isoft.etraffic.cta.sddipages.ReviewTLPage;
import isoft.etraffic.data.ExcelReader;
import isoft.etraffic.db.CtaDBQueries;
import isoft.etraffic.testbase.TestBase;

public class ReviewTLTest {
	WebDriver driver;
	
	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
	@Optional("CHROME") String browser, @Optional("en") String lang) throws Exception {
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;

	}
	
	@DataProvider(name = "ReviewTL")
	public Object[][] ActivityData(ITestContext context) throws IOException {
		 String ExcelfileName = context.getCurrentXmlTest().getParameter("filename");
		// get data from Excel Reader class
		ExcelReader ER = new ExcelReader();
		System.out.println(ExcelfileName);
		int TotalNumberOfCols = 2;
		String sheetname = "ReviewTL";
		return ER.getExcelData(ExcelfileName, sheetname, TotalNumberOfCols);}


	@Test(dataProvider="ReviewTL")
	public void ReviewTL(String transaction_No, String order_no) throws InterruptedException, ClassNotFoundException, SQLException {
		
		ReviewTLPage ReviewObject = new ReviewTLPage(driver);
		//NewTLNOCPage NOCpage = new NewTLNOCPage(driver);
		  
		ReviewObject.ReviewTL(transaction_No, order_no); 
		
		DeliveryMethodPage DeliveryObject = new DeliveryMethodPage(driver);
		DeliveryObject.delivermethod("0501234657", "04065858585", "test@test.com", "test@test.com");
		 
		// Pay by credit card
		
		PaymentCreaditCard payment = new PaymentCreaditCard(driver);
		payment.paymentcreaditcard(driver);
		Thread.sleep(30000);
		
		// get certification No
		
		CtaDBQueries getorderno = new CtaDBQueries();
		String certificatenumber = getorderno.getcertificateno(order_no);
		System.out.println("Certificate No "+certificatenumber);
		System.out.println("Order No "+order_no);
	}
	
	public void ReviewTLwithoutpay(String transaction_No, String order_no) throws InterruptedException, ClassNotFoundException, SQLException {
		
		ReviewTLPage ReviewObject = new ReviewTLPage(driver);
		//NewTLNOCPage NOCpage = new NewTLNOCPage(driver);
		  
		ReviewObject.ReviewTL(transaction_No, order_no); 
		
		DeliveryMethodPage DeliveryObject = new DeliveryMethodPage(driver);
		DeliveryObject.delivermethod("0501234657", "04065858585", "test@test.com", "test@test.com");
		
		// get certification No
		
		CtaDBQueries getorderno = new CtaDBQueries();
		String certificatenumber = getorderno.getcertificateno(order_no);
		System.out.println("Certificate No "+certificatenumber);
		System.out.println("Order No "+order_no);
	}
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, InterruptedException {
		TestBase testBase = new TestBase();
		testBase.tearDown(result);
		driver = testBase.driver;
	}

}
