package isoft.etraffic.vhl.sdditest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.PlateCategory;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.enums.VehicleWeight;
import isoft.etraffic.testbase.TestBase;
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

	public void closeDriver() {
		driver.quit();
	}

	public void startBrowser(String browser, String serverURL) {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--new-window");
		options.addArguments("-incognito");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get(serverURL);
	}
}
