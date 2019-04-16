package isoft.etraffic.vhl.sdditest;

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
import isoft.etraffic.enums.VHLTransaction;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.enums.VehicleWeight;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;
import isoft.etraffic.vhl.sddipages.RenewalPages;

public class RenewSDDITest {

	/////////////////// Test Data ////////////////////
	VehicleClass vehicleClass;
	VehicleWeight vehicleWeight;
	PlateCategory plateCategoryId;
	OldPlateStatus oldPlateStatus;
	boolean isOrganization;
	WebDriver driver;
	
	String electricOwnerTrafficFile = "10547783";

	String trafficFileNo, plateNumber, plateCode, plateCategory, chassis, weight, testDateTime;
	DBQueries dbQueries = new DBQueries();
	CommonPageOnline commonPage;
	RenewalPages renewalPages;
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
		
		
		commonPage = new CommonPageOnline(driver);
		commonPage.loginByTrafficFile(trafficFileNo);
		commonPage.gotoLicensing();
		commonPage.gotoMyVehicles();
		
		// Search for plate & go to Renewal
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);

		renewalPages = new RenewalPages(driver);
		renewalPages.gotoVehicleRenewal();

		// Start Renewal Process
		renewalPages.startRenewalProcess();

		commonPage.gotoSelectDelivery();

		// Delivery date Calendar
		commonPage.selectDeliveryDateCourier(true);
		commonPage.payOnline();
	}

	private void getExpiredVehicle() throws ClassNotFoundException, SQLException {
		String[] vehicle = dbQueries.getExpiredVehicle(vehicleClass, vehicleWeight, plateCategoryId, isOrganization);
		trafficFileNo = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = dbQueries.getPlateCategoryEnDesc(vehicle[3]);
		chassis = vehicle[4];
		weight = vehicle[5];

		if (weight.equals("0") && vehicleClass.equals(VehicleClass.HeavyVehicle))
			weight = "3500";
		else
			weight = "2500";

		dbQueries.addTest(chassis, vehicleClass, weight);

		if (!vehicleClass.equals(VehicleClass.Trailer) && !vehicleClass.equals(VehicleClass.EntertainmentMotorcycle)) {
			dbQueries.addInsurance(chassis, vehicleClass);
		}

		dbQueries.removeBlocker(trafficFileNo);
		//dbQueries.addFines(trafficFileNo, chassis);
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
