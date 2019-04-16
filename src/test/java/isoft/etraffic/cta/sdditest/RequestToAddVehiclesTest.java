package isoft.etraffic.cta.sdditest;

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

import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.cta.sddipages.CtaCommonPages;
import isoft.etraffic.cta.ftfpages.ReviewContractRequestPage;
import isoft.etraffic.db.DBQueries;
import isoft.etraffic.testbase.TestBase;


public class RequestToAddVehiclesTest   {
	
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
	
	@Parameters({ "username","password","centerName" })	
	@Test
	public void requesttoaddvehicles(String username , String password , String centerName) throws SQLException, InterruptedException, ClassNotFoundException, AWTException {

		LoginFTFPage loginftf = new LoginFTFPage(driver);
		loginftf.loginFTF(username,password, centerName);
		
		ReviewContractRequestPage addvehicles = new ReviewContractRequestPage(driver);
		addvehicles.requestToAddVehicleForPlanDep();
		addvehicles.requestToAddVehicleForContractDep();
		addvehicles.requestToAddVehicleForFinancialDep();
		
}
	 @AfterMethod
	 public void aftermethod(ITestResult result) throws IOException, InterruptedException {
		 TestBase testBase = new TestBase();
		 testBase.tearDown(result);
	 }
}