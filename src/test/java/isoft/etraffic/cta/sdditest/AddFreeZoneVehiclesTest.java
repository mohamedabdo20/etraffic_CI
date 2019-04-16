package isoft.etraffic.cta.sdditest;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.cta.sddipages.AddFreeZoneVehiclesPage;
import isoft.etraffic.cta.sddipages.CtaCommonPages;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;

public class AddFreeZoneVehiclesTest {
	WebDriver driver;
	
	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
	@Optional("CHROME") String browser, @Optional("en") String lang) throws Exception {
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;

	}
	String TRF = "50052702";
	
	@Test
	public void addfreezonevehicle() throws InterruptedException {
		
		CommonPageOnline onlinelogin = new CommonPageOnline(driver);
		onlinelogin.loginByTrafficFile(TRF);
		
		CtaCommonPages mycontract = new CtaCommonPages(driver);
		mycontract.openmycontract();
		
	AddFreeZoneVehiclesPage addnewvhl = new AddFreeZoneVehiclesPage(driver);
	addnewvhl.addfreezonevehiclespage();
	
	}
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, InterruptedException {
		TestBase testBase = new TestBase();
		testBase.tearDown(result);
		driver = testBase.driver;
	}

}
