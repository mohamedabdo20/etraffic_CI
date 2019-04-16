package isoft.etraffic.vhl.sdditest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;
import isoft.etraffic.vhl.sddipages.TourismReturnBackPages;

public class TourismReturnBackOnlineTest {
	
	String trafficFileNo = "10019316";
	String plateNumber = "92223";
	String plateCategory = "Private";
	String plateCode = "E";
	String vehicleWeight = "3340";
	String chassis = "JL7BCE1J99K000958";
	VehicleClass vehicleClass = VehicleClass.LightVehicle;
	
	WebDriver driver;
	int wait = 0;
	DBQueries dbQueries = new DBQueries();
	CommonPageOnline commonPage;
	TourismReturnBackPages tourismReturnBackPages;
	List<String> transactionsLst = new ArrayList<String>();
	
	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang)
			throws ClassNotFoundException, SQLException, InterruptedException {
		dbQueries.addTest(chassis, vehicleClass, vehicleWeight);

		transactionsLst.add("");
		System.out.println("transactionsLst.size(): " + transactionsLst.size());
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;
	}
	
	@Test
	public void getReturnFromTourismCertificate() throws Exception {
		
		commonPage = new CommonPageOnline(driver);
		commonPage.loginByTrafficFile(trafficFileNo);
		commonPage.gotoLicensing();
		commonPage.gotoMyVehicles();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoPlateTourismRetunService("Return Back From Tourism");
		
		tourismReturnBackPages = new TourismReturnBackPages(driver);
		tourismReturnBackPages.proceedTrs();
		
		commonPage.payOnline();
	}
	
//	@AfterMethod
//	public void aftermethod() {
//		driver.quit();
//	}

	@AfterClass
	public void afterClass() {
		for (String trns : transactionsLst) {
			System.out.println("ExportCertificate FTF trns: " + trns);
		}
	}
}
