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

import isoft.etraffic.cta.sddipages.MyContractPage;
import isoft.etraffic.cta.sddipages.RequestToReduceVehiclesPage;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;

public class RequestToReduceVehiclesTest  {

	
	String TRF = "50189495";
	CommonPageOnline commonpage;
	WebDriver driver;
	
	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
	@Optional("CHROME") String browser, @Optional("en") String lang) throws Exception {
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;

	}
	@Test
	public void requesttoreducevehicles() throws SQLException, InterruptedException, ClassNotFoundException {

		
		commonpage = new CommonPageOnline(driver);
		commonpage.loginByTrafficFile(TRF);
		MyContractPage mycontract = new MyContractPage(driver);
		mycontract.accessMyContract();
		
		RequestToReduceVehiclesPage reducevehicles = new RequestToReduceVehiclesPage(driver);
		reducevehicles.requesttoreducevehicle();
}
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, InterruptedException {
		TestBase testBase = new TestBase();
		testBase.tearDown(result);
		driver = testBase.driver;
	}

}