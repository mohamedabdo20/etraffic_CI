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
import isoft.etraffic.drl.ftfpages.IssuingRoadExaminationPage_TC_0020;
import isoft.etraffic.drl.ftfpages.TrainingPage;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class IssueRoadExaminationTest_TC_0020 {
	
	String username = "rta4166";
	String center = "مركز كلداري لتعليم قيادة السيارات - القصيص";
	String trafficFileNo = "13896074";

	WebDriver driver;
	DBQueries dbQueires = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage; 
	IssuingRoadExaminationPage_TC_0020 IssuingRoadExaminationObject;
	TrainingPage trainingPage;
	List<String> transactionsLst = new ArrayList<String>();

	@Test
	public void issueRoadExamination() throws ClassNotFoundException, InterruptedException, SQLException, AWTException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueires.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFileNo);
		commonPage.gotoReqTestsTab();
		
//		trainingPage = new TrainingPage(driver);
//		trainingPage.navigateToTrainingTab();
//		 Thread.sleep(5000);
			
		 IssuingRoadExaminationObject = new IssuingRoadExaminationPage_TC_0020(driver);
		 IssuingRoadExaminationObject.proceedTrs();
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
		//driver.quit();
	}
}



//DRL_TC_0020

//1- الدخول بموظف معهد كلداري الى نظام المرور الالكتروني
//DC315002
//2- الدخول الى شاشة الخدمات الذكية والبحث برقم الملف المروري
//3- فتح نافذة التدريبات المطلوبة والاطلاع على الحصص المطلوبة لكل نوع تدريب
//4-فتح نافذة الفحوصات المطلوبة والضغط على زر اصدار موعد فحص طريق
//6-اختيار الوقت والتاريخ المطلوبين
//7- استكمال المعاملة ودفع الرسوم