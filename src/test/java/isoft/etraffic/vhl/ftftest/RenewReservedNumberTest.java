package isoft.etraffic.vhl.ftftest;

 
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.ReservationPeriod;
import isoft.etraffic.enums.VHLTransaction;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.RenewReservedNumberPage;

public class RenewReservedNumberTest {

	String username = "rta10781";// "rta10686";
	String center = "مؤسسة الترخيص - ديرة";
	 
	LoginFTFPage loginPage;
	CommonPage commonPage;
	RenewReservedNumberPage renewReservedNumberPage;
	DBQueries dbQueries = new DBQueries();
	String trafficFile, plateCategory, plateNumber, plateCode, testDateTime;
	WebDriver driver;
	List<String> transactionsLst = new ArrayList<String>();

	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang)
			throws ClassNotFoundException, SQLException, InterruptedException {
		String[] vehicle = dbQueries.getExpiredReservedNumber();
		trafficFile = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = vehicle[3];
		
		dbQueries.removeBlocker(trafficFile);
		
		transactionsLst.add("");
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;
	}
	
	@Test
	public void renewBookingPlateNumber() throws Exception {
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

		if (commonPage.isBRShown()) {
			transactionsLst.remove(transactionsLst.size() - 1);
			transactionsLst.add(commonPage.getBRText());
			assertTrue(false);
		}
		
		transactionsLst.remove(transactionsLst.size() - 1);
		transactionsLst.add(commonPage.getTransactionId());
		
		commonPage.payFTF();
	}
	
	@AfterMethod
	public void aftermethod() {
		driver.quit();
	}

	@BeforeClass
	public void beforeClass() throws ClassNotFoundException, SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		testDateTime = sdf.format(cal.getTime());
	}

	@AfterClass
	public void AfterClass() throws ClassNotFoundException, SQLException {

		for (String trns : transactionsLst) {
			System.out.println("RenewReservedNumber FTF trns: " + trns);
		}
		assertTrue(dbQueries.checkVLDFeesEvent(VHLTransaction.VLD_RENEW_RES_PLT, testDateTime));
	}
}
