package isoft.etraffic.drl.sdditest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
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

public class RenewLicense {
	CommonPageOnline commonPage;
	DBQueries dbQueries = new DBQueries();
	LicenseFramePage licenseFramePage;
	WebDriver driver;
	String trafficFile, licenseNumber, birthYear, issueDate,PersonId;
	List<String> transactionsLst = new ArrayList<String>();
	@Test
	public void RenewLicenseTest() throws Exception {
		
		commonPage = new CommonPageOnline(driver);
		commonPage.loginByTrafficFile(trafficFile);
		commonPage.gotoLicensing();
		commonPage.gotoMyLicense();
		commonPage.gotoService("License Renewal");
		commonPage.startTransaction();

		commonPage.clickConfirmAndProceedtoDeliveryMethod();
		commonPage.selectDeliveryDateCourierTrustedAgent(true);
		System.out.println("Actual Amout: " + commonPage.Amount() + " And Expected Amount: " + "345 AED");
		Assert.assertEquals(commonPage.Amount(), "400 AED");
	}

	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang)
			throws ClassNotFoundException, SQLException, InterruptedException {
		String[] person = dbQueries.getExpiredLicense();
		trafficFile = person[0];
		licenseNumber = person[1];
		birthYear = person[2];
		issueDate = person[3];
		PersonId = person[4];

		dbQueries.updatePersonAge(PersonId, "19");
		dbQueries.updatePersonPhoto(PersonId);
		dbQueries.removeBlocker(trafficFile);
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
		//driver.quit();
	}
}
