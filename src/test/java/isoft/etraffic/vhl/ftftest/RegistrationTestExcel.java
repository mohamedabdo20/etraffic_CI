package isoft.etraffic.vhl.ftftest;

import static org.testng.Assert.assertTrue;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import isoft.etraffic.data.ExcelReader;
import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.PlateSize;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.RegistrationPage;

public class RegistrationTestExcel
{
	String username = "rta13580";
	String center = "اينوك";
	String trafficFile = "10184041";

	WebDriver driver;
	JavascriptExecutor js;
	LoginFTFPage loginPage;
	CommonPage commonPage;
	RegistrationPage registrationPage;
	String chassis, sourceDate;
	List<String> transactionsLst = new ArrayList<String>();
	DBQueries dbQuery = new DBQueries();
	ExcelReader ER = new ExcelReader();
	int TotalNumberOfCols = 13;
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
	public void registerVehicle(String bookletSource, String sourceId, String vehicleClass, String vehicleDescription,
			String plateCategory, String manufacturer, String fuelSource, String fuelSubType, String vehicleWeight,
			String noOfSeats, String noOfDoors, String modelYear, String vehicleSource) throws Exception {

		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQuery.getUserPassword(username), center);

		if (vehicleClass.equals("مقطورة"))
			dbQuery.addTest(chassis, VehicleClass.Trailer, vehicleWeight);
		else
			dbQuery.addInsurance(chassis, vehicleClass);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFile);
		commonPage.gotoMainService("تسجيل مركبة جديدة");
		registrationPage = new RegistrationPage(driver);
		registrationPage.setVehicleSourceDetails("دبي", bookletSource, sourceId, sourceDate, vehicleClass,
				vehicleDescription, plateCategory, chassis);

		commonPage.closeBRAlert();

		if (vehicleClass.equals("مركبة خفيفة"))
			registrationPage.clickChassisExceptionBtn();

		registrationPage.clickProceedBtn();

		if (commonPage.isBRFiredRegistration()) {
			transactionsLst.remove(transactionsLst.size()-1);
			transactionsLst.add(commonPage.getTransactionId());
			assertTrue(false);
		}

		if (bookletSource.equals("شهادة جمركية")) {
			registrationPage.setVehicleDetails(chassis, manufacturer, "", "اليابان", modelYear, "75385296741",
					vehicleSource, fuelSource, fuelSubType, vehicleWeight, noOfDoors, noOfSeats, "ابيض", vehicleClass);
		} else {
			dbQuery.addTest(vehicleDescription, dbQuery.getVehicleClassEnDescription(vehicleClass), vehicleWeight);
			registrationPage.setCertificateVehicleDetails(vehicleSource);

		}

		commonPage.selectNewPlateSource_PStrategy("صرف يومى");
		commonPage.clickCertifyBtn();

		if (commonPage.isBRShown()) {
			transactionsLst.remove(transactionsLst.size()-1);
			transactionsLst.add(commonPage.getTransactionId());
			assertTrue(false);
		}

		commonPage.selectNewPlates_PStrategy(false, PlateSize.Luxury, PlateSize.Long);
		commonPage.clickContinue_PStrategy();
		
		if (commonPage.isBRShownRegistration()) {
			transactionsLst.remove(transactionsLst.size()-1);
			transactionsLst.add(commonPage.getTransactionId());
			assertTrue(false);
		}
		
		commonPage.printApplicationForm();
		commonPage.goToPayment();
		
		transactionsLst.remove(transactionsLst.size()-1);
		transactionsLst.add(commonPage.getTransactionId());
		
		commonPage.payFTF();
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
