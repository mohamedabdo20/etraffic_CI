package isoft.etraffic.vhl.sdditest;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
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
import isoft.etraffic.vhl.sddipages.CommonPageOnline;
import isoft.etraffic.vhl.sddipages.MortgageAddAndReleasePages;

public class AddThenApproveMortgage {

	WebDriver driver;
	CommonPageOnline commonPage;
	MortgageAddAndReleasePages mortgageAddAndReleasePages;
	String currentday;
	Select list3;
	JavascriptExecutor jsx1;

	// Registered Vehicle
	String chassisRegistered;
	String trafficFile;
	String passport;
	int wait, chassisIndex = 0;
	DBQueries dbQueries = new DBQueries();
	String plateNumber, plateCode, chassisNotRegistered;
	List<String> transactionsLst = new ArrayList<String>();

	@Test
	public void addMortgageForRegisteredVehicle() throws Exception {

		commonPage = new CommonPageOnline(driver);
		commonPage.loginAsTrustedAgent("بنك دبي الاسلامي - اضافة الرهن - مقدم الطلبات");

		mortgageAddAndReleasePages = new MortgageAddAndReleasePages(driver);
		mortgageAddAndReleasePages.gotoPlaceMortgageRequest();
		mortgageAddAndReleasePages.searchByChassis(chassisRegistered);
		mortgageAddAndReleasePages.clickAddMortgageBtn();
		mortgageAddAndReleasePages.setRequiredInfo(trafficFile, passport, currentday);
		mortgageAddAndReleasePages.clickconfirmAndProceedBtn();

		approveMortgage(chassisRegistered, passport);
	}

	// @Test
	public void addMortgage_Not_RegisteredVehicle() throws Exception {

		commonPage = new CommonPageOnline(driver);
		commonPage.loginAsTrustedAgent("بنك دبي الاسلامي - اضافة الرهن - مقدم الطلبات");

		mortgageAddAndReleasePages = new MortgageAddAndReleasePages(driver);
		mortgageAddAndReleasePages.gotoPlaceMortgageRequest();

		mortgageAddAndReleasePages.selectCertificateSource("Dubai");
		mortgageAddAndReleasePages.selectCertificateType("Custom Certificate");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		mortgageAddAndReleasePages.setCertificateInfo(sdf.format(cal.getTime()));

		mortgageAddAndReleasePages.setUnregisteredChassis(chassisNotRegistered);

		mortgageAddAndReleasePages.setUnregisteredVehicleDetails(chassisNotRegistered, sdf.format(cal.getTime()));
		mortgageAddAndReleasePages.clickconfirmAndProceedBtn();

		approveMortgage(chassisNotRegistered, passport);
	}

	private void approveMortgage(String chassisRegistered, String passport) throws Exception {
		driver.get("https://tst12c:7793/trfesrv/test_login.jsp");
		commonPage = new CommonPageOnline(driver);
		commonPage.loginAsTrustedAgent("بنك دبي الاسلامي - اضافة رهن - مدقق الطلبات");

		mortgageAddAndReleasePages = new MortgageAddAndReleasePages(driver);
		mortgageAddAndReleasePages.gotoMortgageRequests();
		mortgageAddAndReleasePages.searchMortgageRequestsByChassis(chassisRegistered);
		mortgageAddAndReleasePages.clickApproveBtn();
		mortgageAddAndReleasePages.setNewOwnerPassport(passport);
		mortgageAddAndReleasePages.clickAprroveAndGotoPayment();

		commonPage.payCashOnline();
		commonPage.printTransactionNumber();
	}

	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang)
			throws ClassNotFoundException, SQLException, InterruptedException {

		String[] vehicle = dbQueries.getVehicle(VehicleClass.LightVehicle, PlateCategory.Private,
				VehicleWeight.LessThan3001);
		trafficFile = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		chassisRegistered = vehicle[4];
		System.out.println("chassisRegistered = " + vehicle[4]);
		passport = vehicle[6];
		System.out.println("passport = " + vehicle[6]);
		dbQueries.removeBlocker(trafficFile);

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		currentday = sdf.format(cal.getTime()).substring(0, sdf.format(cal.getTime()).indexOf('T'));
		chassisNotRegistered = sdf.format(cal.getTime()).replace("-", "").replace(":", "") + "00";

		transactionsLst.add("");
		System.out.println("transactionsLst.size(): " + transactionsLst.size());
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;
	}

	 @AfterMethod
	 public void aftermethod() {
	 driver.quit();
	 }

	@AfterClass
	public void afterClass() {
		for (String trns : transactionsLst) {
			System.out.println("AddThenApproveMortgage trns: " + trns);
		}
	}
}
