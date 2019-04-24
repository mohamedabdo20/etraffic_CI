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
import isoft.etraffic.drl.ftfpages.UpdateSponsorInfoPage;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.ChangeVehicleOwnershipPage;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class UpdateSponsorInfo_TC_0168_0170 {
	String trafficFileNo = "12314101";

	String username = "rta4064";
	String center = "مركز كلداري لتعليم قيادة السيارات - القصيص";
	
	WebDriver driver;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	ChangeVehicleOwnershipPage changeVehicleOwnershipPage;
	UpdateSponsorInfoPage updateSponsorInfoPage;
	List<String> transactionsLst = new ArrayList<String>();

	@Test
	public void update_TC_0168() throws ClassNotFoundException, InterruptedException, SQLException, AWTException  {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);
		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFileNo);
		commonPage.gotoMainService("تعديل بيانات الكفيل أو المهنة");
		
		updateSponsorInfoPage = new UpdateSponsorInfoPage(driver);
		updateSponsorInfoPage.updateInfo("مشغل جهاز بلورة السكر", "10184041");
		
		commonPage.confirmFreeTransaction();
	
	}

	//@Test
	public void update_TC_0170() throws ClassNotFoundException, InterruptedException, SQLException, AWTException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);
		commonPage = new CommonPage(driver);
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFileNo);
		commonPage.gotoMainService("تعديل بيانات الكفيل أو المهنة");
		
		updateSponsorInfoPage = new UpdateSponsorInfoPage(driver);
		updateSponsorInfoPage.updateInfo("مشغل جهاز بلورة السكر", "10184041");
		
		commonPage.goToPayment();
		commonPage.payFTF();
		
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
		////driver.quit();
	}
}
