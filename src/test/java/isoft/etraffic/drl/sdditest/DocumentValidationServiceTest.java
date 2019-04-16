package isoft.etraffic.drl.sdditest;

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
import isoft.etraffic.drl.sddipages.DocumentValidationServicePage;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;


public class DocumentValidationServiceTest {
	String certificateType = "Drivers Experience/History Certificate";
	String certificateNumber = "071681";

	DBQueries dbQueries = new DBQueries();
	WebDriver driver;
	CommonPageOnline commonPage;
	DocumentValidationServicePage documentValidationServicePage;
	List<String> transactionsLst = new ArrayList<String>();
	
	@Test
	public void searchAndViewCertificate() throws Exception {
		certificateNumber = dbQueries.getDRLExperienceCertificate();
		documentValidationServicePage = new DocumentValidationServicePage(driver);
		documentValidationServicePage.searchByCertificate(certificateType, certificateNumber);
	}

	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang)
			throws ClassNotFoundException, SQLException, InterruptedException {
		url = url.substring(0,url.indexOf("trfesrv"));
		System.out.println("---- url ---- : " + url);
		url = url+"trfesrv/public_resources/my-ecertificates.do?preserveParameters=TRUE&switchLanguage=en";
		
		transactionsLst.add("");
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;
		
		driver.get(url);
	}
	
	@AfterMethod
	public void aftermethod(ITestResult result) throws ClassNotFoundException, SQLException {
		System.out.println(result.getMethod().getMethodName() + " trnsNo: " + transactionsLst.get(0));
		transactionsLst.remove(transactionsLst.size() - 1);
		driver.quit();
	}
}
