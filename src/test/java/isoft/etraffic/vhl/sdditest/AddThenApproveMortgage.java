package isoft.etraffic.vhl.sdditest;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import isoft.etraffic.enums.PlateCategory;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.enums.VehicleWeight;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;
import isoft.etraffic.vhl.sddipages.MortgageAddAndReleasePages;
import isoft.etraffic.db.DBQueries;

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

	@BeforeMethod
	public void beforeTest() throws ClassNotFoundException, SQLException {
		////////// DataBaseQuery /////////////////
		 String[] vehicle = dbQueries.getVehicle(VehicleClass.LightVehicle, PlateCategory.Private, VehicleWeight.Between3001AND12000);
		 trafficFile = vehicle[0];
		 plateNumber = vehicle[1];
		 plateCode = vehicle[2];
		 chassisRegistered = vehicle[4];
		 System.out.println("chassisRegistered = "+ vehicle[4]);
		 passport = vehicle[6];
		 System.out.println("passport = "+ vehicle[6]);
		 dbQueries.removeBlocker(trafficFile);
		////////////////////////////////////////////
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);
		driver.get("https://tst12c:7793/trfesrv/test_login.jsp");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		currentday = sdf.format(cal.getTime());
		chassisNotRegistered = sdf.format(cal.getTime()).replace("-", "").replace(":", "") + "00";
		System.out.println("Current Date: " + sdf.format(cal.getTime()));
	}

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

	//@Test
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

	public void approveMortgage(String chassisRegistered, String passport) throws Exception {
//		chassisRegistered = "WP1ZZZ92ZCLA12870";
//		passport = "752618";
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
		// Actions actions = new Actions(driver);
		// actions.moveToElement(driver.findElement(By.xpath("//*[@class='fancybox-overlay
		// fancybox-overlay-fixed']")));
		// actions.sendKeys(Keys.ESCAPE);
		// actions.build().perform();
		//
		// JavascriptExecutor jsx8 = (JavascriptExecutor) driver;
		// jsx8.executeScript("window.scrollBy(0,550)", "");
		// Thread.sleep(5000);
		// driver.findElement(By.xpath("//*[@id='mainDivId']/div[2]/div/a[2]")).click();
	}
}
