package isoft.etraffic.vhl.ftftest;

import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import isoft.etraffic.db.DBQueries;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.VHLPage;

public class PlatesManufacturingAndDistributionTest {

	String username = "rta10519";
	String center = "العوير المعارض";

	String plateCategory, plateNumber, plateCode, chassis, trafficFileNo, weight;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	VHLPage vhlPage;
	WebDriver driver;
	List<String> transactionsLst = new ArrayList<String>();

	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang)
			throws ClassNotFoundException, SQLException, InterruptedException {
		
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoVHL();
	}

	@Test
	public void openPlatesManufacturingScreen() throws InterruptedException, ClassNotFoundException, SQLException
	{	
		vhlPage = new VHLPage(driver);
		vhlPage.gotoScreen("تصنيع اللوحات");
		Assert.assertTrue(vhlPage.isUnderVHL());
	}
	
	@Test
	public void openPlateDeliveryScreen() throws InterruptedException
	{
		vhlPage = new VHLPage(driver);
		vhlPage.gotoScreen("تسليم اللوحات");
		assertTrue(vhlPage.isUnderVHL());
	}
	
	@AfterMethod
	public void aftermethod() {
		driver.quit();
	}
}
