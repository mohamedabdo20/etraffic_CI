package isoft.etraffic.drl.sdditest;

import static org.testng.Assert.assertEquals;
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
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;


public class ExperienceCertificateOnline {
	String trafficFile;//="10184041";
	
	CommonPageOnline commonPage;
	DBQueries dbQueries = new DBQueries();
	WebDriver driver;
	List<String> transactionsLst = new ArrayList<String>();

	@Test
	public void DRL_TC_0041() throws Exception {
		trafficFile = dbQueries.getTrfFile_0038();
		
		commonPage = new CommonPageOnline(driver);
		commonPage.loginByTrafficFile(trafficFile);
		commonPage.gotoLicensing();
		commonPage.gotoMyLicense();
		commonPage.gotoService("Experience cert");
		commonPage.startTransaction();

		commonPage.selectDeliveryDateFromCenterTrustedAgent(false);
		
		transactionsLst.remove(transactionsLst.size() - 1);
		transactionsLst.add(commonPage.getTransactionNumber());
		
		assertEquals(commonPage.getSDDIFees(), "70");
		
		commonPage.payOnline();
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