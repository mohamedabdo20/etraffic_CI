package isoft.etraffic.cta.ftftest;

import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.cta.ftfpages.AddNewContractPage;
import isoft.etraffic.cta.sddipages.ContractsNavigationPage;
import isoft.etraffic.db.CtaDBQueries;
import isoft.etraffic.db.DBQueries;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class AddNewContractTestCase {

	AddNewContractPage addcontractObject ;
	CtaDBQueries getTFandTLObject ;  
	String username = "kareem";// "rta10686";
	String center = "معهد الإمارات للسياقة - القصيص";
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginFTFobject ;
	ContractsNavigationPage OpenContract ;
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
	public void addnewFRCcontract() throws InterruptedException, SQLException, ClassNotFoundException {
		
		addcontractObject = new AddNewContractPage(driver);
		getTFandTLObject = new CtaDBQueries();
		loginFTFobject = new LoginFTFPage(driver);
		OpenContract = new ContractsNavigationPage(driver);
		Thread.sleep(5000);
		loginFTFobject.loginFTF(username, dbQueries.getUserPassword(username), center);
		getTFandTLObject.getTFandTLForaddnewContract();
		String TF =getTFandTLObject.TRF;
		String TL =getTFandTLObject.TL;
		 
		OpenContract.openContractRequests(); 
		addcontractObject.fillcontractform(TF, "نوع عقد جديد", TL, "4");

	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, InterruptedException {
		TestBase testBase = new TestBase();
		testBase.tearDown(result);
		driver = testBase.driver;
	}
}
