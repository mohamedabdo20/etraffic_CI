package isoft.etraffic.vhl.sdditest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.PlateCategory;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.enums.VehicleWeight;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.sddipages.ClearenceCertificatePage;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;

public class ClearanceCertificateOnlineTest {
	VehicleClass vehicleClass = VehicleClass.LightVehicle;
	VehicleWeight vehicleWeight = VehicleWeight.LessThan3001;
	PlateCategory plateCategoryValue = PlateCategory.Private;

	// WebDriver driver;
	String trafficFileNo, plateNumber, plateCode, plateCategory, chassis, weight;
	DBQueries dbQueries = new DBQueries();
	CommonPageOnline commonPage;
	ClearenceCertificatePage clearenceCertificatePage;
	List<String> transactionsLst = new ArrayList<String>();
	WebDriver driver;

	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang)
			throws ClassNotFoundException, SQLException, InterruptedException {
		String[] vehicle = dbQueries.getVehicle();// vehicleClass, plateCategoryValue, vehicleWeight);
		trafficFileNo = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = dbQueries.getPlateCategoryEnDesc(vehicle[3]);
		chassis = vehicle[4];
		weight = vehicle[5];
		dbQueries.removeBlocker(trafficFileNo);

		transactionsLst.add("");
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;
	}

	@Test
	public void clearanceCertificateSDDITest() throws Exception {

		commonPage = new CommonPageOnline(driver);
		commonPage.loginByTrafficFile(trafficFileNo);
		commonPage.gotoLicensing();
		commonPage.gotoMyVehicles();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoPlateService("Vehicle(s) Clearance Certificate");
		clearenceCertificatePage = new ClearenceCertificatePage(driver);
		clearenceCertificatePage.proceedTrs();
		
		transactionsLst.remove(transactionsLst.size() - 1);
		transactionsLst.add(commonPage.getTransactionNumber());
		//commonPage.payOnline();
	}

	@AfterMethod
	 public void aftermethod() {
	 driver.quit();
	 }

	@AfterClass
	public void afterClass() {
		for (String trns : transactionsLst) {
			System.out.println("ClearanceCertificate Online trns: " + trns);
		}
	}
	
}