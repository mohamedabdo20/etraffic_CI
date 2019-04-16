package isoft.etraffic.vhl.ftftest;

import static org.testng.Assert.assertTrue;
import java.awt.AWTException;
import java.io.IOException;
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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.ITestContext;
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
import isoft.etraffic.vhl.ftfpages.ChangeVehicleOwnershipPage;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class ChangeVehicleOwnershipTest {

	String username = "rta13580";
	String center = "مؤسسة الترخيص - ديرة";
	
	String newOwnertrafficFileNo, testDateTime;
	VehicleClass vehicleClass;
	PlateCategory plateCategoryId;
	VehicleWeight vehicleWeight;
	OldPlateStatus oldPlateStatus = OldPlateStatus.Transfered;
	WebDriver driver;
	List<String> transactionsLst = new ArrayList<String>();

	String trafficFile, plateNumber, plateCode, plateCategory, chassis, weight;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	ChangeVehicleOwnershipPage changeVehicleOwnershipPage;
	boolean isOrganization;
	ExcelReader ER = new ExcelReader();
	int TotalNumberOfCols = 6;
	String ExcelfileName, sheetname = "ChangeVehicleOwnership";

	@DataProvider(name = "ChangeVehicleOwnership")
	public Object[][] vehicleData(ITestContext context) throws IOException {
		// get data from Excel Reader class
		ExcelfileName = context.getCurrentXmlTest().getParameter("filename");
		return ER.getExcelData(ExcelfileName, sheetname, TotalNumberOfCols);
		
	}

	@Test(dataProvider = "ChangeVehicleOwnership")
	public void changeVehicleOwnership(String vehicleClassValue, String plateCategoryValue, String vehicleWeightRange,
			String isOrganizationValue, String newOwnertrafficFileNo, String excpectedFees)
			throws InterruptedException, ClassNotFoundException, SQLException, AWTException {
		
		vehicleClass = dbQueries.getVehicleClassEnDescription(vehicleClassValue);
		vehicleWeight = dbQueries.setVehicleWeightEnum(vehicleWeightRange);
		plateCategoryId = dbQueries.getPlateCategoryEnum(plateCategoryValue);
		isOrganization = Boolean.parseBoolean(isOrganizationValue);
		
		getVehicle();

		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(newOwnertrafficFileNo);

		commonPage.gotoMainService("نقل ملكية مركبة");
		
		changeVehicleOwnershipPage = new ChangeVehicleOwnershipPage(driver);
		changeVehicleOwnershipPage.searchbyPlate(plateCategory, plateCode, plateNumber);
		dbQueries.removeBlocker(newOwnertrafficFileNo);
		
		changeVehicleOwnershipPage.proceedTrs(oldPlateStatus);

		if (commonPage.isBRShown()) {
			transactionsLst.remove(transactionsLst.size() - 1);
			transactionsLst.add(commonPage.getBRText());
			assertTrue(false);
		}
		
		commonPage.selectPlateDesign_PStrategy(PlateDesign.New);
		if (plateCategoryId == PlateCategory.Private)
			commonPage.selectNewPlates_PStrategy(false, PlateSize.Long, PlateSize.Short);
		else {
			commonPage.skipLogoStep();
			if (plateCategoryId == PlateCategory.Motorcycle || plateCategoryId == PlateCategory.Trailer)
				commonPage.selectNewPlates_PStrategy(PlateSize.Short);
			else
				commonPage.selectNewPlates_PStrategy(PlateSize.Short, PlateSize.Short);
		}

		commonPage.selectCollectionCenter_PStrategy("مركز تسليم - اينوك تسجيل القصيص");
		commonPage.selectDeliveryDate_PStrategy();
		commonPage.clickContinue_PStrategy();

		
		commonPage.printApplicationForm();
		commonPage.goToPayment();
		
		transactionsLst.remove(transactionsLst.size()-1);
		transactionsLst.add(commonPage.getTransactionId());
		
		commonPage.payFTF();
		if (!commonPage.transactionFeesAssertion(Integer.parseInt(excpectedFees))) {
			String s = (transactionsLst.get(transactionsLst.size() - 1) + " - Fess Faliure");
			transactionsLst.remove(transactionsLst.size() - 1);
			transactionsLst.add(s);
		}
	}
	
//	@Test
//	public void testWriteExcell() throws IOException
//	{
//		transactionsLst.add("MAwgoooooda");
//		ER.setTransactions(ExcelfileName, sheetname, TotalNumberOfCols, transactionsLst);
//	}

	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(String url, String browser, @Optional("en") String lang)
			throws ClassNotFoundException, SQLException, InterruptedException {
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
	public void AfterTest() throws ClassNotFoundException, SQLException {

		for (String trns : transactionsLst) {
			System.out.println("ChangeVehicleOwnership FTF trns: " + trns);
		}
		assertTrue(dbQueries.checkVLDFeesEvent(VHLTransaction.VLD_CHANGE_OWNERSHIP, testDateTime));
	}
	
	private void getVehicle() throws ClassNotFoundException, SQLException {
		String[] vehicle = dbQueries.getVehicle(vehicleClass, vehicleWeight, plateCategoryId, isOrganization, false);
		trafficFile = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = vehicle[3];
		chassis = vehicle[4];
		weight = vehicle[5];

		dbQueries.removeBlocker(trafficFile);
		dbQueries.addTest(chassis, vehicleClass, weight);
		dbQueries.addInsurance(chassis, vehicleClass);
	}
}