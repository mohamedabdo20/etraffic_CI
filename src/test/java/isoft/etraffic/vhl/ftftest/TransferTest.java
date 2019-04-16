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
import isoft.etraffic.enums.OldPlateStatus;
import isoft.etraffic.enums.PlateCategory;
import isoft.etraffic.enums.VHLTransaction;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.enums.VehicleWeight;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.RegisteredVehicleSelectionPage;
import isoft.etraffic.vhl.ftfpages.RegistrationPage;

public class TransferTest {
	// Test Data
	String username = "rta13580";
	String center = "مؤسسة الترخيص - ديرة";
	VehicleClass vehicleClass;
	PlateCategory plateCategoryId;
	VehicleWeight vehicleWeight;
	OldPlateStatus oldPlateStatus = OldPlateStatus.Reserved;
	String electricVehicleOwner = "13965518";
	boolean isOrganization;

	String trafficFile, plateCategory, plateCode, plateNumber, chassis, weight, testDateTime;

	JavascriptExecutor js;
	LoginFTFPage loginPage;
	CommonPage commonPage;
	RegistrationPage registrationPage;
	RegisteredVehicleSelectionPage registeredVehiclePage;
	DBQueries dbQueries = new DBQueries();
	WebDriver driver;
	List<String> transactionsLst = new ArrayList<String>();
	ExcelReader ER = new ExcelReader();
	int TotalNumberOfCols = 6;
	String ExcelfileName, sheetname = "Transfer";

	public void setup() throws Exception {
		String[] vehicle = dbQueries.getVehicle(vehicleClass, vehicleWeight, plateCategoryId, isOrganization, false);
		trafficFile = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = vehicle[3];
		chassis = vehicle[4];
		weight = vehicle[5];

		dbQueries.addInsurance(chassis, vehicleClass);
		dbQueries.removeBlocker(trafficFile);
	}

	@DataProvider(name = "Transfer")
	public Object[][] vehicleData(ITestContext context) throws IOException {
		// get data from Excel Reader class
		ExcelfileName = context.getCurrentXmlTest().getParameter("filename");
		return ER.getExcelData(ExcelfileName, sheetname, TotalNumberOfCols);

	}

	public Object[][] vehicleData() throws IOException {
		// get data from Excel Reader class
		ExcelReader ER = new ExcelReader();
		int TotalNumberOfCols = 6;
		String ExcelfileName = "vhl";
		String sheetname = "Transfer";
		return ER.getExcelData(ExcelfileName, sheetname, TotalNumberOfCols);
	}

	@Test(dataProvider = "Transfer")
	public void transferVehicle(String vehicleClassValue, String plateCategoryValue, String vehicleWeightRange,
			String isOrganizationValue, String newOwnertrafficFileNo, String excpectedFees) throws Exception {
		vehicleClass = dbQueries.getVehicleClassEnDescription(vehicleClassValue);
		vehicleWeight = dbQueries.setVehicleWeightEnum(vehicleWeightRange);
		plateCategoryId = dbQueries.getPlateCategoryEnum(plateCategoryValue);
		isOrganization = Boolean.parseBoolean(isOrganizationValue);

		setup();

		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoPlateService("شهادة تحويل");

		registeredVehiclePage = new RegisteredVehicleSelectionPage(driver);
		if (newOwnertrafficFileNo.equals("NA"))
			registeredVehiclePage.transfer("عجمان", oldPlateStatus);
		else
			registeredVehiclePage.transferToNewOwner("عجمان", oldPlateStatus, newOwnertrafficFileNo);

		if (commonPage.isBRShown()) {
			transactionsLst.remove(transactionsLst.size() - 1);
			transactionsLst.add(commonPage.getBRText());
			assertTrue(false);
		}

		if (oldPlateStatus.equals(OldPlateStatus.ReturntoRTA) || oldPlateStatus.equals(OldPlateStatus.Reserved)) {
			commonPage.printApplicationForm();
			commonPage.goToPayment();
		}

		transactionsLst.remove(transactionsLst.size() - 1);
		transactionsLst.add(commonPage.getTransactionId());

		commonPage.payFTF();

		// if (!commonPage.transactionFeesAssertion(Integer.parseInt(excpectedFees),
		// Integer.parseInt(excpectedFees))) {
		// String s = (transactionsLst.get(transactionsLst.size() - 1) + " - Fess
		// Faliure");
		// transactionsLst.remove(transactionsLst.size() - 1);
		// transactionsLst.add(s);
		// }
	}

	// @Parameters({ "newOwnertrafficFileNo", "vehicleClassValue",
	// "plateCategoryValue", "vehicleWeightRange", "isOrganizationValue" })
	// @Test
	// public void transferToNewOwner(String newOwnertrafficFileNo, String
	// vehicleClassValue, String plateCategoryValue, String vehicleWeightRange,
	// String isOrganizationValue) throws Exception {
	//
	// vehicleClass = dbQueries.getVehicleClassEnDescription(vehicleClassValue);
	// vehicleWeight = dbQueries.setVehicleWeightEnum(vehicleWeightRange);
	// plateCategoryId = dbQueries.getPlateCategoryEnum(plateCategoryValue);
	// isOrganization = Boolean.parseBoolean(isOrganizationValue);
	// setup();
	//
	// loginPage = new LoginFTFPage(driver);
	// loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);
	//
	// commonPage = new CommonPage(driver);
	// commonPage.gotoHomePage();
	// commonPage.gotoSmartServices();
	// commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
	// commonPage.gotoPlateService("شهادة تحويل");
	//
	//
	// registeredVehiclePage = new RegisteredVehicleSelectionPage(driver);
	//
	// registeredVehiclePage.transferToNewOwner("عجمان", oldPlateStatus,
	// newOwnertrafficFileNo);
	//
	// if (oldPlateStatus.equals(OldPlateStatus.ReturntoRTA) ||
	// oldPlateStatus.equals(OldPlateStatus.Reserved)) {
	// commonPage.printApplicationForm();
	// commonPage.goToPayment();
	// }
	// commonPage.payFTF();
	// }
	//

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
			System.out.println("Transfer FTF trns: " + trns);
		}
		assertTrue(dbQueries.checkVLDFeesEvent(VHLTransaction.VLD_TRANSFER, testDateTime));
	}
}
