package isoft.etraffic.drl.ftftest;

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
import isoft.etraffic.drl.ftfpages.EmployeeDailyPlanPage_TC_0013_0014;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class EmployeeDailyPlanTest_TC_0013_0014 {
	String username = "rta4166";
	String center = "مركز كلداري لتعليم قيادة السيارات - القصيص";

	WebDriver driver;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	EmployeeDailyPlanPage_TC_0013_0014 employeeDailyPlanPage;
	List<String> transactionsLst = new ArrayList<String>();

	@Test
	public void checkExaminersManagement_TC_0013() throws ClassNotFoundException, InterruptedException, SQLException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoDriversTest();
		commonPage.gotoScreen("بناء خطة الفاحصين اليومية");
		
		employeeDailyPlanPage = new EmployeeDailyPlanPage_TC_0013_0014(driver);
		employeeDailyPlanPage.selectPeriods();
		employeeDailyPlanPage.clickBuildCalendarBtn();
		//employeeDailyPlanPage.clickAbsentBtn();
	}
	
	@Test
	public void attend_TC_0014() throws ClassNotFoundException, InterruptedException, SQLException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoDriversTest();
		commonPage.gotoScreen("بناء خطة الفاحصين اليومية");
		
		employeeDailyPlanPage = new EmployeeDailyPlanPage_TC_0013_0014(driver);
		employeeDailyPlanPage.clickAbsentBtn();
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