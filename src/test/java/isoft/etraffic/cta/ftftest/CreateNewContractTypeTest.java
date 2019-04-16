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
import isoft.etraffic.cta.ftfpages.CreateNewContractTypePage;
import isoft.etraffic.cta.sddipages.CtaCommonPages;
import isoft.etraffic.db.CtaDBQueries;
import isoft.etraffic.db.DBQueries;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class CreateNewContractTypeTest  
{

	CreateNewContractTypePage CreateNewContractTypeObject ;
	CtaDBQueries getTFandTLObject ;  
	CtaCommonPages common;
	String username = "kareem";// "rta10686";
	String center = "معهد الإمارات للسياقة - القصيص";
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginFTFobject ;
	WebDriver driver ;
	
	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang) throws InterruptedException {
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;}
	@Test
	private void createNewContract() throws InterruptedException, ClassNotFoundException, SQLException {
		
		getTFandTLObject = new CtaDBQueries();
		loginFTFobject = new LoginFTFPage(driver);
		CreateNewContractTypeObject = new CreateNewContractTypePage(driver);
		common = new CtaCommonPages(driver);
		loginFTFobject.loginFTF(username, dbQueries.getUserPassword(username), center);
		common.goToContractsMainMenu();
		CreateNewContractTypeObject.fillData();
		
	}

	 @AfterMethod
	 public void aftermethod(ITestResult result) throws IOException, InterruptedException {
		 TestBase testBase = new TestBase();
		 testBase.tearDown(result);
	 }
}
