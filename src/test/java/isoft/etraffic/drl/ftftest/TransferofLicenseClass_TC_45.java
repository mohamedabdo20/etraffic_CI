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
import isoft.etraffic.drl.ftfpages.TransferOfLicensePage;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.ChangeVehicleOwnershipPage;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class TransferofLicenseClass_TC_45 {

	String trafficFileNo;// = "11276535";
	String username = "rta4166";
	String center = "معهد الإمارات للسياقة - القصيص";
	
	WebDriver driver;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	ChangeVehicleOwnershipPage changeVehicleOwnershipPage;
	TransferOfLicensePage transferOfLicensePage;
	List<String> transactionsLst = new ArrayList<String>();

	@Test
	public void getLostDamagedLearningPermit() throws ClassNotFoundException, InterruptedException, SQLException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);
		
		trafficFileNo = dbQueries.gettrafficFile_TC_45();
		
		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFileNo);
		commonPage.gotolicenseTab();

		transferOfLicensePage = new TransferOfLicensePage(driver);
		transferOfLicensePage.proceedTrs();
		
		if (commonPage.isBRShown()) {
			transactionsLst.remove(transactionsLst.size() - 1);
			transactionsLst.add(commonPage.getBRText());
			assertTrue(false);
		}
		
		transactionsLst.remove(transactionsLst.size()-1);
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


//DRL_TC_0143

//1-يوجد رخصة للعميل من صنف اوتوماتيك
//2- الرخصة غير منتهية
//4-تعديل الاسم بالانجليزي برقم السيناريو
//Sheet name: update name  for scenarios