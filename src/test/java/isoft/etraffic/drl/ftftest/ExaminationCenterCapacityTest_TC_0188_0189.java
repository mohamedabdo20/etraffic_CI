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
import isoft.etraffic.drl.ftfpages.ExaminationCenterCapacityPage_TC_0188_0189;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class ExaminationCenterCapacityTest_TC_0188_0189 {

	String centerName = "معهد الإمارات للسياقة - القصيص";
	String username = "rta4166";
	String center = "مركز كلداري لتعليم قيادة السيارات - القصيص";
	String trafficFileNo = "13644832";

	WebDriver driver;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	ExaminationCenterCapacityPage_TC_0188_0189 examinationCenterCapacityPage;
	List<String> transactionsLst = new ArrayList<String>();

	@Test
	public void search_DLD__TC_0188() throws ClassNotFoundException, InterruptedException, SQLException, AWTException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoDriversTest();
		commonPage.gotoScreen("إعداد النظام");

		examinationCenterCapacityPage = new ExaminationCenterCapacityPage_TC_0188_0189(driver);
		examinationCenterCapacityPage.gotoScreen();
		examinationCenterCapacityPage.searchByCenter(centerName);
		examinationCenterCapacityPage.clickLightVehicle();
		assertTrue(examinationCenterCapacityPage.firstVehicle("مركبة خفيفة"));
	}

	@Test
	public void edit_DLD__TC_0189() throws ClassNotFoundException, InterruptedException, SQLException, AWTException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoDriversTest();
		commonPage.gotoScreen("إعداد النظام");

		examinationCenterCapacityPage = new ExaminationCenterCapacityPage_TC_0188_0189(driver);
		examinationCenterCapacityPage.gotoScreen();
		examinationCenterCapacityPage.searchByCenter(centerName);
		examinationCenterCapacityPage.clickLightVehicle();
		assertTrue(examinationCenterCapacityPage.firstVehicle("مركبة خفيفة"));
		
		examinationCenterCapacityPage.enableFirstResultStatus();
		assertTrue(examinationCenterCapacityPage.isUpdatedSuccessfully());
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
		dbQueries.updateTrfFileEnName(result.getMethod().getMethodName(), trafficFileNo);
		driver.quit();
	}
}