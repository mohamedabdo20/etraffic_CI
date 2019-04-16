package isoft.etraffic.vhl.sdditest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.PlateCategory;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;
import isoft.etraffic.vhl.sddipages.RenewalPages;

public class BasketScenarios {

	VehicleClass vehicleClass = VehicleClass.LightVehicle;
	PlateCategory plateCategoryValue = PlateCategory.Private;

	WebDriver driver;
	JavascriptExecutor js;
	String trafficFileNo;// = "10011058";
	DBQueries dbQueries = new DBQueries();
	CommonPageOnline commonPage;
	RenewalPages renewalPages;
	String[][] vehicles;
	List<String> transactionsLst = new ArrayList<String>();

	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang)
			throws ClassNotFoundException, SQLException, InterruptedException {

		transactionsLst.add("");
		System.out.println("transactionsLst.size(): " + transactionsLst.size());
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;
	}
	
	@Test
	public void renewVehiclesBasket() throws InterruptedException, ClassNotFoundException, SQLException {
		// Add first Vehicle
		beforeTest();
		commonPage.searchByPlate(dbQueries.getPlateCategoryEnDesc(vehicles[0][3]), vehicles[0][1], vehicles[0][2]);
		renewalPages = new RenewalPages(driver);
		renewalPages.gotoVehicleRenewal();
		renewalPages.startRenewalProcess();

		renewalPages.addtoBasket();

		// Add Second Vehicle
		commonPage.searchByPlate(dbQueries.getPlateCategoryEnDesc(vehicles[1][3]), vehicles[1][1], vehicles[1][2]);
		renewalPages.gotoVehicleRenewal();
		renewalPages.startRenewalProcess();

		commonPage.gotoSelectDelivery();

		// Delivery date Calendar
		commonPage.selectDeliveryDateCourier(false);
	}

	//@Test
	public void saveBasketThenRemove1Vehicle() throws InterruptedException, ClassNotFoundException, SQLException {
		// Add first Vehicle
		beforeTest();
		// Add first Vehicle
		commonPage.searchByPlate(dbQueries.getPlateCategoryEnDesc(vehicles[0][3]), vehicles[0][1], vehicles[0][2]);
		renewalPages = new RenewalPages(driver);
		renewalPages.gotoVehicleRenewal();
		renewalPages.startRenewalProcess();

		renewalPages.addtoBasket();

		// Add Second Vehicle
		commonPage.searchByPlate(dbQueries.getPlateCategoryEnDesc(vehicles[1][3]), vehicles[1][1], vehicles[1][2]);
		renewalPages.gotoVehicleRenewal();
		renewalPages.startRenewalProcess();

		commonPage.gotoSelectDelivery();

		// Delivery date Calendar
		commonPage.selectDeliveryDateCourier(false);

		// Save Basket
		renewalPages.saveBasket();

		// Remove the first added vehicle
		Assert.assertTrue(renewalPages.removeFromBasketFirstVehicle());
	}

	//@Test
	public void remove1VehicleFromBasket() throws InterruptedException, ClassNotFoundException, SQLException {
		// Add first Vehicle
		beforeTest();
		commonPage.searchByPlate(dbQueries.getPlateCategoryEnDesc(vehicles[0][3]), vehicles[0][1], vehicles[0][2]);
		renewalPages = new RenewalPages(driver);
		renewalPages.gotoVehicleRenewal();
		renewalPages.startRenewalProcess();

		renewalPages.addtoBasket();

		// Add Second Vehicle
		commonPage.searchByPlate(dbQueries.getPlateCategoryEnDesc(vehicles[1][3]), vehicles[1][1], vehicles[1][2]);
		renewalPages.gotoVehicleRenewal();
		renewalPages.startRenewalProcess();

		commonPage.gotoSelectDelivery();

		// Delivery date Calendar
		commonPage.selectDeliveryDateCourier(false);

		// Remove the first added vehicle
		Assert.assertTrue(renewalPages.removeFromBasketFirstVehicle());
	}

	//@Test
	public void addVehicleWithReleaseMortgageRequestNotApproved()
			throws InterruptedException, ClassNotFoundException, SQLException {
		// Add first Vehicle
		startBrowser("chrome");
		String[] vehicle = dbQueries.getVehiclewithMortgageRelease();
		if (!vehicle[4].equals(null)) {
			dbQueries.makeVehicleExpired(vehicle[4]);
			dbQueries.addTest(vehicle[4], vehicleClass, vehicle[5]);
			dbQueries.addInsurance(vehicle[4], vehicleClass);

			startBrowser("chrome");

			dbQueries.removeBlocker(vehicle[0]);

			commonPage = new CommonPageOnline(driver);
			commonPage.loginByTrafficFile(vehicle[0]);
			commonPage.gotoLicensing();
			commonPage.gotoMyVehicles();

			commonPage.searchByPlate(dbQueries.getPlateCategoryEnDesc(vehicle[3]), vehicle[1], vehicle[2]);
			renewalPages = new RenewalPages(driver);
			renewalPages.gotoVehicleRenewal();
			renewalPages.startRenewalProcess();

			renewalPages.addtoBasket();
		}

		else {System.out.println("TC failed due to unavailability of test data");}
		// Delivery date Calendar
		// commonPage.selectDeliveryDateCourier(false);
		//
		// // Remove the first added vehicle
		// renewalPages.removeFromBasketFirstVehicle();
	}

	private void beforeTest() throws InterruptedException, ClassNotFoundException, SQLException {

		trafficFileNo = "10024177";//dbQueries.getTrafficFileHas2ExpiredVehicles();
		vehicles = dbQueries.getExpiredVehiclesByTrafficFile(trafficFileNo);
		for (int i = 0; i < 2; i++) {
			dbQueries.addTest(vehicles[i][4], vehicleClass, vehicles[i][5]);
			dbQueries.addInsurance(vehicles[i][4], vehicleClass);
		}

		dbQueries.removeBlocker(trafficFileNo);

		commonPage = new CommonPageOnline(driver);
		commonPage.loginByTrafficFile(trafficFileNo);
		commonPage.gotoLicensing();
		commonPage.gotoMyVehicles();
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
			// Go to URL
			driver.get("https://tst12c:7793/trfesrv/test_login.jsp");
			driver.manage().window().maximize();
			break;
		case "ie":
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			// Go to URL
			driver.get("https://tst12c:7793/trfesrv/test_login.jsp");
			break;
		default:
			System.out.println("Invalid browser type");
			break;
		}
	}
}
