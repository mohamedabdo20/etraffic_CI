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
import isoft.etraffic.enums.PlateCategory;
import isoft.etraffic.enums.VHLTransaction;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.enums.VehicleWeight;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.RegistrationPage;
import isoft.etraffic.vhl.ftfpages.TourismPage;
import isoft.etraffic.vhl.sdditest.TourismIssueNOCCertficate;

public class TourismCertificateTest {

	String username = "rta13580", center = "مؤسسة الترخيص - ديرة";
	VehicleClass vehicleClass;
	PlateCategory plateCategoryId;
	VehicleWeight vehicleWeight;
	boolean isOrganization;
	LoginFTFPage loginPage;
	CommonPage commonPage;
	RegistrationPage registrationPage;
	TourismPage tourismPage;
	WebDriver driver;
	String trafficFile, plateCategory, plateNumber, plateCode, chassis, weight, url, testDateTime;
	DBQueries dbQueries = new DBQueries();
	List<String> transactionsLst = new ArrayList<String>();
	TourismIssueNOCCertficate tourismIssueNOCCertficate;
	ExcelReader ER = new ExcelReader();
	int TotalNumberOfCols = 5;
	String ExcelfileName = "vhl";
	String sheetname = "TourismCertificate";

	@DataProvider(name = "TourismCertificate")
	public Object[][] vehicleData(ITestContext context) throws IOException {
		// get data from Excel Reader class
		ExcelfileName = context.getCurrentXmlTest().getParameter("filename");
		return ER.getExcelData(ExcelfileName, sheetname, TotalNumberOfCols);
	}

	@Test(dataProvider = "TourismCertificate")
	public void getTourismCertificate(String vehicleClassValue, String plateCategoryValue, String vehicleWeightValue,
			String isOrganizationValue, String excpectedFees) throws Exception {

		vehicleClass = dbQueries.getVehicleClassEnDescription(vehicleClassValue);
		vehicleWeight = dbQueries.setVehicleWeightEnum(vehicleWeightValue);
		plateCategoryId = dbQueries.getPlateCategoryEnum(plateCategoryValue);
		isOrganization = Boolean.parseBoolean(isOrganizationValue);

		getVehicle();
		getNOCCertificate();

		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoPlateService("شهادة سياحة");

		tourismPage = new TourismPage(driver);
		tourismPage.proceedTrs("مصر");

		if (commonPage.isBRShown()) {
			transactionsLst.remove(transactionsLst.size() - 1);
			transactionsLst.add(commonPage.getBRText());
			assertTrue(false);
		}

		transactionsLst.remove(transactionsLst.size() - 1);
		transactionsLst.add(commonPage.getTransactionId());

		commonPage.payFTF();
		assertTrue(commonPage.transactionFeesAssertion(Integer.parseInt(excpectedFees)));
	}

	private void getNOCCertificate() throws Exception {
		tourismIssueNOCCertficate = new TourismIssueNOCCertficate(chassis, trafficFile,
				dbQueries.getPlateCategoryEnDesc(plateCategory), plateNumber, plateCode, weight);
		tourismIssueNOCCertficate.startBrowser("chrome", getServerURL());
		try{tourismIssueNOCCertficate.issueNOCCertificate();}
		catch(Exception e) {System.out.println("Failed on adding add NOC for :" + plateNumber + dbQueries.getPlateCategoryEnDesc(plateCategory) + plateCode);}
		tourismIssueNOCCertficate.closeDriver();
	}

	private String getServerURL() {
		if (url.contains("tst12"))
			return "https://tst12c:7793/trfesrv/test_login.jsp";
		else // (url.contains("er12"))
			return "https://er12cr2:7784/trfesrv/test_login.jsp";
	}

	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(String url, String browser, @Optional("en") String lang)
			throws ClassNotFoundException, SQLException, InterruptedException {
		transactionsLst.add("");
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;
		this.url = url;
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
			System.out.println("TourismCertificateT FTF trns: " + trns);
		}
		assertTrue(dbQueries.checkVLDFeesEvent(VHLTransaction.VLD_TOURISM_CERT, testDateTime));
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
		dbQueries.addInsurance(chassis, vehicleClass);
	}
}
