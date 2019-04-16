package isoft.etraffic.vhl.sdditest;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.RegistrationPage;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;
import isoft.etraffic.vhl.sddipages.PossessionNonregisteredVehiclePage;

public class PossessionNonRegisteredVehicleTest {
	String trafficFile = "10000159";// "10184041";
	String username = "rta13580";// "rta10686";
	String center = "مؤسسة الترخيص - ديرة";

	WebDriver driver;
	CommonPage ftfCommonPage;
	String chassis, sourceDate;
	LoginFTFPage loginPage;
	CommonPageOnline commonPage;
	RegistrationPage registrationPage;
	PossessionNonregisteredVehiclePage possessionPage;
	DBQueries dbQuery = new DBQueries();
	List<String> transactionsLst = new ArrayList<String>();

	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("ar") String lang)
			throws ClassNotFoundException, SQLException, InterruptedException {
		getFTFURL(url);
		chassisException(getFTFURL(url));

		transactionsLst.add("");
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;
	}

	@Test
	public void getPossesionCertifcate() throws Exception {

		commonPage = new CommonPageOnline(driver);
		commonPage.loginAsTrustedAgent("مؤسسة الترخيص الموثوقة");

		dbQuery.removeBlocker(trafficFile);

		commonPage.gotoTab("Traffic File No.");
		commonPage.searchByTRFFileTrustedAgent(trafficFile);
		commonPage.gotoCustomerProfile();
		commonPage.gotoMyVehicles();
		commonPage.gotoMainService("Non-Registered Vehicle Possession Certificate");

		possessionPage = new PossessionNonregisteredVehiclePage(driver);
		possessionPage.proceedTrs(chassis, sourceDate);

		commonPage.selectDeliveryDateFromCenterTrustedAgent();
		
		transactionsLst.remove(transactionsLst.size() - 1);
		transactionsLst.add(commonPage.getTransactionNumber());
		
		commonPage.payCreditCardTrustedAgeny();
	}

	private void chassisException(String url) throws InterruptedException, ClassNotFoundException, SQLException {
		startIEBrowser(url);
		generateChassisNumber();
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQuery.getUserPassword(username), center);

		ftfCommonPage = new CommonPage(driver);
		ftfCommonPage.gotoHomePage();
		ftfCommonPage.gotoSmartServices();
		ftfCommonPage.searchByTRFFile(trafficFile);
		ftfCommonPage.gotoMainService("تسجيل مركبة جديدة");
		registrationPage = new RegistrationPage(driver);
		registrationPage.setVehicleSourceDetails("دبي", "شهادة جمركية", "123456789", sourceDate, "مركبة خفيفة",
				"مركبة خصوصية", "خصوصي", chassis);
		registrationPage.clickChassisExceptionBtn();
	}

	private String getFTFURL(String url) {
		if (url.contains("https://tst12c"))
			return "https://tst12c:7791/traffic/faces/jsf/auth/login.jsf";
		if (url.contains("https://er12cr2"))
			return "https://er12cr2:7784/trfesrv/test_login.jsp";
		else
			return "https://tst12c:7791/traffic/faces/jsf/auth/login.jsf";

	}
	
	private void generateChassisNumber() throws ClassNotFoundException, SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
		SimpleDateFormat sorceDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		sourceDate = sorceDateFormat.format(cal.getTime());
		chassis = sdf.format(cal.getTime()).replace("-", "").replace(":", "") + "00";
		System.out.println("chassis: " + chassis);
		dbQuery.addInsurance(chassis, VehicleClass.LightVehicle);
		dbQuery.addTestUnRegisteredVehicle(chassis, VehicleClass.LightVehicle, "2500");
	}

	@AfterMethod
	public void aftermethod() {
		driver.quit();
	}

	@AfterClass
	public void afterClass() {
		for (String trns : transactionsLst) {
			System.out.println("PossessionNonRegisteredVehicle trns: " + trns);
		}
	}

	private void startIEBrowser(String url) {
		System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\drivers\\IEDriverServer.exe");
		driver = new InternetExplorerDriver();
		driver.get(url);
		driver.get("javascript:document.getElementById('overridelink').click();");
		driver.manage().window().maximize();
	}
}