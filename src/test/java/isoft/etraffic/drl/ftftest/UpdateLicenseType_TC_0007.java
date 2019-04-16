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
import isoft.etraffic.drl.ftfpages.StudentFileTrsElementsPage;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.*;
import isoft.etraffic.db.DBQueries;

public class UpdateLicenseType_TC_0007 {

	String username = "DC315002";
	String center = "مركز كلداري لتعليم قيادة السيارات - القصيص";

	WebDriver driver;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	StudentFileTrsElementsPage Updatepage;
	List<String> transactionsLst = new ArrayList<String>();
	String testDateTime, trafficFileNo = "";

	@Test
	public void updateLicense_TC_0007() throws ClassNotFoundException, InterruptedException, SQLException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		trafficFileNo = dbQueries.getTrfFileHasManualLicense();
		commonPage = new CommonPage(driver);
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFileNo);
		commonPage.gotoStudentFileTab();
		commonPage.gotoStudentFileService("تعديل صنف الرخصة");

		Updatepage = new StudentFileTrsElementsPage(driver);
		Updatepage.clickUpdateLicenceBtn();

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
		driver.quit();
	}
}
