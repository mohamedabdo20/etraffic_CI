package isoft.etraffic.vhl.ftftest;

import static org.testng.Assert.assertTrue;
import java.awt.AWTException;
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
import isoft.etraffic.vhl.ftfpages.ExportCertificatePage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class ExportCertificateTest {

	String username = "rta13580";
	String center = "اينوك";
	VehicleClass vehicleClass;
	PlateCategory plateCategoryId;
	VehicleWeight vehicleWeight;
	OldPlateStatus oldPlateStatus = OldPlateStatus.Reserved;

	boolean isOrganization;
	String trafficFile, plateCategory, plateNumber, plateCode, chassis, weight, licenseNumber;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	ExportCertificatePage exportCertificatePage;
	WebDriver driver;
	List<String> transactionsLst = new ArrayList<String>();

	@DataProvider(name = "ExportVehicle")
	public Object[][] vehicleData(String url) throws IOException {
		System.out.println("URL Dataprovider: " + url);
		// get data from Excel Reader class
		ExcelReader ER = new ExcelReader();
		int TotalNumberOfCols = 5;
		String ExcelfileName = "vhl";
		String sheetname = "Export";
		return ER.getExcelData(ExcelfileName, sheetname, TotalNumberOfCols);
	}

	@Test(dataProvider = "ExportVehicle")
	public void ExportVehicle(String vehicleClassValue, String plateCategoryValue, String vehicleWeightRange,
			String isOrganizationValue, String newOwnertrafficFileNo)
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
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoPlateService("شهادة تصدير");

		exportCertificatePage = new ExportCertificatePage(driver);
		if (newOwnertrafficFileNo.equals("NA"))
			exportCertificatePage.proceedTrs("مصر", oldPlateStatus, true, licenseNumber);
		else
			exportCertificatePage.exportToNewOwner("مصر", oldPlateStatus, false, newOwnertrafficFileNo, licenseNumber);

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
	}

	private void getVehicle() throws ClassNotFoundException, SQLException {
		String[] vehicle = dbQueries.getVehicle(vehicleClass, vehicleWeight, plateCategoryId, isOrganization);
		trafficFile = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = vehicle[3];
		chassis = vehicle[4];
		weight = vehicle[5];
		dbQueries.removeBlocker(trafficFile);
		licenseNumber = dbQueries.getLicenseNumber(trafficFile);

		dbQueries.addInsurance(chassis, vehicleClass);
		dbQueries.addTest(chassis, vehicleClass, weight);

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
