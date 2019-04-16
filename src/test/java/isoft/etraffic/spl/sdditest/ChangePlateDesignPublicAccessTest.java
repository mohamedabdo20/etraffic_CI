package isoft.etraffic.spl.sdditest;

import static org.testng.Assert.assertTrue;
import java.sql.SQLException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import isoft.etraffic.enums.*;
import isoft.etraffic.spl.sddipages.ChangePlateDesignPages;
import isoft.etraffic.db.DBQueries;
//import RTA.VHL.Online.testclass;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;
import isoft.etraffic.testbase.TestBase;

public class ChangePlateDesignPublicAccessTest extends TestBase{
	// Test data
	VehicleWeight vehicleWeight = VehicleWeight.Any;
	PlateCategory plateCategoryValue = PlateCategory.Private;

	///////////////////////////////////////////////////////////////
	WebDriver driver;
	String trafficFileNo, plateNumber, plateCode, plateCategory;
	DBQueries dbQueries = new DBQueries();
	CommonPageOnline commonPage;
	ChangePlateDesignPages changePlateDesignPages;

	@BeforeTest
	public void beforeTest() throws InterruptedException, ClassNotFoundException, SQLException {
		//startBrowser("chrome");

		commonPage = new CommonPageOnline(driver);
		commonPage.gotoService("تغيير تصميم اللوحة");
	}

	@Test
	public void changePlateDesign() throws InterruptedException, ClassNotFoundException, SQLException {

		getValidVehicle();
		dbQueries.removeBlocker(trafficFileNo);
		// Start Renewal Process

		selectNewDesign(PlateSize.Luxury, PlateSize.Long, true);

		// Delivery date Calendar
		commonPage.gotoSelectDelivery();
		commonPage.selectDeliveryDateCourier(false);
		commonPage.payOnline();
	}

	@Test
	public void changeDesign2TimesWithSamePlateDesign()
			throws InterruptedException, ClassNotFoundException, SQLException {

		getValidVehicle();
		dbQueries.removeBlocker(trafficFileNo);
		// Start Renewal Process

		selectNewDesign(PlateSize.Luxury, PlateSize.Long, true);

		// Delivery date Calendar
		commonPage.gotoSelectDelivery();
		commonPage.selectDeliveryDateCourier(false);
		commonPage.payOnline();

		//////////////////////////////////////////////////////////////
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do?switchLanguage=ar");
		commonPage.gotoService("تغيير تصميم اللوحة");
		selectNewDesign(PlateSize.Luxury, PlateSize.Long, true);
		commonPage.gotoSelectDelivery();

		commonPage.isBRShown("انجاز المعاملة حاليا لأنه يوجد نفس المعاملة قيد الانجاز في الحكومة الالكترونية");
	}

	@Test
	public void changeDesign2TimesWithDifferentDesignWithExtraFees()
			throws InterruptedException, ClassNotFoundException, SQLException {

		getValidVehicle();
		dbQueries.removeBlocker(trafficFileNo);
		// Start Renewal Process

		selectNewDesign(PlateSize.Short, PlateSize.Short, true);

		// Delivery date Calendar
		commonPage.gotoSelectDelivery();
		commonPage.selectDeliveryDateCourier(false);
		commonPage.payOnline();

		//////////////////////////////////////////////////////////////
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do?switchLanguage=ar");
		commonPage.gotoService("تغيير تصميم اللوحة");
		selectNewDesign(PlateSize.Luxury, PlateSize.Long, false);

		commonPage.gotoSelectDelivery();
		commonPage.selectDeliveryDateCourier(false);

		assertTrue(changePlateDesignPages.checkExtraFees("150"));
	}

	public void changeDesign2TimesWithDifferentDesignWithExtraFees200()
			throws InterruptedException, ClassNotFoundException, SQLException {

		getValidVehicle();
		dbQueries.removeBlocker(trafficFileNo);
		// Start Renewal Process

		selectNewDesign(PlateSize.Long, PlateSize.Long, false);//100

		// Delivery date Calendar
		commonPage.gotoSelectDelivery();
		commonPage.selectDeliveryDateCourier(false);
		commonPage.payOnline();

		//////////////////////////////////////////////////////////////
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do?switchLanguage=ar");
		commonPage.gotoService("تغيير تصميم اللوحة");
		selectNewDesign(PlateSize.Luxury, PlateSize.Long, true);//700

		commonPage.gotoSelectDelivery();
		commonPage.selectDeliveryDateCourier(false);

		assertTrue(changePlateDesignPages.checkExtraFees("600"));
	}
	
