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

import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;

public class LostDamageLicense {
	String trafficFile = "10184041";
	CommonPageOnline commonPage;
	WebDriver driver;
	List<String> transactionsLst = new ArrayList<String>();
	@Test
	public void testEasy() throws Exception {
		commonPage = new CommonPageOnline(driver);
		commonPage.loginByTrafficFile(trafficFile);
		commonPage.gotoLicensing();
		commonPage.gotoMyLicense();
		commonPage.gotoService("Lost/Damage License");
		commonPage.startTransaction();
		commonPage.selectFromListByVisibleText(By.xpath("//*[@name='replacementReason']"), "Lost");
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
		driver.quit();
	}
}
