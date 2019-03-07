package isoft.etraffic.vhl.ftftest;

import static org.testng.Assert.assertTrue;

import java.sql.SQLException;

 
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.VHLPage;

public class PlatesManufacturingAndDistributionTest extends TestBase{

	String username = "rta10519";
	String center = "مؤسسة الترخيص - ديرة";

	 
	String plateCategory, plateNumber, plateCode, chassis, trafficFileNo, weight;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	VHLPage vhlPage;

	@BeforeMethod
	public void setup() throws ClassNotFoundException, SQLException, InterruptedException {
		 
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoVHL();
	}

	@Test
	public void openPlatesManufacturingScreen() throws InterruptedException
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
}
