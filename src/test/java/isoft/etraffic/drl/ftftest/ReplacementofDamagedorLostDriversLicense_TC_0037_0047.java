package isoft.etraffic.drl.ftftest;

import static org.testng.Assert.assertTrue;
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
import isoft.etraffic.drl.ftfpages.ReplacementofDamagedorLostDriversLicensePage;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.ChangeVehicleOwnershipPage;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class ReplacementofDamagedorLostDriversLicense_TC_0037_0047 {
	String username = "rta10711";
	String center = "مؤسسة الترخيص - بر دبي";
	String trafficFileNo = "12505481";
	WebDriver driver;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	ChangeVehicleOwnershipPage changeVehicleOwnershipPage;
	ReplacementofDamagedorLostDriversLicensePage replacementPage;
	List<String> transactionsLst = new ArrayList<String>();

	@Test
	public void DRL_TC_0037() throws ClassNotFoundException, InterruptedException, SQLException {
		
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);
		
		trafficFileNo = dbQueries.getTrfFile_0037();
		
		commonPage = new CommonPage(driver);
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFileNo);
		commonPage.gotoMainService("استبدال رخصة القيادة تالفة أو مفقودة");
		replacementPage=new ReplacementofDamagedorLostDriversLicensePage(driver);
		replacementPage.proceedTrs();

		transactionsLst.remove(transactionsLst.size() - 1);
		transactionsLst.add(commonPage.getTransactionId());
		
		commonPage.payFTF();
		assertTrue(commonPage.transactionFeesAssertion(120));
	}
	
	@Test
	public void DRL_TC_0074() throws ClassNotFoundException, InterruptedException, SQLException {
		
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);
		
		trafficFileNo = dbQueries.getTrfFile_0037();
		
		commonPage = new CommonPage(driver);
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFileNo);
		commonPage.gotoMainService("استبدال رخصة القيادة تالفة أو مفقودة");
		replacementPage=new ReplacementofDamagedorLostDriversLicensePage(driver);
		replacementPage.proceedTrs();

		transactionsLst.remove(transactionsLst.size() - 1);
		transactionsLst.add(commonPage.getTransactionId());
		
		commonPage.payFTF();
		assertTrue(commonPage.transactionFeesAssertion(120));
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
		//driver.quit();
	}
}
