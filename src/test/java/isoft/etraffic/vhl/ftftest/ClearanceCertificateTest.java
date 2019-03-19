package isoft.etraffic.vhl.ftftest;

import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import isoft.etraffic.vhl.ftfpages.*;
import isoft.etraffic.db.DBQueries;
import isoft.etraffic.testbase.TestBase;

public class ClearanceCertificateTest {
	String username = "rta13580";
	String center = "مؤسسة الترخيص - ديرة";
	String plateNumber, plateCategory, plateCode, trafficFileNo;

	CommonPage commonPage;
	LoginFTFPage loginPage;
	ClearanceCertificatePage clearanceCertificatePage;
	WebDriver driver;
	DBQueries dbQueries = new DBQueries();
	List<String> transactionsLst = new ArrayList<String>();

	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang) throws ClassNotFoundException, SQLException, InterruptedException {
		
		String[] vehicle = dbQueries.getVehicle();
		trafficFileNo = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = "خصوصي";
		
		transactionsLst.add("");
		System.out.println("transactionsLst.size(): " + transactionsLst.size());
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;
	}

	@Test
	public void vehicleClearanceCert() throws InterruptedException, ClassNotFoundException, SQLException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoPlateService("شهادة براءة ذمة");

		clearanceCertificatePage = new ClearanceCertificatePage(driver);
		clearanceCertificatePage.proceedTrs();

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
	public void after() throws ClassNotFoundException, SQLException, InterruptedException {
		assertTrue(commonPage.transactionFeesAssertion(120, 120));
		driver.quit();
	}

	@AfterClass
	public void afterClass() {
		for (String trns : transactionsLst) {
			System.out.println("trns: " + trns);
		}
	}
}
