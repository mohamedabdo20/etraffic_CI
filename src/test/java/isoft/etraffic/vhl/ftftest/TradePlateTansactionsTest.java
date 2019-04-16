package isoft.etraffic.vhl.ftftest;

import static org.testng.Assert.assertTrue;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import isoft.etraffic.data.ExcelReader;
import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.OldPlateStatus;
import isoft.etraffic.enums.PlateSize;
import isoft.etraffic.enums.PlateSource;
import isoft.etraffic.enums.VHLTransaction;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.TradePlatePage;

public class TradePlateTansactionsTest {

	String username = "rta10686";
	String center = "مؤسسة الترخيص - ديرة";
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	TradePlatePage tradePlatePage;
	WebDriver driver;
	List<String> transactionsLst = new ArrayList<String>();
	ExcelReader ER = new ExcelReader();
	int TotalNumberOfCols = 5;
	String testDateTime, ExcelfileName, sheetname = "IssueTradePlate";

	@DataProvider(name = "IssueTradePlate")
	public Object[][] vehicleData(ITestContext context) throws IOException {
		// get data from Excel Reader class

		ExcelfileName = context.getCurrentXmlTest().getParameter("filename");
		return ER.getExcelData(ExcelfileName, sheetname, TotalNumberOfCols);
	}

	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang) throws Exception {
		transactionsLst.add("");
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;
	}

	@Test(dataProvider = "IssueTradePlate")
	public void issueTradePlate(String trafficFileNo) throws Exception {

		System.out.println("-------------Gowaaaaaaaaaaaaaa issueTradePlate---------------------");
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFileNo);
		commonPage.gotoMainService("اصدار ملكية لوحات تجارية");

		tradePlatePage = new TradePlatePage(driver);
		tradePlatePage.issueTradePlate(OldPlateStatus.Lost, PlateSize.Long, PlateSize.Long, PlateSource.Daily, false);

		if (commonPage.isBRShown()) {
			transactionsLst.remove(transactionsLst.size() - 1);
			transactionsLst.add(commonPage.getBRText());
			assertTrue(false);
		}

		commonPage.skipLogoStep();
		commonPage.selectNewPlates_PStrategy(PlateSize.Short, PlateSize.Short);
		commonPage.clickContinue_PStrategy();

		if (commonPage.isBRShown()) {
			transactionsLst.remove(transactionsLst.size() - 1);
			transactionsLst.add(commonPage.getBRText());
			assertTrue(false);
		}

		transactionsLst.remove(transactionsLst.size() - 1);
		transactionsLst.add(commonPage.getTransactionId());

		commonPage.payFTF();
	}

	@Test
	public void renewTradePlate() throws Exception {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		String plateNumber = dbQueries.getTradePlate();
		dbQueries.makeTradePlateExpired(plateNumber);
		commonPage.searchByPlate("تجارية", plateNumber, "");
		commonPage.gotoPlatesTab(plateNumber);
		commonPage.gotoRenewPlate();

		tradePlatePage = new TradePlatePage(driver);
		tradePlatePage.fillInsuranceDetails(true);
		commonPage.clickContinue_PStrategy();
		commonPage.selectNewPlates_PStrategy(PlateSize.Short, PlateSize.Short);
		commonPage.clickContinue_PStrategy();

		if (commonPage.isBRShown()) {
			transactionsLst.remove(transactionsLst.size() - 1);
			transactionsLst.add(commonPage.getBRText());
			assertTrue(false);
		}

		transactionsLst.remove(transactionsLst.size() - 1);
		transactionsLst.add(commonPage.getTransactionId());

		commonPage.payFTF();
	}

	@Test
	public void cancelTradePlate() throws Exception {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		String plateNumber = dbQueries.getTradePlateCancel();
		commonPage.searchByPlate("تجارية", plateNumber, "");
		commonPage.gotoPlatesTab(plateNumber);
		commonPage.gotoCancelPlate();

		tradePlatePage = new TradePlatePage(driver);
		tradePlatePage.proceedTrs();

		if (commonPage.isBRShown()) {
			transactionsLst.remove(transactionsLst.size() - 1);
			transactionsLst.add(commonPage.getBRText());
			assertTrue(false);
		}

		commonPage.printApplicationForm();
		commonPage.goToPayment();

		transactionsLst.remove(transactionsLst.size() - 1);
		transactionsLst.add(commonPage.getTransactionId());
	}

	@AfterMethod
	public void aftermethod(ITestResult result) {
		System.out.println(result.getMethod().getMethodName() + " trns: " + transactionsLst.get(0));
		transactionsLst.remove(transactionsLst.size() - 1);
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

		assertTrue(dbQueries.checkVLDFeesEvent(VHLTransaction.VLD_ISSUE_TRA_PLT, testDateTime));
	}
}
