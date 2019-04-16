package isoft.etraffic.drl.sdditest;

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
import isoft.etraffic.drl.sddipages.LicenseFramePage;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;

public class LostDamageLicensePublicAccess {

	//String trafficFile = "10184041";
	CommonPageOnline commonPage;
	DBQueries dbQueries = new DBQueries();
	LicenseFramePage licenseFramePage;
	WebDriver driver;
	String trafficFile, plateNumber, plateCode, plateCategory, chassis, weight;
	List<String> transactionsLst = new ArrayList<String>();
	@Test
	public void testEasy() throws Exception {
		commonPage = new CommonPageOnline(driver);
		commonPage.gotoDriversTabPA();
		commonPage.gotoService("استبدال رخصة القيادة تالفة أو مفقودة");
		
		licenseFramePage = new LicenseFramePage(driver);
		licenseFramePage.byPlate(plateNumber, plateCode, plateCategory, trafficFile, dbQueries.getTrfBithYear(trafficFile));
		
		Thread.sleep(30000);
		commonPage.gotoService("فاقد رخصة");
		commonPage.startTransaction();
		commonPage.selectFromListByVisibleText(By.xpath("//*[@name='replacementReason']"), "فاقد");
		commonPage.clickConfirmAndProceedtoDeliveryMethod();
		commonPage.selectDeliveryDateCourierTrustedAgent(true);
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
		dbQueries.updateTrfFileEnName(result.getMethod().getMethodName(), trafficFile);
		driver.quit();
	}
}
