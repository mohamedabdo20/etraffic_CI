package isoft.etraffic.vhl.ftftest;

import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.vhl.ftfpages.ClearanceCertificatePage;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class ClearanceCertificateTest {
	String username = "rta13580";// "rta10686";
	String center = "مؤسسة الترخيص - ديرة";
	String plateNumber, plateCategory, plateCode, trafficFileNo;

	WebDriver driver;
	DBQueries dbQueries = new DBQueries();
	CommonPage commonPage;
	LoginFTFPage loginPage;
	ClearanceCertificatePage clearanceCertificatePage;

	@BeforeMethod
	public void setup() throws ClassNotFoundException, SQLException {
		startBrowser("ie");
		String[] vehicle = dbQueries.getVehicle();
		trafficFileNo = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = "خصوصي";
	}

	@Test
	public void vehicleClearanceCert() throws InterruptedException, ClassNotFoundException, SQLException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		/*commonPage = new CommonPage(driver);
		commonPage.gotoSmartServices();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoPlateService("شهادة براءة ذمة");

		clearanceCertificatePage = new ClearanceCertificatePage(driver);
		clearanceCertificatePage.proceedTrs();

		commonPage.payFTF();*/
	}


	private void startBrowser(String browser) {
		System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\drivers\\IEDriverServer.exe");
		InternetExplorerOptions options =new InternetExplorerOptions();
		options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true); 
		driver = new InternetExplorerDriver(options);
		driver.get("https://tst12c:7791/traffic/faces/jsf/auth/login.jsf");// "https://qctest:4443/traffic/faces/jsf/auth/login.jsf");
		driver.get("javascript:document.getElementById('overridelink').click();");
		driver.manage().window().maximize();
	}
}
