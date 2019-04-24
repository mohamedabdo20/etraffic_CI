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

import isoft.etraffic.cta.sddipages.CtaCommonPages;
import isoft.etraffic.cta.sddipages.PayDepreciationFinesPage;
import isoft.etraffic.cta.sddipages.PaymentCreaditCard;
import isoft.etraffic.db.CtaDBQueries;
import isoft.etraffic.testbase.TestBase;

public class PayDepreciationFinesTest {
	
WebDriver driver;
@BeforeMethod
@Parameters({ "url", "browser", "lang" })
public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
@Optional("CHROME") String browser, @Optional("en") String lang) throws Exception {
	TestBase testBase = new TestBase();
	testBase.setup(url, browser, lang);
	driver = testBase.driver;

}

	
	
	CtaDBQueries getTFForfines = new CtaDBQueries();
	CtaCommonPages GP = new CtaCommonPages(driver);
 
	
	@Test
	public void payFines() throws ClassNotFoundException, SQLException, InterruptedException {
		PayDepreciationFinesPage PayFinesObject = new PayDepreciationFinesPage(driver);
		getTFForfines.getTFandTLForDepreciationFines();

		String Traffic_file = getTFForfines.TRF;
		String Trade_license = getTFForfines.TL;
		
		PayFinesObject.paydepreciationfines("50089523", "612985");
		
		PaymentCreaditCard payment = new PaymentCreaditCard(driver);
		payment.paymentcreaditcard(driver);
		Thread.sleep(30000);

	}
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, InterruptedException {
		TestBase testBase = new TestBase();
		//testBase.tearDown(result);
		driver = testBase.driver;
	}
}
