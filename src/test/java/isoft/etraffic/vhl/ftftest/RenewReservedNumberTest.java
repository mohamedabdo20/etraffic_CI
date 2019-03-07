package isoft.etraffic.vhl.ftftest;

 
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.ReservationPeriod;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.RenewReservedNumberPage;

public class RenewReservedNumberTest extends TestBase{

	String username = "rta10781";// "rta10686";
	String center = "مؤسسة الترخيص - ديرة";
	 
	LoginFTFPage loginPage;
	CommonPage commonPage;
	RenewReservedNumberPage renewReservedNumberPage;
	DBQueries dbQueries = new DBQueries();
	String trafficFile, plateCategory, plateNumber, plateCode;

	@BeforeMethod
	public void setup() throws Exception {
		
		String[] vehicle = dbQueries.getExpiredReservedNumber();
		trafficFile = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = vehicle[3];
		
		dbQueries.removeBlocker(trafficFile);
		 
	}

	@Test
	public void renew() throws Exception {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoPlatesTab(plateNumber);
		commonPage.gotoRenewPlate();

		renewReservedNumberPage = new RenewReservedNumberPage(driver);
		renewReservedNumberPage.proceedTrs(ReservationPeriod.SixMonths);

		commonPage.payFTF();
	}
}
