package isoft.etraffic.vhl.sdditest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;

public class Replacementlostdamagedpossessioncertificate {
	WebDriver driver;
	String trafficFileNo, certificateNumber;
	DBQueries dbQueries = new DBQueries();
	CommonPageOnline commonPage;
	List<String> transactionsLst = new ArrayList<String>();
	
	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang)
			throws ClassNotFoundException, SQLException, InterruptedException {
		
		String[] ExportCertificateData = dbQueries.getPossessionCertificateData();
		trafficFileNo = ExportCertificateData[1];
		certificateNumber = ExportCertificateData[0];
		
		transactionsLst.add("");
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;
	}
	
	@Test
	public void replaceLostDamagedPossessionCert() throws Exception {

		commonPage = new CommonPageOnline(driver);
		commonPage.loginByTrafficFile(trafficFileNo);
		commonPage.gotoLicensing();
		commonPage.gotoMyVehicles();
		commonPage.gotoMainService("Replacement of lost\\ damaged possession certificate");
		
		// Proceed Trns
		commonPage.waitForElement(By.id("reasonSelect"));
		commonPage.selectFromListByVisibleText(By.id("reasonSelect"), "Lost");
		Thread.sleep(500);
		commonPage.waitForElement(By.id("searchRadio"));
		commonPage.clickElementJS(By.id("searchRadio"));
		commonPage.writeToElement(By.xpath("//*[@id=\"allPageContentId\"]/div[1]/div/div/ul/table/tbody/tr[1]/td[2]/input"), certificateNumber);
		Thread.sleep(500);
		commonPage.clickElementJS(By.id("confirmAndProceedButton"));
		
		commonPage.setDeliveryDetailsOnline();
		
		transactionsLst.remove(transactionsLst.size() - 1);
		transactionsLst.add(commonPage.getTransactionNumber());
		//commonPage.payOnline();
	}
	
	@AfterMethod
	 public void aftermethod() {
	 driver.quit();
	 }

	@AfterClass
	public void afterClass() {
		for (String trns : transactionsLst) {
			System.out.println("ReplacementLostPossessionCert Online trns: " + trns);
		}
	}
}
