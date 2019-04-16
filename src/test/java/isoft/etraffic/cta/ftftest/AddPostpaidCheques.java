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

import isoft.etraffic.cta.ftfpages.AddPostpaidChequesPage;
import isoft.etraffic.cta.sddipages.CtaCommonPages;
import isoft.etraffic.db.CtaDBQueries;
import isoft.etraffic.db.DBQueries;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class AddPostpaidCheques{
	CtaDBQueries getTFandTLObject ;  
	CtaCommonPages common;
	String username = "kareem";// "rta10686";
	String center = "معهد الإمارات للسياقة - القصيص";
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginFTFobject ;
	WebDriver driver;
	
	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
	@Optional("CHROME") String browser, @Optional("en") String lang) throws Exception {
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;

	}
	

	
	@Parameters({ "username","password","centerName" })
	@Test
	public void addpostpaidcheques (String username , String password , String centerName) throws InterruptedException, ClassNotFoundException, SQLException {
		
		CtaCommonPages common = new CtaCommonPages(driver);
		loginFTFobject = new LoginFTFPage(driver);
		loginFTFobject.loginFTF(username, dbQueries.getUserPassword(username), center);
		common.goToContractsMainMenu();
		common.searchForContract();
		AddPostpaidChequesPage addcheques = new AddPostpaidChequesPage(driver);
		addcheques.addpostpaidchequespage();
		//addcheques.StartDate();
		//addcheques.EndDate();
		
	}
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, InterruptedException {
		TestBase testBase = new TestBase();
		testBase.tearDown(result);
		driver = testBase.driver;
	}

}
