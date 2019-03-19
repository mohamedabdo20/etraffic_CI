package isoft.etraffic.vhl.sdditest;

import java.sql.SQLException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;
import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.PlateCategory;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.enums.VehicleWeight;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;
import isoft.etraffic.vhl.sddipages.TourismNOCCertificatePage;

public class TourismIssueNOCCertficate {

	VehicleClass vehicleClass = VehicleClass.LightVehicle;
	VehicleWeight vehicleWeight = VehicleWeight.Any;
	PlateCategory plateCategoryValue = PlateCategory.Private;

	WebDriver driver;
	CommonPageOnline commonPage;
	TourismNOCCertificatePage tourismNOCCertificatePage;
	DBQueries dbQueries = new DBQueries();
	String chassis, trafficFileNo, plateCategory, weight, plateNumber, plateCode;

	public TourismIssueNOCCertficate(String chassis, String trafficFileNo, String plateCategory, String plateNumber, String plateCode, String weight) 
	{
		this.chassis = chassis;
		this.trafficFileNo = trafficFileNo;
		this.plateCategory = plateCategory;
		this.weight = weight;
		this.plateNumber = plateNumber;
		this.plateCode = plateCode;
	}

	public void beforeClass() throws ClassNotFoundException, SQLException, InterruptedException {
		String[] vehicle = dbQueries.getVehicle(vehicleClass, plateCategoryValue, vehicleWeight);
		trafficFileNo = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = dbQueries.getPlateCategoryEnDesc(vehicle[3]);
		chassis = vehicle[4];
		weight = vehicle[5];
	}

	@Test(priority = 1, enabled = true)
	public void issueNOCCertificate() throws Exception {
		commonPage = new CommonPageOnline(driver);
		commonPage.loginAsTrustedAgent("نادي الامارات للسيارات و السياحة");
		commonPage.selectTrustedAgentService("Add Noc");
		commonPage.clickByLinkTxt("Proceed to service");
		commonPage.startTransactionNew();

		tourismNOCCertificatePage = new TourismNOCCertificatePage(driver);
		tourismNOCCertificatePage.proceedTrs(plateNumber, plateCategory, plateCode);
	}

	public void startBrowser(String browser, String url) {
		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--new-window");
			options.addArguments("-incognito");
			ChromeDriverService service = ChromeDriverService.createDefaultService();
			driver = new ChromeDriver(service, options);
			// Go to URL
			driver.get(url);
			driver.manage().window().maximize();
			break;
		default:
			System.out.println("Invalid browser type");
			break;
		}
	}

	public void closeDriver() {
		driver.quit();
	}
}
