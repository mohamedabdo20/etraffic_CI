package isoft.etraffic.vhl.ftftest;

 
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.VHLTransaction;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.ImportCertificatePage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class ImportCetificateTest {

	String username = "rta4733";
	String center = "اينوك تسجيل جبل علي";
	String trafficFileNo = "10010088";
	String vehicleWeight = "2500";

	LoginFTFPage loginPage;
	CommonPage commonPage;
	ImportCertificatePage importCertificatePage;
	DBQueries dbQueries = new DBQueries();
	WebDriver driver;
	List<String> transactionsLst = new ArrayList<String>();
	String testDateTime = "";

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

	@Test
	public void importVehicle() throws Exception {

		dbQueries.removeBlocker(trafficFileNo);
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFileNo);
		commonPage.gotoMainService("استيراد");

		importCertificatePage = new ImportCertificatePage(driver);
		importCertificatePage.proceedTrs(dbQueries.generateChassisNo());

		if (commonPage.isBRShown()) {
			transactionsLst.remove(transactionsLst.size() - 1);
			transactionsLst.add(commonPage.getBRText());
			assertTrue(false);
		}
		
		transactionsLst.remove(transactionsLst.size() - 1);
		transactionsLst.add(commonPage.getTransactionId());
		
		commonPage.payFTF();
	}
	
	@AfterMethod
	public void aftermethod() {
		driver.quit();
	}

	@BeforeClass
	public void beforeClass() throws ClassNotFoundException, SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		testDateTime = sdf.format(cal.getTime());
	}

	@AfterClass
	public void AfterTest() throws ClassNotFoundException, SQLException {

		for (String trns : transactionsLst) {
			System.out.println("ImportCetificate FTF trns: " + trns);
		}
		assertTrue(dbQueries.checkVLDFeesEvent(VHLTransaction.VLD_IMPORT_VLD, testDateTime));
	}
}
