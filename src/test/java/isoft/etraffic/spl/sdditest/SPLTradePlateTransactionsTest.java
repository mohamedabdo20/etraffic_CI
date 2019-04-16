package isoft.etraffic.spl.sdditest;

import java.awt.AWTException;
import java.sql.SQLException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import isoft.etraffic.db.*;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;
import isoft.etraffic.spl.sddipages.SPLTradePlateTransactionsPages;

public class SPLTradePlateTransactionsTest {
	
	// Test data
	WebDriver driver;
	String trafficFileNo = "11207689", secondTrafficFileNo = "10004004";
	DBQueries dbQueries = new DBQueries();
	CommonPageOnline commonPage;
	SPLTradePlateTransactionsPages issuePlateTradePermitPages;

	@BeforeTest
	public void beforeTest() throws InterruptedException, ClassNotFoundException, SQLException {
		startBrowser("chrome");
	}

	@Test
	public void issueNewTradePlateSPL_TC_33() throws InterruptedException, ClassNotFoundException, SQLException, AWTException {

		commonPage = new CommonPageOnline(driver);
		commonPage.loginByTrafficFile(trafficFileNo);
		commonPage.gotoLicensing();
		commonPage.gotoService("Issue New Trade Plate Permit");
		
		issuePlateTradePermitPages = new SPLTradePlateTransactionsPages(driver);
		issuePlateTradePermitPages.switchtoServiceWindow();
		issuePlateTradePermitPages.acceptTermsAndConditions();
		issuePlateTradePermitPages.addPlatePackage();
		issuePlateTradePermitPages.addTradeLicenseInfo("12345");
		issuePlateTradePermitPages.setShipmentContactDetails();
		
		// Delivery date Calendar
		issuePlateTradePermitPages.clickPayFeesBtn();
		commonPage.payDubaiPay();
		issuePlateTradePermitPages.finish();		
	}
	
	@Test
	public void renewNewTradePlatePermitSPL_TC_34() throws InterruptedException, ClassNotFoundException, SQLException, AWTException {
		
		trafficFileNo = dbQueries.getTrfFileHasTradePermit();
		commonPage = new CommonPageOnline(driver);
		commonPage.loginByTrafficFile(trafficFileNo);
		
		dbQueries.removeBlocker(trafficFileNo);
		dbQueries.makePermitDateExpired(trafficFileNo);
		
		commonPage.gotoLicensing();
		commonPage.gotoService("Renew New Trade Plate Permit");
		issuePlateTradePermitPages = new SPLTradePlateTransactionsPages(driver);
		issuePlateTradePermitPages.switchtoServiceWindow();
		issuePlateTradePermitPages.acceptTermsAndConditions();
		
		issuePlateTradePermitPages.addTradeLicenseInfo2("12345");
		issuePlateTradePermitPages.setShipmentContactDetails();
		
		// Delivery date Calendar
		issuePlateTradePermitPages.clickPayFeesBtn();
		commonPage.payDubaiPay();
		issuePlateTradePermitPages.finish();
	}
	
	@Test
	public void purchasePlatePackagesSPL_TC_35() throws InterruptedException, ClassNotFoundException, SQLException, AWTException {
		
//		trafficFileNo = dbQueries.getTrfFileHasTradePermit();
		commonPage = new CommonPageOnline(driver);
		commonPage.loginByTrafficFile(trafficFileNo);
		dbQueries.removeBlocker(trafficFileNo);
		commonPage.gotoLicensing();
		commonPage.gotoService("Request for special plate");
		
		issuePlateTradePermitPages = new SPLTradePlateTransactionsPages(driver);
		issuePlateTradePermitPages.switchtoServiceWindow();
		issuePlateTradePermitPages.selectPurchasingType("Plate Packages");
		
		issuePlateTradePermitPages.setShipmentContactDetails();
		
		// Delivery date Calendar
		issuePlateTradePermitPages.clickPayFeesBtn();
		commonPage.payDubaiPay();
		issuePlateTradePermitPages.finish();		
	}
	
