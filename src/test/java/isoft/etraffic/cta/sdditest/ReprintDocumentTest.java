package isoft.etraffic.cta.sdditest;

import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.cta.sddipages.CtaCommonPages;
import isoft.etraffic.cta.sddipages.DeliveryMethodPage;
import isoft.etraffic.cta.sddipages.PaymentCreaditCard;
import isoft.etraffic.cta.sddipages.ReprintDocumentPage;
import isoft.etraffic.db.CtaDBQueries;
import isoft.etraffic.testbase.TestBase;

public class ReprintDocumentTest 
{
	public String TF;
	public String TL;
	WebDriver driver;
	CtaDBQueries getTFTL = new CtaDBQueries();
	CtaCommonPages GP = new CtaCommonPages(driver);	
	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
	@Optional("CHROME") String browser, @Optional("en") String lang) throws Exception {
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;

	}
	@Test
	private void reprintDocument() throws ClassNotFoundException, InterruptedException, SQLException 
	{
		ReprintDocumentPage printObject = new ReprintDocumentPage(driver);
		getTFTL.getGeneralTFandTL();
		TF = getTFTL.TRF;
		TL = getTFTL.TL;
		
		printObject.reprintdocumentInitiate(TF, TL);
		
		DeliveryMethodPage DeliveryObject = new DeliveryMethodPage(driver);
		DeliveryObject.delivermethod("0501234657", "04065858585", "test@test.com", "test@test.com");

		PaymentCreaditCard payment = new PaymentCreaditCard(driver);
		payment.paymentcreaditcard(driver);
		System.out.println("Actual Amout: " + payment.Amount() + " And Expected Amount: " + "Total Amount: 120 AED");
		Assert.assertEquals(payment.Amount(),  "Total Amount: 120 AED");
		
	}
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, InterruptedException {
		TestBase testBase = new TestBase();
		testBase.tearDown(result);
		driver = testBase.driver;
	}

}
