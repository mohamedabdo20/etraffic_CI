package isoft.etraffic.vhl.ftftest;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
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
import isoft.etraffic.enums.OldPlateStatus;
import isoft.etraffic.enums.PlateCategory;
import isoft.etraffic.enums.PlateDesign;
import isoft.etraffic.enums.PlateSize;
import isoft.etraffic.enums.PlateSource;
import isoft.etraffic.enums.VHLTransaction;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.enums.VehicleWeight;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.ChangePlateNumberPage;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class ChangePlateNumberTest {

	String username = "esam";//"rta10686";
	String center = "مؤسسة الترخيص - ديرة";

	String trafficFile, plateCategory, plateNumber, plateCode, chassis, weight;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	ChangePlateNumberPage changePlateNumberPage;
	WebDriver driver;
	List<String> transactionsLst = new ArrayList<String>();
	String testDateTime = "";

	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang)
			throws ClassNotFoundException, SQLException, InterruptedException {
//		String[] vehicle = dbQueries.getVehicle(VehicleClass.LightVehicle, VehicleWeight.LessThan3001,
//				PlateCategory.Private, false, true);
//		trafficFile = vehicle[0];
//		plateNumber = vehicle[1];
//		plateCode = vehicle[2];
//		plateCategory = vehicle[3];
//		chassis = vehicle[4];
//		weight = vehicle[5];
		
		String[] vehicle = dbQueries.getVehicleOfTRFHasPreservedPlate();
		trafficFile = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = vehicle[3];
		chassis = vehicle[4];
		weight = vehicle[5];

		//dbQueries.addFines(trafficFile, chassis);
		
		transactionsLst.add("");
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;
	}

	@Test
	public void changePlateNumber() throws InterruptedException, ClassNotFoundException, SQLException, AWTException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoPlateService("تغيير رقم اللوحة");

		changePlateNumberPage = new ChangePlateNumberPage(driver);
		changePlateNumberPage.proceedTrs(OldPlateStatus.Reserved, PlateSource.Daily);

		if (commonPage.isBRShown()) {
			if (commonPage.getBRText()
					.contains("أسف عن حدوث خطأ تقني غير متوقع أثناء تنفيذ الإجراء، تم تسجيله تحت مرجع")) {
				System.out.println(
						"أسف عن حدوث خطأ تقني غير متوقع أثناء تنفيذ الإجراء، تم تسجيله تحت مرجع  أسف عن حدوث خطأ تقني غير متوقع أثناء تنفيذ الإجراء، تم تسجيله تحت مرجع");
				commonPage.closeBRAlert();
				changePlateNumberPage.selectOldPlateStatus(OldPlateStatus.Reserved);
				changePlateNumberPage.clickProceedBtn();
			}

			if (commonPage.isBRShown()) {
				transactionsLst.remove(transactionsLst.size() - 1);
				transactionsLst.add(commonPage.getBRText());
				assertTrue(false);
			}
		}

		commonPage.selectPlateDesign_PStrategy(PlateDesign.New);
		commonPage.selectNewPlates_PStrategy(false, PlateSize.Short, PlateSize.Long);
		commonPage.clickContinue_PStrategy();

		commonPage.printApplicationForm();
		commonPage.goToPayment();

		transactionsLst.remove(transactionsLst.size() - 1);
		transactionsLst.add(commonPage.getTransactionId());

		commonPage.payFTF();
	}

//	@AfterMethod
//	public void aftermethod() {
//		driver.quit();
//	}

	@BeforeClass
	public void beforeClass() throws ClassNotFoundException, SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		testDateTime = sdf.format(cal.getTime());
	}

	@AfterClass
	public void AfterTest() throws ClassNotFoundException, SQLException {

		for (String trns : transactionsLst) {
			System.out.println("ChangePlateNumber FTF trns: " + trns);
		}
		assertTrue(dbQueries.checkVLDFeesEvent(VHLTransaction.VLD_CHANGE_NO, testDateTime));
	}
}