	@Test
	public void changePlateOwnershipSPL_TC_36() throws InterruptedException, ClassNotFoundException, SQLException, AWTException {
		
		//trafficFileNo = dbQueries.getTrfFileHasTradePermit();
		commonPage = new CommonPageOnline(driver);
		commonPage.loginByTrafficFile(trafficFileNo);
		dbQueries.removeBlocker(trafficFileNo);
		commonPage.gotoLicensing();
		commonPage.gotoService("My Trade Plate");
		
		issuePlateTradePermitPages = new SPLTradePlateTransactionsPages(driver);
		issuePlateTradePermitPages.switchtoServiceWindow();
		issuePlateTradePermitPages.selectPlate();
		String password = dbQueries.getTrfPassword(trafficFileNo);
		issuePlateTradePermitPages.setPassword(password);
		issuePlateTradePermitPages.setNewOwnerInfo(dbQueries.getTrfFileHasTradePermit());
		
		// Delivery date Calendar
		issuePlateTradePermitPages.clickPayFeesBtn();
		commonPage.payDubaiPay();
		issuePlateTradePermitPages.finish();		
	}
	
	@Test
	public void bidAuctionSPL_TC_40() throws InterruptedException, ClassNotFoundException, SQLException, AWTException {

		//trafficFileNo = dbQueries.getTrfFileHasTradePermit();
		commonPage = new CommonPageOnline(driver);
		commonPage.loginByTrafficFile(trafficFileNo);
		
		dbQueries.removeBlocker(trafficFileNo);
		commonPage.gotoLicensing();
		commonPage.gotoService("Request for special plate");
		issuePlateTradePermitPages = new SPLTradePlateTransactionsPages(driver);
		issuePlateTradePermitPages.switchtoServiceWindow();
		commonPage.gotoService("Package Auction");
		
		issuePlateTradePermitPages.clickBidBtn();
	}
	
	@Test
	public void bidAuctionSPL_TC_27() throws InterruptedException, ClassNotFoundException, SQLException, AWTException {

		//trafficFileNo = dbQueries.getTrfFileHasTradePermit();
		commonPage = new CommonPageOnline(driver);
		commonPage.loginByTrafficFile(trafficFileNo);
		
		dbQueries.removeBlocker(trafficFileNo);
		commonPage.gotoLicensing();
		commonPage.gotoService("Request for special plate");
		issuePlateTradePermitPages = new SPLTradePlateTransactionsPages(driver);
		issuePlateTradePermitPages.switchtoServiceWindow();
		commonPage.gotoService("Electronic Auction");
		
		issuePlateTradePermitPages.bidForFirstUser();
		
		issuePlateTradePermitPages.switchtoParentWindow();
		driver.get("https://tst12c:7793/trfesrv/test_login.jsp");
		commonPage.loginByTrafficFile(secondTrafficFileNo);
		
		dbQueries.removeBlocker(trafficFileNo);
		commonPage.gotoLicensing();
		commonPage.gotoService("Request for special plate");
		issuePlateTradePermitPages.switchtoServiceWindow();
		commonPage.gotoService("Electronic Auction");
		
		issuePlateTradePermitPages.bidForSecondUser();
		
		
	}
	
	private void startBrowser(String browser) {
		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--new-window");
			options.addArguments("-incognito");
			ChromeDriverService service = ChromeDriverService.createDefaultService();
			driver = new ChromeDriver(service, options);
			// Go to URL
			driver.get("https://tst12c:7793/trfesrv/test_login.jsp");
			driver.manage().window().maximize();
			break;
		case "ie":
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			// Go to URL
			driver.get("https://tst12c:7793/trfesrv/test_login.jsp");
			break;
		default:
			System.out.println("Invalid browser type");
			break;
		}
	}
}