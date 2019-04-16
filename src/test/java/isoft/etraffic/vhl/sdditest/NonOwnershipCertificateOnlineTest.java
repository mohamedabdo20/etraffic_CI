package isoft.etraffic.vhl.sdditest;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import isoft.etraffic.data.ExcelReader;
import isoft.etraffic.db.DBQueries;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;
import isoft.etraffic.vhl.sddipages.NonOwnershipCertificatePages;

public class NonOwnershipCertificateOnlineTest {

	WebDriver driver;
	String trafficFile;
	DBQueries dbQueries = new DBQueries();
	CommonPageOnline commonPage;
	NonOwnershipCertificatePages nonOwnershipCertificate;
	List<String> transactionsLst = new ArrayList<String>();
	ExcelReader ER = new ExcelReader();
	int TotalNumberOfCols = 1;
	String ExcelfileName, sheetname = "NonOwnerShipOnline";

	@DataProvider(name = "NonOwnerShipOnline")
	public Object[][] vehicleData(ITestContext context) throws IOException {
		
		ExcelfileName = context.getCurrentXmlTest().getParameter("filename");
		return ER.getExcelData(ExcelfileName, sheetname, TotalNumberOfCols);
	}
	
	@BeforeMethod
	@Parameters({"url", "browser", "lang"})
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang)
			throws ClassNotFoundException, SQLException, InterruptedException {
		transactionsLst.add("");
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;
	}
	
	@Test(dataProvider = "NonOwnerShipOnline")
	public void getNonOwnershipCertificate(String trafficFile) throws Exception {
		dbQueries.removeBlocker(trafficFile);
		
		commonPage = new CommonPageOnline(driver);
		commonPage.loginByTrafficFile(trafficFile);
		commonPage.gotoLicensing();
		commonPage.gotoMyVehicles();
		commonPage.gotoMainService("Non-ownership Certificate");
		
		nonOwnershipCertificate = new NonOwnershipCertificatePages(driver);
		nonOwnershipCertificate.proceedTrs();

		if(commonPage.isBRShown(By.id("dateOfDelivery_<%=deliveryDay%>")))
		{
			transactionsLst.remove(transactionsLst.size() - 1);
			transactionsLst.add(commonPage.getBRText());
			assertTrue(false);
		}
		
		commonPage.setDeliveryDetails();
		
		transactionsLst.remove(transactionsLst.size() - 1);
		transactionsLst.add(commonPage.getTransactionNumber());
		
		commonPage.payOnline();
	}
	
	@AfterMethod
	 public void aftermethod() {
	 driver.quit();
	 }

	@AfterClass
	public void afterClass() {
		for (String trns : transactionsLst) {
			System.out.println("NonOwnerShipCertificate Online trns: " + trns);
		}
	}
}
