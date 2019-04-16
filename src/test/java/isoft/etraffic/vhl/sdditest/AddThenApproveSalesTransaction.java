package isoft.etraffic.vhl.sdditest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;
import isoft.etraffic.vhl.sddipages.SalesTransactionPages;

public class AddThenApproveSalesTransaction {
	
	DBQueries dbQueries =  new DBQueries();
	String sellerTrfFileNo = "50149189", buyerTrfFileNo= "50081030";
	
	WebDriver driver;
	String plateNumber , plateCode , plateCategory;
	CommonPageOnline commonPage;
	SalesTransactionPages salesTransactionPages;
	List<String> transactionsLst = new ArrayList<String>();

	@Test
	public void addSalesTransaction() throws Exception {
		commonPage = new CommonPageOnline(driver);
		commonPage.loginAsTrustedAgent("غسان عبـــود للسيارات - مبايعات - مقدمي الطلبات");
		commonPage.selectTrustedAgentService("Vehicle Sales Transaction");
		commonPage.clickProceedToServiceBtn();
		commonPage.gotoMainService("Vehicle Sales Transaction");
		
		salesTransactionPages = new SalesTransactionPages(driver);
		salesTransactionPages.fillSellerAndBuyerInfo("Registered Vehicle", sellerTrfFileNo, "696406", buyerTrfFileNo, "584237");
		salesTransactionPages.fillDetailsInfo();
		salesTransactionPages.fillVehicleDetails(plateNumber, plateCode);
		salesTransactionPages.clickSaveAndConfirm();
		
		driver.get("https://tst12c:7793/trfesrv/test_login.jsp");
		approveSalesTransaction();
	}

	//@Test
	public void approveSalesTransaction() throws Exception {

		commonPage = new CommonPageOnline(driver);
		commonPage.loginAsTrustedAgent("غسان عبـــود للسيارات - مبايعات - مدققي الطلبات");
		commonPage.selectTrustedAgentService("Vehicle Sales Transaction");
		commonPage.clickProceedToServiceBtn();

		salesTransactionPages = new SalesTransactionPages(driver);
		salesTransactionPages.approveSalesTrns();
		
		commonPage.clickConfrimAndProceedButton();
		commonPage.payCreditCardTrustedAgeny();
	}

//	@BeforeMethod
//	public void beforeTest() throws ClassNotFoundException, SQLException, InterruptedException {
//		String[] vehicle = dbQueries.getLatestVehicleAdded(sellerTrfFileNo);
//		plateNumber = vehicle[1];
//		plateCode = vehicle[2];
//		plateCategory = vehicle[3];
//		//////////////////////////////////////////////
//		dbQueries.removeBlocker(sellerTrfFileNo);
//		dbQueries.removeBlocker(buyerTrfFileNo);
//		
//		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver.exe");
//		ChromeOptions options = new ChromeOptions();
//		options.addArguments("--start-maximized");
//		driver = new ChromeDriver(options);
//		driver.get("https://tst12c:7793/trfesrv/test_login.jsp");
//		///////////////////////////////////////////
//	}
	
	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang)
			throws ClassNotFoundException, SQLException, InterruptedException {
		String[] vehicle = dbQueries.getLatestVehicleAdded(sellerTrfFileNo);
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = vehicle[3];
		//////////////////////////////////////////////
		dbQueries.removeBlocker(sellerTrfFileNo);
		dbQueries.removeBlocker(buyerTrfFileNo);

		transactionsLst.add("");
		System.out.println("transactionsLst.size(): " + transactionsLst.size());
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;
	}
}
