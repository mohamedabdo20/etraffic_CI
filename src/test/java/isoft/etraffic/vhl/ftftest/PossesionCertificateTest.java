package isoft.etraffic.vhl.ftftest;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import isoft.etraffic.data.ExcelReader;
import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.OldPlateStatus;
import isoft.etraffic.enums.PlateCategory;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.enums.VehicleWeight;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.PossessionPage;

public class PossesionCertificateTest {
	String username = "rta13580";
	String center = "مؤسسة الترخيص - ديرة";
	VehicleClass vehicleClass;
	PlateCategory plateCategoryId;
	VehicleWeight vehicleWeight;
	OldPlateStatus oldPlateStatus = OldPlateStatus.Reserved;
	boolean isOrganization;
	List<String> transactionsLst = new ArrayList<String>();
	WebDriver driver;

	String trafficFile, plateCategory, plateNumber, plateCode, chassis, weight;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	PossessionPage possessionPage;

	@DataProvider(name = "PossessionCertificate")
	public Object[][] vehicleData() throws IOException {
		// get data from Excel Reader class
		ExcelReader ER = new ExcelReader();
		int TotalNumberOfCols = 5;
		String ExcelfileName = "vhl";
		String sheetname = "PossessionCertificate";
		return ER.getExcelData(ExcelfileName, sheetname, TotalNumberOfCols);
	}

//	// @Test(dataProvider = "PossessionCertificate")
//	public void getPossessionCertificate(String vehicleClassValue, String plateCategoryValue, String vehicleWeightRange,
//			String isOrganizationValue) throws Exception {
//
//		vehicleClass = dbQueries.getVehicleClassEnDescription(vehicleClassValue);
//		vehicleWeight = dbQueries.setVehicleWeightEnum(vehicleWeightRange);
//		plateCategoryId = dbQueries.getPlateCategoryEnum(plateCategoryValue);
//		isOrganization = Boolean.parseBoolean(isOrganizationValue);
//		setup();
//
//		loginPage = new LoginFTFPage(driver);
//		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);
//
//		commonPage = new CommonPage(driver);
//		commonPage.gotoHomePage();
//		commonPage.gotoSmartServices();
//		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
//		commonPage.gotoPlateService("شهادة حيازة");
//
//		possessionPage = new PossessionPage(driver);
//		possessionPage.proceedTrs(oldPlateStatus, false, "");
//		
//		if (commonPage.IsBRShown()) {
//			transactionsLst.remove(transactionsLst.size() - 1);
//			transactionsLst.add(commonPage.getBRText());
//			assertTrue(false);
//		}
//		
//		commonPage.printApplicationForm();
//		commonPage.goToPayment();
//		
//		transactionsLst.remove(transactionsLst.size() - 1);
//		transactionsLst.add(commonPage.getTransactionId());
//		
//		commonPage.payFTF();
//	}

	@Test(dataProvider = "PossessionCertificate")
	public void getPossessionCertificateWithChangeOwner(String vehicleClassValue, String plateCategoryValue,
			String vehicleWeightRange, String isOrganizationValue, String newOwnertrafficFileNo) throws Exception {

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
		commonPage.gotoPlateService("شهادة حيازة");

		possessionPage = new PossessionPage(driver);
		if (newOwnertrafficFileNo.equals("NA"))
			possessionPage.proceedTrs(oldPlateStatus, false, "");
		else {
			if (trafficFile.equals(newOwnertrafficFileNo) && !trafficFile.equals("10031526"))
				newOwnertrafficFileNo = "10031526";
			dbQueries.removeBlocker(newOwnertrafficFileNo);
			possessionPage.proceedTrs(oldPlateStatus, true, newOwnertrafficFileNo);
		}
		
		if (commonPage.isBRShown()) {
			transactionsLst.remove(transactionsLst.size() - 1);
			transactionsLst.add(commonPage.getBRText());
			assertTrue(false);
		}
		
		commonPage.printApplicationForm();
		commonPage.goToPayment();
		
		transactionsLst.remove(transactionsLst.size()-1);
		transactionsLst.add(commonPage.getTransactionId());
		
		commonPage.payFTF();
	}

	private void setup() throws Exception {
		String[] vehicle = dbQueries.getVehicle(vehicleClass, vehicleWeight, plateCategoryId, isOrganization);
		trafficFile = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = vehicle[3];
		chassis = vehicle[4];
		weight = vehicle[5];
		dbQueries.removeBlocker(trafficFile);
	}

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

	@AfterClass
	public void afterClass() {
		for (String trns : transactionsLst) {
			System.out.println("trns: " + trns);
		}
	}
}
