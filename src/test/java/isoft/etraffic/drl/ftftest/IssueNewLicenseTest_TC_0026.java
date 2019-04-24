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
import isoft.etraffic.drl.ftfpages.IssuingNewLicensePage_TC_0026;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class IssueNewLicenseTest_TC_0026 {
	
	String username = "rta10711";
	String center = "مؤسسة الترخيص";

	String trafficFileNo = "13310339";
	WebDriver driver;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage; 
	IssuingNewLicensePage_TC_0026 IssuingNewLicensePage;
	List<String> transactionsLst = new ArrayList<String>();

	@Test
	public void issueNewLicense() throws ClassNotFoundException, InterruptedException, SQLException, AWTException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);
		commonPage = new CommonPage(driver);
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFileNo);
		commonPage.gotoStudentFileTab();
	
		IssuingNewLicensePage = new IssuingNewLicensePage_TC_0026(driver);
		IssuingNewLicensePage.proceedTrs();
		
		commonPage.printApplicationForm();
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
		//driver.quit();
	}
}



//DRL_TC_0026
//اصدار و طباعة رخصة جديدة لمدة سنة من فحص- Traffic
//1- الدخول الى نظام المرور الالكتروني بموظف الواجهة
//rta10711
//2- الدخول الى خدمات ترخيص السائقين واختيار خدمه اصدار رخصة جديدة
//3-ادخال الملف المروري في شاشة البحث ثم اختيار المصدر للرخصة من فحص
//4-تدقيق المعاملة واستكمالها
//5-طباعه الايصال
//6- ثم يتم الدخول لشاشات ترخيص السائقين واختيار رابط طباعه الرخص
//7- ادخال ملف المرور واختيار السجل والضغط على طباعه الرخصة