	@Test
	public void changeDesign2TimesWithDifferentDesignWithoutExtraFees()
			throws InterruptedException, ClassNotFoundException, SQLException {

		getValidVehicle();
		dbQueries.removeBlocker(trafficFileNo);
		// Start Renewal Process

		selectNewDesign(PlateSize.Luxury, PlateSize.Long, true);

		// Delivery date Calendar
		commonPage.gotoSelectDelivery();
		commonPage.selectDeliveryDateCourier(false);
		commonPage.payOnline();

		//////////////////////////////////////////////////////////////
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do?switchLanguage=ar");
		commonPage.gotoService("تغيير تصميم اللوحة");
		selectNewDesign(PlateSize.Long, PlateSize.Long, false);

		commonPage.gotoSelectDelivery();
		commonPage.selectDeliveryDateCourier(false);

		assertTrue(commonPage.checkFreeTransaction());
		// commonPage.confirm();
	}

	@Test
	public void changePlateDesignForExpiredVehicle() throws InterruptedException, ClassNotFoundException, SQLException {
		getExpiredVehicle();
		dbQueries.removeBlocker(trafficFileNo);

		// Start Renewal Process
		changePlateDesignPages = new ChangePlateDesignPages(driver);
		changePlateDesignPages.startProcess();
		changePlateDesignPages.fillVehicleInfo(plateNumber, plateCode, plateCategory);
		changePlateDesignPages.changePlate(PlateSize.Luxury, PlateSize.Long, true);// Add Dubai Logo?
		commonPage.gotoSelectDelivery();

		// Assertion on BR
		assertTrue(commonPage.isBRShown("إن تسجيل المركبة منتهي، يرجى تجديد تسجيل مركبتك قبل التقديم لهذه الخدمة"));
	}

	@Test
	public void changeDesignWithTwoLostPlates() throws InterruptedException, ClassNotFoundException, SQLException {

		getValidVehicle();
		dbQueries.removeBlocker(trafficFileNo);
		// Start Renewal Process
		changePlateDesignPages = new ChangePlateDesignPages(driver);
		changePlateDesignPages.startProcess();
		changePlateDesignPages.fillVehicleInfo(plateNumber, plateCode, plateCategory);
		changePlateDesignPages.changePlate(PlateSize.Luxury, PlateSize.Long, true);// Add Dubai Logo?
		changePlateDesignPages.selectLostPlates(true, true);

		assertTrue(changePlateDesignPages.isSelectDeliveryBtnHidden());
	}

	@Test
	public void changePlateDesignForImpoundedVehicle()
			throws ClassNotFoundException, SQLException, InterruptedException {
		getExpiredVehicle();
		dbQueries.removeBlocker(trafficFileNo);
		/////////////////////////////////////////////////////
		//// dbQueries.makeVehicleImpounded(chassis));
		/////////////////////////////////////////////////////
		// Start Renewal Process
		changePlateDesignPages = new ChangePlateDesignPages(driver);
		changePlateDesignPages.startProcess();
		changePlateDesignPages.fillVehicleInfo(plateNumber, plateCode, plateCategory);
		changePlateDesignPages.changePlate(PlateSize.Luxury, PlateSize.Long, true);// Add Dubai Logo?
		commonPage.gotoSelectDelivery();

		// Assertion on BR
		assertTrue(commonPage.isBRShown(""));
	}

	@Test
	public void changePlateDesignForCiculatedTrafficFile()
			throws ClassNotFoundException, SQLException, InterruptedException {
		getExpiredVehicle();
		/////////////////////////////////////////////////////
		//// dbQueries.AddCirculationOnTrf(trafficFileNo));
		/////////////////////////////////////////////////////
		// Start Renewal Process
		changePlateDesignPages = new ChangePlateDesignPages(driver);
		changePlateDesignPages.startProcess();
		changePlateDesignPages.fillVehicleInfo(plateNumber, plateCode, plateCategory);
		changePlateDesignPages.changePlate(PlateSize.Luxury, PlateSize.Long, true);// Add Dubai Logo?
		commonPage.gotoSelectDelivery();

		// Assertion on BR
		assertTrue(commonPage.isBRShown(""));
		/////////////////////////////////////////////////////
		//// dbQueries.RemoveCirculationOnTrf(trafficFileNo));
		/////////////////////////////////////////////////////
	}

	private void selectNewDesign(PlateSize frontPlate, PlateSize backPlate, boolean dubaiLogo)
			throws InterruptedException {
		changePlateDesignPages = new ChangePlateDesignPages(driver);
		changePlateDesignPages.startProcess();
		changePlateDesignPages.fillVehicleInfo(plateNumber, plateCode, plateCategory);
		changePlateDesignPages.changePlate(frontPlate, backPlate, dubaiLogo);// Add Dubai Logo?
	}

	private void getValidVehicle() throws ClassNotFoundException, SQLException {
		String[] vehicle = dbQueries.getOwnedPlatesToChangeDesign();
		trafficFileNo = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = vehicle[3];
	}

	private void getExpiredVehicle() throws ClassNotFoundException, SQLException {
		String[] vehicle = dbQueries.getExpiredOwnedPlatesToChangeDesign();
		trafficFileNo = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = vehicle[3];
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
			driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do?switchLanguage=ar");
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