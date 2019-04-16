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

import isoft.etraffic.cta.sddipages.AddUsedVehiclesPage;
import isoft.etraffic.cta.sddipages.MyContractPage;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;

public class AddUsedVehiclesTest {
	WebDriver driver;
	
	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
	@Optional("CHROME") String browser, @Optional("en") String lang) throws Exception {
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;

	}

	String TRF = "50195341";

	@Test
	public void addusedvehiclesoncontract() throws SQLException, InterruptedException, ClassNotFoundException {

		
		CommonPageOnline onlinlogin = new CommonPageOnline(driver);
		onlinlogin.loginByTrafficFile(TRF);
		
		MyContractPage mycontract = new MyContractPage(driver);
		mycontract.accessMyContract();
		
		AddUsedVehiclesPage addusedvhl = new AddUsedVehiclesPage(driver);
		addusedvhl.addusedvehicles();
				

}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, InterruptedException {
		TestBase testBase = new TestBase();
		testBase.tearDown(result);
		driver = testBase.driver;
	}
	
	
}
