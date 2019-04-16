package isoft.etraffic.vhl.ftftest;

import static org.testng.Assert.assertTrue;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import isoft.etraffic.data.ExcelReader;
import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.PlateSize;
import isoft.etraffic.enums.VHLTransaction;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.RegistrationPage;

public class RegistrationTest {
	String username = "rta13580";
	String center = "اينوك";

	WebDriver driver;
	JavascriptExecutor js;
	LoginFTFPage loginPage;
	CommonPage commonPage;
	RegistrationPage registrationPage;
	String chassis, sourceDate, testDateTime;
	List<String> transactionsLst = new ArrayList<String>();
	DBQueries dbQueries = new DBQueries();
	ExcelReader ER = new ExcelReader();
	int TotalNumberOfCols = 14;
	String ExcelfileName, sheetname = "registration";

	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
		SimpleDateFormat sorceDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		sourceDate = sorceDateFormat.format(cal.getTime());
		chassis = sdf.format(cal.getTime()).replace("-", "").replace(":", "") + "00";
		System.out.println("chassis: " + chassis);

		transactionsLst.add("");
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;
	}

	@DataProvider(name = "Registration")
	public Object[][] vehicleData(ITestContext context) throws IOException {
		// get data from Excel Reader class

		ExcelfileName = context.getCurrentXmlTest().getParameter("filename");
		return ER.getExcelData(ExcelfileName, sheetname, TotalNumberOfCols);
	}

	@Test(dataProvider = "Registration")
	public void registerVehicle(String trafficFile, String bookletSource, String sourceId, String vehicleClass, String vehicleDescription,
			String plateCategory, String manufacturer, String fuelSource, String fuelSubType, String vehicleWeight,
			String noOfSeats, String noOfDoors, String modelYear, String vehicleSource) throws Exception {

		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);
		dbQueries.removeBlocker(trafficFile);
		if (vehicleClass.equals("مقطورة"))
			dbQueries.addTestFotTrailer(chassis);
		else
			dbQueries.addInsurance(chassis, vehicleClass);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFile);
		commonPage.gotoMainService("تسجيل مركبة جديدة");
		registrationPage = new RegistrationPage(driver);
		registrationPage.setVehicleSourceDetails("دبي", bookletSource, sourceId, sourceDate, vehicleClass,
				vehicleDescription, plateCategory, chassis);

		commonPage.closeBRAlert();

		if (vehicleClass.equals("مركبة خفيفة") && bookletSource.equals("شهادة جمركية"))
			registrationPage.clickChassisExceptionBtn();

		registrationPage.clickProceedBtn();

		if (commonPage.isBRFiredRegistration()) {
			transactionsLst.remove(transactionsLst.size() - 1);
			transactionsLst.add(commonPage.getBRText());
			assertTrue(false);
		}

		if (bookletSource.equals("شهادة جمركية")) {
			if (vehicleClass.equals("مقطورة")) {
				registrationPage.setTrailerDetails("اليابان", "ابيض");
			}
			registrationPage.setVehicleDetails(chassis, manufacturer, "", "اليابان", modelYear, "75385296741",
					vehicleSource, fuelSource, fuelSubType, vehicleWeight, noOfDoors, noOfSeats, "ابيض", vehicleClass);
		} else {
			dbQueries.addTestUnRegisteredVehicle(chassis, dbQueries.getVehicleClassEnDescription(vehicleClass),
					vehicleWeight);
			registrationPage.setCertificateVehicleDetails(vehicleSource);
		}

		commonPage.selectNewPlateSource_PStrategy("صرف يومى");
		commonPage.clickCertifyBtn();

		if (commonPage.isBRShown()) {
			transactionsLst.remove(transactionsLst.size() - 1);
			transactionsLst.add(commonPage.getBRText());
			assertTrue(false);
		}

		if (plateCategory.equals("خصوصي")) {
			commonPage.selectNewPlates_PStrategy(false, PlateSize.Long, PlateSize.Short);
		} else {
			commonPage.skipLogoStep();
			if (plateCategory.equals("مقطورة") || plateCategory.equals("دراجة نارية"))
				commonPage.selectNewPlates_PStrategy(PlateSize.Short);
			else
				commonPage.selectNewPlates_PStrategy(PlateSize.Short, PlateSize.Short);
		}

		commonPage.clickContinue_PStrategy();

		if (commonPage.isBRShownRegistration()) {
			transactionsLst.remove(transactionsLst.size() - 1);
			transactionsLst.add(commonPage.getBRText());
			assertTrue(false);
		}

		commonPage.printApplicationForm();
		commonPage.goToPayment();

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
	public void AfterClass() throws ClassNotFoundException, SQLException {

		for (String trns : transactionsLst) {
			System.out.println("Registration FTF trns: " + trns);
		}
		assertTrue(dbQueries.checkVLDFeesEvent(VHLTransaction.REGISTRATION, testDateTime));
	}
}
