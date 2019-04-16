package isoft.etraffic.drl.ftftest;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
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
import isoft.etraffic.drl.ftfpages.PersonalIssueAppPage_TC_0009;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.*;

public class PearsonIssueApp_TC_0009_0163_0169 {
	String username = "DC315002";
	String center = "مركز كلداري لتعليم قيادة السيارات - القصيص";
	String trafficFileNo;

	WebDriver driver;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	PersonalIssueAppPage_TC_0009 personalIssueAppPage;
	List<String> transactionsLst = new ArrayList<String>();

	@Test
	public void DRL_TC_009() throws ClassNotFoundException, InterruptedException, SQLException, AWTException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);
		
		trafficFileNo = dbQueries.getTrfFile_0009();
		
		commonPage = new CommonPage(driver);
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFileNo);
		commonPage.gotoapplicantEligiblityTab();
		
		personalIssueAppPage = new PersonalIssueAppPage_TC_0009(driver);
		personalIssueAppPage.proceedTrs();
		
		transactionsLst.remove(transactionsLst.size() - 1);
		transactionsLst.add(commonPage.getTransactionId());
		
		commonPage.payFTFCashDRL();
		assertTrue(commonPage.transactionFeesAssertion(240));
	}

	//@Test
	public void DRL_TC_0163() throws ClassNotFoundException, InterruptedException, SQLException, AWTException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		trafficFileNo = dbQueries.getTrfFile_0009();
		
		commonPage = new CommonPage(driver);
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFileNo);
		commonPage.gotoapplicantEligiblityTab();
		
		personalIssueAppPage = new PersonalIssueAppPage_TC_0009(driver);
		personalIssueAppPage.proceedTrs();
		
		transactionsLst.remove(transactionsLst.size() - 1);
		transactionsLst.add(commonPage.getTransactionId());
		
		
		commonPage.payFTFCashDRL();
	}
	
	//@Test
	public void DRL_TC_0169() throws ClassNotFoundException, InterruptedException, SQLException, AWTException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		trafficFileNo = dbQueries.getTrfFile_0009();
		
		commonPage = new CommonPage(driver);
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFileNo);
		commonPage.gotoapplicantEligiblityTab();
		
		personalIssueAppPage = new PersonalIssueAppPage_TC_0009(driver);
		personalIssueAppPage.proceedTrs();
		
		transactionsLst.remove(transactionsLst.size() - 1);
		transactionsLst.add(commonPage.getTransactionId());
		
		commonPage.payFTFCashDRL();
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
		dbQueries.updateTrfFileEnName(result.getMethod().getMethodName(), trafficFileNo);
		driver.quit();
	}
}
