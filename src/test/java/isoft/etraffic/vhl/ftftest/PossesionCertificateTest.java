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
import isoft.etraffic.vhl.ftfpages.PossessionPage;

public class PossesionCertificateTest {
	String username = "rta13580";
	String center = "مؤسسة الترخيص - ديرة";
	VehicleClass vehicleClass;
	PlateCategory plateCategoryId;
	VehicleWeight vehicleWeight;
	OldPlateStatus oldPlateStatus = OldPlateStatus.ReturntoRTA;
	boolean isOrganization;
	List<String> transactionsLst = new ArrayList<String>();
	WebDriver driver;

	String trafficFile, plateCategory, plateNumber, plateCode, chassis, weight, testDateTime;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	PossessionPage possessionPage;
	ExcelReader ER = new ExcelReader();
	int TotalNumberOfCols = 5;
	String ExcelfileName;
	String sheetname = "PossessionCertificate";

	@DataProvider(name = "PossessionCertificate")
	public Object[][] vehicleData(ITestContext context) throws IOException {
		// get data from Excel Reader class
		
		ExcelfileName = context.getCurrentXmlTest().getParameter("filename");
		return ER.getExcelData(ExcelfileName, sheetname, TotalNumberOfCols);
	}

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
		String[] vehicle = dbQueries.getVehicle(vehicleClass, vehicleWeight, plateCategoryId, isOrganization, false);
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

	@BeforeClass
	public void beforeClass() throws ClassNotFoundException, SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		testDateTime = sdf.format(cal.getTime());
	}

	@AfterClass
	public void AfterClass() throws ClassNotFoundException, SQLException {

		for (String trns : transactionsLst) {
			System.out.println("PossesionCertificate FTF trns: " + trns);
		}
		assertTrue(dbQueries.checkVLDFeesEvent(VHLTransaction.VLD_POSSESSION, testDateTime));
	}
}
