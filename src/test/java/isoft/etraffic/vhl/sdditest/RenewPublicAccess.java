package isoft.etraffic.vhl.sdditest;

import java.sql.SQLException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.PlateCategory;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.enums.VehicleWeight;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;
import isoft.etraffic.vhl.sddipages.RenewalPages;

public class RenewPublicAccess {
	// Test data
	VehicleClass vehicleClass = VehicleClass.LightVehicle;
	VehicleWeight vehicleWeight = VehicleWeight.Any;
	PlateCategory plateCategoryValue = PlateCategory.Private;
	boolean isOrganization = false;
	
	///////////////////////////////////////////////////////////////
	WebDriver driver;
	JavascriptExecutor js;
	String trafficFileNo, plateNumber, plateCode, plateCategory, chassis, weight;
	DBQueries dbQueries = new DBQueries();
	CommonPageOnline commonPage;
	RenewalPages renewalPages;

	@BeforeTest
	public void beforeTest() throws InterruptedException, ClassNotFoundException, SQLException {
		String[] vehicle = dbQueries.getExpiredVehicle(vehicleClass, vehicleWeight, plateCategoryValue, false);
		trafficFileNo = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = vehicle[3];
		chassis = vehicle[4];
		weight = vehicle[5];

		if (weight.equals("0") && vehicleClass.equals(VehicleClass.HeavyVehicle))
			weight = "3500";
		else
			weight = "2500";

		dbQueries.addTest(chassis, vehicleClass, weight);

		startBrowser("chrome");

		if (!vehicleClass.equals(VehicleClass.Trailer) && !vehicleClass.equals(VehicleClass.EntertainmentMotorcycle)) {
			dbQueries.addInsurance(chassis, vehicleClass);
		}
		dbQueries.removeBlocker(trafficFileNo);

		commonPage = new CommonPageOnline(driver);
		commonPage.gotoService("تجديد مركبة");
	}

	@Test
	public void renewVehicle() throws InterruptedException {
		// Search for plate & go to Renewal
		//commonPage.searchByPlate(plateCategory, plateNumber, plateCode);

		renewalPages = new RenewalPages(driver);

		// Start Renewal Process
		renewalPages.startRenewalProcess();
		renewalPages.fillVehicleInfo(plateNumber, plateCode, plateCategory);
		commonPage.gotoSelectDelivery();
		
		// Delivery date Calendar
		commonPage.selectDeliveryDateCourier(false);
		//commonPage.payOnline();	
	}
	
	private void startBrowser(String browser) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--new-window");
			options.addArguments("-incognito");
			ChromeDriverService service = ChromeDriverService.createDefaultService();
			driver = new ChromeDriver(service, options);
			// Go to URL
			driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do?switchLanguage=ar");
			driver.manage().window().maximize();
	}
}