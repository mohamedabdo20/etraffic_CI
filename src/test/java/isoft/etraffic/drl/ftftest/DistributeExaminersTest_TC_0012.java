package isoft.etraffic.drl.ftftest;

import static org.testng.Assert.assertTrue;

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
import isoft.etraffic.drl.ftfpages.DistributeExaminersPage_TC_0012;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class DistributeExaminersTest_TC_0012 {
	String username = "rta4166";
	String center = "مركز كلداري لتعليم قيادة السيارات - القصيص";

	WebDriver driver;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	DistributeExaminersPage_TC_0012 distributeExaminersPage;

	List<String> transactionsLst = new ArrayList<String>();

	@Test
	public void checkExaminersManagement() throws ClassNotFoundException, InterruptedException, SQLException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoDriversTest();
		commonPage.gotoScreen("نظام توزيع ومراقبة الفاحصين/المشرفين الذكي");
		
		distributeExaminersPage = new DistributeExaminersPage_TC_0012(driver);
		distributeExaminersPage.gotoExaminersManagement();
		distributeExaminersPage.selectEmployeeName("ابراهيم احمد عبدالله قاسم");
		
	}
	
	@Test
	public void checkCentersIndicators() throws ClassNotFoundException, InterruptedException, SQLException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoDriversTest();
		commonPage.gotoScreen("نظام توزيع ومراقبة الفاحصين/المشرفين الذكي");
		
		distributeExaminersPage = new DistributeExaminersPage_TC_0012(driver);
		distributeExaminersPage.gotoCentersIndicators();
		distributeExaminersPage.setStartAndEndDate();
		distributeExaminersPage.clickSearchBtn();
		
		assertTrue(distributeExaminersPage.isFirstCenterShown());
	}
	
	@Test
	public void checkExaminerDefinition() throws ClassNotFoundException, InterruptedException, SQLException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoDriversTest();
		commonPage.gotoScreen("نظام توزيع ومراقبة الفاحصين/المشرفين الذكي");
		
		distributeExaminersPage = new DistributeExaminersPage_TC_0012(driver);
		distributeExaminersPage.gotoExaminerDefinition();
		distributeExaminersPage.selectExaminerName("ابراهيم احمد عبدالله قاسم");
		distributeExaminersPage.clickSearchBtn();
		distributeExaminersPage.selectFirstResultRow();
		distributeExaminersPage.clickEditBtn();
		
		assertTrue(distributeExaminersPage.isFirstRowLightVehicle());
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
		//driver.quit();
	}
}
