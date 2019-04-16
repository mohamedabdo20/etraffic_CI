package isoft.etraffic.cta.ftftest;

import java.awt.AWTException;
import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.cta.ftfpages.AddBankGuaranteePage;
import isoft.etraffic.cta.sddipages.ContractsNavigationPage;
import isoft.etraffic.db.DBQueries;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;


public class AddBankGurantee {
	WebDriver driver;
	
	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
	@Optional("CHROME") String browser, @Optional("en") String lang) throws Exception {
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;

	}

	String username = "kareem";// "rta10686";
	String center = "اينوك";
	DBQueries dbQuery = new DBQueries();
	LoginFTFPage loginPage;
	ContractsNavigationPage contractnavigation;
	
	@Parameters({ "username","password","centerName" })		 
	@Test
	public void addbankgurantee(String username , String password , String centerName) throws InterruptedException, ClassNotFoundException, SQLException, AWTException {

		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQuery.getUserPassword(username), center);
		
		AddBankGuaranteePage addbankgua = new AddBankGuaranteePage(driver);
		addbankgua.addbankguaranteepage();
		contractnavigation = new ContractsNavigationPage(driver);
	
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, InterruptedException {
		TestBase testBase = new TestBase();
		testBase.tearDown(result);
		driver = testBase.driver;
	}
	

}
