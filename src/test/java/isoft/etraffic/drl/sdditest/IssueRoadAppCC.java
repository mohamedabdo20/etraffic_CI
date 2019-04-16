package isoft.etraffic.drl.sdditest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.drl.sddipages.IssueRoadAppPage;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;

public class IssueRoadAppCC {
	String trafficFile = "13554761";

	CommonPageOnline commonPage;
	DBQueries dbQueries = new DBQueries();
	IssueRoadAppPage issueRoadAppPage;
	WebDriver driver;
	List<String> transactionsLst = new ArrayList<String>();
	@Test
	public void testEasy() throws Exception {
		commonPage = new CommonPageOnline(driver);
		commonPage.loginAsCallCenter("rta2003");

		commonPage.gotoTab("Traffic File No.");
		commonPage.searchByTRFFileTrustedAgent(trafficFile);
		commonPage.clickFullNameChbox();
		commonPage.clickByLinkTxt("Confirm View Customer Profile");
		commonPage.gotoLicensing();
		commonPage.clickByLinkTxt("Book an Appointment");
		commonPage.clickByLinkTxt("Issue Exam");
		commonPage.clickByLinkTxt("View Schedule");
		
		issueRoadAppPage = new IssueRoadAppPage(driver);
		issueRoadAppPage.proceedTrns();
		
		commonPage.payCreditCardCallCenter();
	}
	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang)
			throws ClassNotFoundException, SQLException, InterruptedException {
		transactionsLst.add("");
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;
	}
	
	@AfterMethod
	public void aftermethod(ITestResult result) throws ClassNotFoundException, SQLException {
		System.out.println(result.getMethod().getMethodName() + " trnsNo: " + transactionsLst.get(0));
		transactionsLst.remove(transactionsLst.size() - 1);
		dbQueries.updateTrfFileEnName(result.getMethod().getMethodName(), trafficFile);
		driver.quit();
	}
}