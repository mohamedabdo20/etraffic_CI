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
import isoft.etraffic.drl.ftfpages.SubstitutableCountriesPage_TC_0068;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class SubstitutableCountriesTest_TC_0068 {
	
	String username = "rta4064";
	String center = "مركز كلداري لتعليم قيادة السيارات - القصيص";
	
	WebDriver driver;
	DBQueries dbQueires = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	SubstitutableCountriesPage_TC_0068 substitutableCountriesPage;
	List<String> transactionsLst = new ArrayList<String>();

	@Test
	public void changeFirstCountryProfessionsCount() throws ClassNotFoundException, InterruptedException, SQLException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueires.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoDriversLicense();
		commonPage.gotoScreen("لها باستبدال رخص القيادة");

		substitutableCountriesPage = new SubstitutableCountriesPage_TC_0068(driver);
		substitutableCountriesPage.changeProfessionsCount();
		substitutableCountriesPage.IsCountChanged();
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
