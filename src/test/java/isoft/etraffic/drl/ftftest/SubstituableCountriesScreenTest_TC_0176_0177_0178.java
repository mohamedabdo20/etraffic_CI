package isoft.etraffic.drl.ftftest;

import static org.testng.Assert.assertTrue;

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
import isoft.etraffic.drl.ftfpages.SubstituableCountriesScreenPage_TC_0176_0177_0178;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class SubstituableCountriesScreenTest_TC_0176_0177_0178 {

	String country = "الولايات المتحدة" , city = "أوكلاهوما";
	String username = "rta4064";
	String center = "مركز كلداري لتعليم قيادة السيارات - القصيص";

	WebDriver driver;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	SubstituableCountriesScreenPage_TC_0176_0177_0178 substituableCountriesScreenPage;
	List<String> transactionsLst = new ArrayList<String>();

	@Test(priority=1)
	public void addNewTest_TC_0176() throws ClassNotFoundException, InterruptedException, SQLException, AWTException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoDriversLicense();
		commonPage.gotoScreen("نماذج رخصة القيادة");
		
		substituableCountriesScreenPage = new SubstituableCountriesScreenPage_TC_0176_0177_0178(driver);
		substituableCountriesScreenPage.clickAddNewBtn();
		substituableCountriesScreenPage.addNew(country, city);
		
		assertTrue(substituableCountriesScreenPage.isAddedSuccessfully());
	}

	@Test(priority=3)
	public void addNewTest_TC_0177() throws ClassNotFoundException, InterruptedException, SQLException, AWTException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoDriversLicense();
		commonPage.gotoScreen("نماذج رخصة القيادة");
		
		substituableCountriesScreenPage = new SubstituableCountriesScreenPage_TC_0176_0177_0178(driver);
		substituableCountriesScreenPage.clickAddNewBtn();
		substituableCountriesScreenPage.addNew("كندا", "نيوفنلند واللابرادور");
		
		assertTrue(substituableCountriesScreenPage.isAddedSuccessfully());
		assertTrue(substituableCountriesScreenPage.deleteAddedRow());
	}
	
	@Test(priority=2)
	public void addAlreadyExistsTest_TC_0178() throws ClassNotFoundException, InterruptedException, SQLException, AWTException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoDriversLicense();
		commonPage.gotoScreen("نماذج رخصة القيادة");
		
		substituableCountriesScreenPage = new SubstituableCountriesScreenPage_TC_0176_0177_0178(driver);
		substituableCountriesScreenPage.clickAddNewBtn();
		substituableCountriesScreenPage.addNew(country, city);
		
		assertTrue(substituableCountriesScreenPage.addedSameSuccessfully());
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
