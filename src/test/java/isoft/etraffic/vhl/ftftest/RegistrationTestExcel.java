package isoft.etraffic.vhl.ftftest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.enums.*;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.RegistrationPage;
import isoft.etraffic.data.ExcelReader;
import isoft.etraffic.db.DBQueries;

public class RegistrationTestExcel {

	String username = "rta13580";// "rta10686";
	String center = "اينوك";
	String trafficFile = "10184041";
 
	WebDriver driver;
	JavascriptExecutor js;
	LoginFTFPage loginPage;
	CommonPage commonPage;
	RegistrationPage registrationPage;
	String chassis, sourceDate;

	DBQueries dbQuery = new DBQueries();

	@BeforeMethod
	public void setup() throws Exception {
		startBrowser("ie");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
		SimpleDateFormat sorceDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		sourceDate = sorceDateFormat.format(cal.getTime());
		chassis = sdf.format(cal.getTime()).replace("-", "").replace(":", "") + "00";
		System.out.println("chassis: " + chassis);
	}

	@DataProvider(name = "Registration")
	public Object[][] ActivityData() throws IOException {
		// get data from Excel Reader class
		ExcelReader ER = new ExcelReader();
		ER.TotalNumberOfCols = 13;
		ER.sheetname = "registration";
		return ER.getExcelData();}


	//@Parameters({ "bookletSource", "sourceId", "vehicleClass", "vehicleDescription", "plateCategory", "manufacturer",
	//	"fuelSource", "fuelSubType", "vehicleWeight", "noOfSeats", "noOfDoors", "modelYear", "vehicleSource" })
	@Test(dataProvider="Registration")
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

		if (vehicleClass.equals("مركبة خفيفة"))
		registrationPage.clickChassisExceptionBtn();
		
		registrationPage.clickProceedBtn();

		if (bookletSource.equals("شهادة جمركية")) {
			registrationPage.setVehicleDetails(chassis, manufacturer, "", "اليابان", modelYear, "75385296741",
					vehicleSource, fuelSource, fuelSubType, vehicleWeight, noOfDoors, noOfSeats, "ابيض", vehicleClass);
		} else {
			dbQuery.addTest(vehicleDescription, dbQuery.getVehicleClassEnDescription(vehicleClass), vehicleWeight);
			registrationPage.setCertificateVehicleDetails(vehicleSource);

		}

		//////////////////////// Plate Strategy Or not
		// registrationPage.selectPlates("صرف يومى", "قصير", "قصير");
		commonPage.selectNewPlates_PStrategy("صرف يومى", false, PlateSize.Luxury, PlateSize.Long);
		commonPage.clickContinue_PStrategy();

		commonPage.printApplicationForm();
		commonPage.goToPayment();
		commonPage.payFTF();
	}

	 @Parameters({ "bookletSource", "sourceId", "vehicleClass",
	 "vehicleDescription", "plateCategory", "manufacturer",
	 "fuelSource", "fuelSubType", "vehicleWeight", "noOfSeats", "noOfDoors",
	 "modelYear" })
	 @Test(enabled=false)
	public void registerVehicleWithoutExemptedChassis(String bookletSource, String sourceId, String vehicleClass,
			String vehicleDescription, String plateCategory, String manufacturer, String fuelSource, String fuelSubType,
			String vehicleWeight, String noOfSeats, String noOfDoors, String modelYear) throws Exception {

		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQuery.getUserPassword(username), center);

		dbQuery.addInsurance(chassis, vehicleClass);

		commonPage = new CommonPage(driver);
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFile);
		commonPage.gotoMainService("تسجيل مركبة جديدة");

		registrationPage = new RegistrationPage(driver);

		registrationPage.setVehicleSourceDetails("دبي", bookletSource, sourceId, sourceDate, vehicleClass,
				vehicleDescription, plateCategory, "1GKS27KJ1JR180300");

		registrationPage.clickChassisValidationBtn();
		registrationPage.validateChassis("1GKS27KJ1JR180300");
		registrationPage.clickProceedBtn();

		// if (bookletSource.equals("شهادة جمركية")) {
		// registrationPage.setVehicleDetails(chassis, manufacturer, "", "اليابان",
		// modelYear, "75385296741",
		// "مستعملة", fuelSource, fuelSubType, vehicleWeight, noOfDoors, noOfSeats,
		// "ابيض", vehicleClass);
		// } else {
		// dbQuery.addTest(vehicleDescription,
		// dbQuery.getVehicleClassEnDescription(vehicleClass), vehicleWeight);
		// registrationPage.setCertificateVehicleDetails("وكالة");
		//
		// }
		// registrationPage.selectPlates("صرف يومى", "قصير", "قصير");
		// commonPage.printApplicationForm();
		// commonPage.goToPayment();
		// commonPage.payFTF();
	}

	private void startBrowser(String browser) {
		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--new-window");
			options.addArguments("-incognito");
			ChromeDriverService service = ChromeDriverService.createDefaultService();
			driver = new ChromeDriver(service, options);
			break;
		case "ie":
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			driver.get("https://tst12c:7791/traffic/faces/jsf/auth/login.jsf");// "https://qctest:4443/traffic/faces/jsf/auth/login.jsf");
			driver.get("javascript:document.getElementById('overridelink').click();");
			break;
		default:
			System.out.println("Invalid browser type");
			break;
		}
		driver.manage().window().maximize();
	}
	

}
