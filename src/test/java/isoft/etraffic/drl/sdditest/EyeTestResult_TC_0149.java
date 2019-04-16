package isoft.etraffic.drl.sdditest;

import java.sql.SQLException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import isoft.etraffic.db.*;
import isoft.etraffic.vhl.ftfpages.*;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;
import isoft.etraffic.drl.sddipages.*;
import isoft.etraffic.testbase.TestBase;


public class EyeTestResult_TC_0149 {

	String trafficFile = "10526699";

	DBQueries dbQueries = new DBQueries();
	WebDriver driver;
	CommonPageOnline commonPage;
	EyeTestResultPage eyeTestResultPage;

	@Test
	public void addEyeTestResul() throws Exception {	
		commonPage = new CommonPageOnline(driver);
		commonPage.loginAsTrustedAgent("مركز الجابر للنظارات");
		commonPage.selectTrustedAgentService("Eye Test Result");
		commonPage.clickByLinkTxt("Proceed to service");
		commonPage.startTransactionTrustedAgent();
		
		eyeTestResultPage = new EyeTestResultPage(driver);
		eyeTestResultPage.searchByTrf(trafficFile);
		eyeTestResultPage.addResult();
		
		commonPage.payCreditCardTrustedAgeny();
	}
	
	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://test12c:7781/trfesrv/test_login.jsp") String url,
	@Optional("CHROME") String browser, @Optional("en") String lang) throws Exception {
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;

	}
}
