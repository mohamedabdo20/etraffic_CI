package isoft.etraffic.vhl.ftftest;

 
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.ReplacementExportCertifcatePage;

public class ReplacementExportCertificateTest {

	String username = "rta13580";
	String center = "مؤسسة الترخيص - ديرة";
	 
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	ReplacementExportCertifcatePage replacementPage;
	String certificateNumber, trafficFile;
	WebDriver driver;
	List<String> transactionsLst = new ArrayList<String>();

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

	@Test
	public void replaceCertificate() throws Exception {
		String[] ExportCertificateData = dbQueries.getExportCertificateData();
		trafficFile = ExportCertificateData[1];
		certificateNumber = ExportCertificateData[0];
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFile);
		commonPage.gotoMainService("تسفير بدل فاقد");

		replacementPage = new ReplacementExportCertifcatePage(driver);
		replacementPage.proceedTrs(certificateNumber);
		
		if (commonPage.isBRShown()) {
			transactionsLst.remove(transactionsLst.size() - 1);
			transactionsLst.add(commonPage.getBRText());
			assertTrue(false);
		}
		
		transactionsLst.remove(transactionsLst.size() - 1);
		transactionsLst.add(commonPage.getTransactionId());
		commonPage.payFTF();
		
		if (!commonPage.transactionFeesAssertion(Integer.parseInt("120"), Integer.parseInt("120"))) {
			String s = (transactionsLst.get(transactionsLst.size() - 1) + " - Fess Faliure");
			transactionsLst.remove(transactionsLst.size() - 1);
			transactionsLst.add(s);}
	}
	
	@AfterMethod
	 public void aftermethod() {
	 driver.quit();
	 }

	@AfterClass
	public void afterClass() {
		for (String trns : transactionsLst) {
			System.out.println("ReplacementExportCertificateTest trns: " + trns);
		}
	}

}
