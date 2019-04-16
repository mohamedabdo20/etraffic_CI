package isoft.etraffic.drl.ftftest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class THEORYTESTCONG_186 {

	String username = "rta4166";
	String center = "مركز كلداري لتعليم قيادة السيارات - القصيص";

	WebDriver driver;
	DBQueries dbQueires = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	List<String> transactionsLst = new ArrayList<String>();

	@Test
	public void changeFirstCountryProfessionsCount() throws ClassNotFoundException, InterruptedException, SQLException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueires.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoDriversTest();
		commonPage.gotoScreen("إعدادات لغات الفحص النظري");
		commonPage.waitForElement(By.id("actions1:addButtonId"));
		driver.findElement(By.id("actions1:addButtonId")).click();
		commonPage.waitForElement(By.id("vehicleClass:vehicleClassSelectOneMenu"));
		commonPage.selectFromListByVisibleText(By.id("vehicleClass:vehicleClassSelectOneMenu"),"مركبة خفيفة");
		commonPage.waitForElement(By.id("translationLanguage:translationLanguageSelectOneMenu"));
		commonPage.selectFromListByVisibleText(By.id("translationLanguage:translationLanguageSelectOneMenu"),"الهندية");
		commonPage.clickElementJS(By.id("noOfReqDaysBeforeBooking:noOfReqDaysBeforeBooking"));
		commonPage.writeToElement(By.id("noOfReqDaysBeforeBooking:noOfReqDaysBeforeBooking"), "12");
		driver.findElement(By.id("j_idt157:saveButtonId")).click();
		
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
