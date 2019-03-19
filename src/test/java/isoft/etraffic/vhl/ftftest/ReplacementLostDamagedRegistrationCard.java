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
import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.Replacement;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.ReplacementLostDamagedOwnershipPage;

public class ReplacementLostDamagedRegistrationCard {

	String username = "rta10781";
	String center = "مؤسسة الترخيص - ديرة";
	 
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	ReplacementLostDamagedOwnershipPage replacementPage;
	String trafficFile, plateCategory, plateNumber, plateCode, chassis, weight;
	WebDriver driver;
	List<String> transactionsLst = new ArrayList<String>();

	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang)
			throws ClassNotFoundException, SQLException, InterruptedException {
		String[] vehicle = dbQueries.getVehicle();
		trafficFile = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory =  "خصوصي";
		chassis = vehicle[4];
		weight = vehicle[5];

		transactionsLst.add("");
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;
	}

	@Test
	public void replaceLostNumber() throws InterruptedException, ClassNotFoundException, SQLException {

		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoPlateService("ملكية بدل فاقد");
		replacementPage = new ReplacementLostDamagedOwnershipPage(driver);
		replacementPage.proceedTrs(Replacement.Damaged);

		if (commonPage.isBRShown()) {
			transactionsLst.remove(transactionsLst.size() - 1);
			transactionsLst.add(commonPage.getBRText());
			assertTrue(false);
		}

		transactionsLst.remove(transactionsLst.size() - 1);
		transactionsLst.add(commonPage.getTransactionId());
		
		commonPage.payFTF();
		assertTrue(commonPage.transactionFeesAssertion(70, 70));
	}
	
	@AfterMethod
	public void aftermethod() {
		driver.quit();
	}

	@AfterClass
	public void afterClass() {
		for (String trns : transactionsLst) {
			System.out.println("trns: " + trns);
		}
	}
}
