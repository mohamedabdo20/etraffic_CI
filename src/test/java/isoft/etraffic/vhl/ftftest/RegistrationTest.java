package isoft.etraffic.vhl.ftftest;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.JavascriptExecutor;
 
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.PlateSize;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.RegistrationPage;

public class RegistrationTest extends TestBase{

	String username = "rta13580";// "rta10686";
	String center = "مؤسسة الترخيص - ديرة";
	String trafficFile = "10184041";

	 
	JavascriptExecutor js;
	LoginFTFPage loginPage;
	CommonPage commonPage;
	RegistrationPage registrationPage;
	String chassis, sourceDate;

	DBQueries dbQuery = new DBQueries();

	@BeforeMethod
	public void setup() throws Exception {
		 
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
		SimpleDateFormat sorceDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		sourceDate = sorceDateFormat.format(cal.getTime());
		chassis = sdf.format(cal.getTime()).replace("-", "").replace(":", "") + "00";
		System.out.println("chassis: " + chassis);
	}

	@Parameters({ "bookletSource", "sourceId", "vehicleClass", "vehicleDescription", "plateCategory", "manufacturer",
			"fuelSource", "fuelSubType", "vehicleWeight", "noOfSeats", "noOfDoors", "modelYear", "vehicleSource" })
	@Test
	public void registerVehicle(String bookletSource, String sourceId, String vehicleClass, String vehicleDescription,
			String plateCategory, String manufacturer, String fuelSource, String fuelSubType, String vehicleWeight,
			String noOfSeats, String noOfDoors, String modelYear, String vehicleSource) throws Exception {

		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQuery.getUserPassword(username), center);

		if (vehicleClass.equals("مقطورة"))
			dbQuery.addTest(chassis, VehicleClass.Trailer, vehicleWeight);
		else 
			dbQuery.addInsurance(chassis, vehicleClass);
			System.out.println("No added Insurance");
		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFile);
		commonPage.gotoMainService("تسجيل مركبة جديدة");
		registrationPage = new RegistrationPage(driver);
		registrationPage.setVehicleSourceDetails("دبي", bookletSource, sourceId, sourceDate, vehicleClass,
				vehicleDescription, plateCategory, chassis);

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
		//registrationPage.selectPlates("صرف يومى", "قصير", "قصير");
		commonPage.selectNewPlates_PStrategy("صرف يومى", false, PlateSize.Luxury, PlateSize.Long);
		commonPage.clickContinue_PStrategy();

		commonPage.printApplicationForm();
		commonPage.goToPayment();
		commonPage.payFTF();
	}

//	 @Parameters({ "bookletSource", "sourceId", "vehicleClass",
//	 "vehicleDescription", "plateCategory", "manufacturer",
//	 "fuelSource", "fuelSubType", "vehicleWeight", "noOfSeats", "noOfDoors",
//	 "modelYear" })
//	 @Test
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
}
