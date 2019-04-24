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
import isoft.etraffic.drl.ftfpages.EmployeesYearPlanPage_TC_0065;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class EmployeesYearPlanTest_TC_0065 {

	String username = "rta4166";
	String center = "مركز كلداري لتعليم قيادة السيارات - القصيص";

	WebDriver driver;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	EmployeesYearPlanPage_TC_0065 employeesYearPlanPage;
	List<String> transactionsLst = new ArrayList<String>();

	@Test
	public void changeFirstCountryProfessionsCount() throws ClassNotFoundException, InterruptedException, SQLException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoDriversTest();
		commonPage.gotoScreen("خطة عمل الفاحصين السنوية");

		employeesYearPlanPage = new EmployeesYearPlanPage_TC_0065(driver);
		employeesYearPlanPage.setStartEndDate("01/07/2018", "30/07/2018");
		employeesYearPlanPage.clickSearchBtn();
		employeesYearPlanPage.selectFirstEmployee();
		employeesYearPlanPage.clickEditBtn();

		employeesYearPlanPage.selectVacation("دورات تدريبية", "٢٠١٨-٠٦-٢٦");
		assertTrue(employeesYearPlanPage.isCountIncremented());

		// Thread.sleep(2000);
		// employeesYearPlanPage.changeCenter("٢٠١٨-١٢-٢٦");
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