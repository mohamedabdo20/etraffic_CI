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
import isoft.etraffic.drl.ftfpages.IssueThenUpdateYardExaminationPage_TC_0015_0017;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class IssueThenUpdateYardExaminationTest_TC_0015_0017 {
	
	String username = "rta4166";
	String center = "مركز كلداري لتعليم قيادة السيارات - القصيص";
	String trafficFileNo = "13810172";

	WebDriver driver;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	IssueThenUpdateYardExaminationPage_TC_0015_0017 issueYardEmaniationPage;
	List<String> transactionsLst = new ArrayList<String>();

	@Test
	public void issue_TC_0015() throws ClassNotFoundException, InterruptedException, SQLException, AWTException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFileNo);
		commonPage.gotoReqTestsTab();
		issueYardEmaniationPage = new IssueThenUpdateYardExaminationPage_TC_0015_0017(driver);
		issueYardEmaniationPage.proceedTrs();
		commonPage.payFTF();
		
		/////
		update_TC_0015();
	}

	@Test
	public void update_TC_0015() throws ClassNotFoundException, InterruptedException, SQLException, AWTException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFileNo);
		commonPage.gotoReqTestsTab();
		issueYardEmaniationPage = new IssueThenUpdateYardExaminationPage_TC_0015_0017(driver);
		issueYardEmaniationPage.updateAppointment();

	}

	@Test
	public void issue_TC_0017() throws ClassNotFoundException, InterruptedException, SQLException, AWTException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFileNo);
		commonPage.gotoReqTestsTab();
		issueYardEmaniationPage = new IssueThenUpdateYardExaminationPage_TC_0015_0017(driver);
		issueYardEmaniationPage.proceedTrs();
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



//DRL_TC_0017

//1- الدخول بموظف معهد كلداري الى نظام المرور الالكتروني
//DC315002
//2- الدخول الى شاشات النظام الذكي وادخال ملف المرور التابع لسيناريو DL_0005
//3- فتح نافذة متابعه الفحوصات والضغط على زر اصدار موعد فحص داخلي
//4- تحديد الوقت والتاريخ 
//5- استكمال المعاملة ودفع الرسوم