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
import isoft.etraffic.enums.OldPlateStatus;
import isoft.etraffic.enums.PlateCategory;
import isoft.etraffic.enums.PlateSize;
import isoft.etraffic.enums.ReplacedPlate;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.enums.VehicleWeight;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.ReplacementLostDamagedPlatePage;

public class ReplacementLostDamagedPlateTest {

	String username = "rta13580";
	String center = "مؤسسة الترخيص - ديرة";
	VehicleClass vehicleClass = VehicleClass.LightVehicle;
	PlateCategory plateCategoryId = PlateCategory.Private;
	VehicleWeight vehicleWeight = VehicleWeight.Any;
	OldPlateStatus oldPlateStatus = OldPlateStatus.Lost;
	boolean isOrganization = false;

	String trafficFile, plateCategory, plateNumber, plateCode, chassis, weight;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	ReplacementLostDamagedPlatePage replacementPage;
	WebDriver driver;
	List<String> transactionsLst = new ArrayList<String>();

	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang)
			throws ClassNotFoundException, SQLException, InterruptedException {
		String[] vehicle = dbQueries.getVehicle(vehicleClass, vehicleWeight, plateCategoryId, isOrganization);
		trafficFile = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = vehicle[3];
		chassis = vehicle[4];
		weight = vehicle[5];
		dbQueries.removeBlocker(trafficFile);

		transactionsLst.add("");
		System.out.println("transactionsLst.size(): " + transactionsLst.size());
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;
	}

	@Test
	public void replaceLostDamagedPlate() throws InterruptedException, ClassNotFoundException, SQLException {

		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoPlateService("رقم بدل فاقد");

		replacementPage = new ReplacementLostDamagedPlatePage(driver);
		replacementPage.selectOldPlateStatus(oldPlateStatus);
		replacementPage.clickproceedBtn();

		if (commonPage.isBRShown()) {
			transactionsLst.remove(transactionsLst.size() - 1);
			transactionsLst.add(commonPage.getBRText());
			assertTrue(false);
		}

		commonPage.selectLostPlate_PStrategy(false, ReplacedPlate.Front, PlateSize.Long, PlateSize.Long);
		commonPage.waitImmediateDeliveryBtn();
		commonPage.clickContinue_PStrategy();

		if (commonPage.isBRShown()) {
			transactionsLst.remove(transactionsLst.size() - 1);
			transactionsLst.add(commonPage.getBRText());
			assertTrue(false);
		}

		transactionsLst.remove(transactionsLst.size() - 1);
		transactionsLst.add(commonPage.getTransactionId());

		commonPage.payFTF();
		assertTrue(commonPage.transactionFeesAssertion(130, 130));
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
