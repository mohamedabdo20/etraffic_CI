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
import isoft.etraffic.drl.ftfpages.AppointmentSetupPage;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class AppointmentSetupTest_TC_193_194_195 {
	String vehicleClass = "مركبة خفيفة", testType="فحص الطريق";
	
	String username = "rta4166";
	String center = "مركز كلداري لتعليم قيادة السيارات - القصيص";

	WebDriver driver;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	AppointmentSetupPage appointmentSetupPage;
	List<String> transactionsLst = new ArrayList<String>();

	@Test
	public void generalSettings() throws AWTException, ClassNotFoundException, InterruptedException, SQLException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoDriversTest();
		commonPage.gotoScreen("إعداد النظام");
		
		appointmentSetupPage = new AppointmentSetupPage(driver);
		appointmentSetupPage.openScreen();
		appointmentSetupPage.searchByVehicleClass(vehicleClass);
		appointmentSetupPage.selectTestType(testType);
		appointmentSetupPage.clickEditBtn();
		appointmentSetupPage.clickSaveBtn();
	}

	@Test
	public void detailsSetting() throws AWTException, ClassNotFoundException, InterruptedException, SQLException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoDriversTest();
		commonPage.gotoScreen("إعداد النظام");
		
		appointmentSetupPage = new AppointmentSetupPage(driver);
		appointmentSetupPage.openScreen();
		appointmentSetupPage.searchByVehicleClass(vehicleClass);
		appointmentSetupPage.selectTestType(testType);
		appointmentSetupPage.clickEditBtn();
		appointmentSetupPage.gotoSettingsDetails();
		appointmentSetupPage.searchByCenter(center);
		appointmentSetupPage.clickEditBtn();
		appointmentSetupPage.clickSaveBtn();
	}
	
	@Test
	public void modifyDetailsSetting() throws AWTException, ClassNotFoundException, InterruptedException, SQLException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoDriversTest();
		commonPage.gotoScreen("إعداد النظام");
		
		appointmentSetupPage = new AppointmentSetupPage(driver);
		appointmentSetupPage.openScreen();
		appointmentSetupPage.searchByVehicleClass(vehicleClass);
		appointmentSetupPage.selectTestType(testType);
		appointmentSetupPage.clickEditBtn();
		appointmentSetupPage.gotoSettingsDetails();
		appointmentSetupPage.searchByCenter(center);
		appointmentSetupPage.clickEditBtn();
		appointmentSetupPage.modifyStartTime("08:30");
		appointmentSetupPage.clickSaveBtn();
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
