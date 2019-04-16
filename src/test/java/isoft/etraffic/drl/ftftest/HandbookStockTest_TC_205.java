package isoft.etraffic.drl.ftftest;

import java.awt.AWTException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.drl.ftfpages.HandbookStockPages;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class HandbookStockTest_TC_205 {
	String username = "rta2132";
	String center = "مؤسسة الترخيص - ديرة";

	WebDriver driver;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	HandbookStockPages handbookStockPages;
	List<String> transactionsLst = new ArrayList<String>();

	@Test
	public void deleteThenCancel() throws ClassNotFoundException, InterruptedException, SQLException, AWTException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTFWithoutCenterSelection(username, dbQueries.getUserPassword(username));

		commonPage = new CommonPage(driver);
		commonPage.clickMainMenuBtn();
		commonPage.gotoDriversTest();
		commonPage.gotoScreen("متابعة مخزون كتيبات التعليم ");
		commonPage.gotoScreen("تحديد الحد الأدنى لمخزون كتيبات التعليم في جميع المراكز ");
		handbookStockPages = new HandbookStockPages(driver);
		handbookStockPages.deleteThenCancel("مركز الأهلي لتعليم قيادة السيارات");
	}
	
	@Test
	public void showDetails() throws ClassNotFoundException, InterruptedException, SQLException, AWTException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTFWithoutCenterSelection(username, dbQueries.getUserPassword(username));

		commonPage = new CommonPage(driver);
		commonPage.clickMainMenuBtn();
		commonPage.gotoDriversTest();
		commonPage.gotoScreen("متابعة مخزون كتيبات التعليم ");
		commonPage.gotoScreen("تحديد الحد الأدنى لمخزون كتيبات التعليم في جميع المراكز ");
		handbookStockPages = new HandbookStockPages(driver);
		handbookStockPages.showDetails("مركز الأهلي لتعليم قيادة السيارات");
	}
	
	@Test
	public void edit() throws ClassNotFoundException, InterruptedException, SQLException, AWTException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTFWithoutCenterSelection(username, dbQueries.getUserPassword(username));

		commonPage = new CommonPage(driver);
		commonPage.clickMainMenuBtn();
		commonPage.gotoDriversTest();
		commonPage.gotoScreen("متابعة مخزون كتيبات التعليم ");
		commonPage.gotoScreen("تحديد الحد الأدنى لمخزون كتيبات التعليم في جميع المراكز ");
		handbookStockPages = new HandbookStockPages(driver);
		handbookStockPages.edit("مركز الأهلي لتعليم قيادة السيارات");
	}

	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang)
			throws ClassNotFoundException, SQLException, InterruptedException {
		transactionsLst.add("");
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;
	}
	
	@AfterMethod
	public void aftermethod(ITestResult result) throws ClassNotFoundException, SQLException {
		System.out.println(result.getMethod().getMethodName() + " trnsNo: " + transactionsLst.get(0));
		transactionsLst.remove(transactionsLst.size() - 1);
		driver.quit();
	}
}
