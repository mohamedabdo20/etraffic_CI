package isoft.etraffic.vhl.ftftest;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
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
import isoft.etraffic.enums.PlateDesign;
import isoft.etraffic.enums.PlateSize;
import isoft.etraffic.enums.VHLTransaction;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.enums.VehicleWeight;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.RenewPage;

public class RenewTest {

	/////////////////// Test Data ////////////////////
	VehicleClass vehicleClass;
	VehicleWeight vehicleWeight;
	PlateCategory plateCategoryId;
	OldPlateStatus oldPlateStatus;
	boolean isOrganization;
	WebDriver driver;
	///////////////////////////////////////////////////
	String events = "'EV_VLD_9', 'EV_VLD_35', 'EV_VLD_36', 'EV_VLD_40', 'EV_VLD_41', 'EV_VLD_45', 'EV_VLD_60', 'EV_VLD_61', 'EV_VLD_62', 'EV_VLD_63', 'EV_VLD_66', 'EV_VLD_68', 'EV_VLD_69', 'EV_VLD_70', 'EV_VLD_76', 'EV_VLD_97', 'EV_VLD_122', 'EV_VLD_123', 'EV_VLD_131', 'EV_VLD_134', 'EV_VLD_135'";
	///////////////////////////////////////////////////
	String username = "rta13580";// "rta4733";//"ISAbdulrahman";
	String center = "ينوك تسجيل";// "مؤسسة الترخيص - ديرة";//"اينوك تسجيل جبل علي";//"مؤسسة الترخيص - ديرة"

	String plateCategory, plateNumber, plateCode, chassis, trafficFileNo, weight, testDateTime;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	RenewPage renewPage;
	List<String> transactionsLst = new ArrayList<String>();
	ExcelReader ER = new ExcelReader();
	int TotalNumberOfCols = 5;
	String ExcelfileName, sheetname = "Renew";

	@DataProvider(name = "RenewVehicle")
	public Object[][] vehicleData(ITestContext context) throws IOException {
		// get data from Excel Reader class
		ExcelfileName = context.getCurrentXmlTest().getParameter("filename");
		return ER.getExcelData(ExcelfileName, sheetname, TotalNumberOfCols);
	}

	@Test(dataProvider = "RenewVehicle")
	public void renewVehicle(String vehicleClassValue, String plateCategoryValue, String vehicleWeightRange,
			String isOrganizationValue, String newPlatesDesign)
			throws ClassNotFoundException, SQLException, InterruptedException {

		vehicleClass = dbQueries.getVehicleClassEnDescription(vehicleClassValue);
		vehicleWeight = dbQueries.setVehicleWeightEnum(vehicleWeightRange);
		plateCategoryId = dbQueries.getPlateCategoryEnum(plateCategoryValue);
		isOrganization = Boolean.parseBoolean(isOrganizationValue);

		getExpiredVehicle();

		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoRenewl();

		renewPage = new RenewPage(driver);
		renewPage.proceedTrs();

		if (commonPage.isBRShown()) {
			transactionsLst.remove(transactionsLst.size() - 1);
			transactionsLst.add(commonPage.getBRText());
			assertTrue(false);
		}

		switch (newPlatesDesign) {
		case "Current":
			renewVehicleWithCurrentDesign();
			break;
		case "NewDesign":
			renewVehicleAndSelectNewDesign();
			break;

		}
	}

	private void renewVehicleWithCurrentDesign() throws InterruptedException, ClassNotFoundException, SQLException {
		commonPage.selectPlateDesign_PStrategy(PlateDesign.Current);

		transactionsLst.remove(transactionsLst.size() - 1);
		transactionsLst.add(commonPage.getTransactionId());

		commonPage.payFTF();
	}

	private void renewVehicleAndSelectNewDesign() throws InterruptedException, ClassNotFoundException, SQLException {
		commonPage.selectPlateDesign_PStrategy(PlateDesign.New);
		if (plateCategoryId == PlateCategory.Private)
			commonPage.selectNewPlates_PStrategy(true, PlateSize.Luxury, PlateSize.Short);
		else {
			commonPage.skipLogoStep();
			if (plateCategoryId == PlateCategory.Motorcycle || plateCategoryId == PlateCategory.Trailer)
				commonPage.selectNewPlates_PStrategy(PlateSize.Short);
			else
				commonPage.selectNewPlates_PStrategy(PlateSize.Short, PlateSize.Short);
		}
		commonPage.selectCollectionCenter_PStrategy("مركز تسليم");
		commonPage.selectDeliveryDate_PStrategy();
		commonPage.clickContinue_PStrategy();

		transactionsLst.remove(transactionsLst.size() - 1);
		transactionsLst.add(commonPage.getTransactionId());
		
		commonPage.payFTF();
	}

	private void getExpiredVehicle() throws ClassNotFoundException, SQLException {
		String[] vehicle = dbQueries.getExpiredVehicle(vehicleClass, vehicleWeight, plateCategoryId, isOrganization);
		trafficFileNo = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = vehicle[3];
		chassis = vehicle[4];
		weight = vehicle[5];

		if (weight.equals("0")) {
			if (vehicleClass.equals(VehicleClass.HeavyVehicle))
				weight = "3500";
			else
				weight = "2500";
		}

		if (!vehicleClass.equals(VehicleClass.Trailer)) {
			dbQueries.addInsurance(chassis, vehicleClass);
		}

		//dbQueries.removeBlocker(trafficFileNo);
		//dbQueries.addFines(trafficFileNo,chassis);
	}

	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang) throws InterruptedException {
		transactionsLst.add("");
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;
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
	public void AfterClass() throws ClassNotFoundException, SQLException {

		for (String trns : transactionsLst) {
			System.out.println("Renew FTF trns: " + trns);
		}
		assertTrue(dbQueries.checkVLDFeesEvent(VHLTransaction.VEHICLE_RENEWAL, testDateTime));
	}
}
