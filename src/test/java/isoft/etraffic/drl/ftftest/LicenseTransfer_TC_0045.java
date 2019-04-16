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
import isoft.etraffic.drl.ftfpages.LicenseTransferPage;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.ChangeVehicleOwnershipPage;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class LicenseTransfer_TC_0045 {
	
	String trafficFileNo = "13023111";

	String username = "rta10711";
	String center = "مؤسسة الترخيص - بر دبي";
	
	WebDriver driver;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	ChangeVehicleOwnershipPage changeVehicleOwnershipPage;
	LicenseTransferPage licenseTransferPage;
	List<String> transactionsLst = new ArrayList<String>();

	@Test
	public void transfer() throws ClassNotFoundException, InterruptedException, SQLException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);
		commonPage = new CommonPage(driver);
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFileNo);
		commonPage.gotoMainService("تحويل رخصة");

		licenseTransferPage=new LicenseTransferPage(driver);
		licenseTransferPage.proceedTrs();
		
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
		driver.quit();
	}
}


//DRL_TC_0045

//تحويل رخصة
//1- ملف مرور لديه رخصة فعالة
//2- تعديل الاسم بالانجليزي برقم السيناريو
//Sheet name: update name  for scenarios