package isoft.etraffic.vhl.sdditest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import isoft.etraffic.vhl.sddipages.PrintingRegisteredVehiclesReportPages;

public class PrintingRegisteredVehiclesReportTest {

	String trafficFile;
	WebDriver driver;
	DBQueries dbQueries = new DBQueries();
	CommonPageOnline commonPage;
	PrintingRegisteredVehiclesReportPages printingRegisteredVehiclesReportPages;
	int wait = 0;

	List<String> transactionsLst = new ArrayList<String>();
	ExcelReader ER = new ExcelReader();
	int TotalNumberOfCols = 1;
	String ExcelfileName, sheetname = "PrintingRegisteredVHLReport";

	@DataProvider(name = "PrintingRegisteredVehiclesReport")
	public Object[][] vehicleData(ITestContext context) throws IOException {

		ExcelfileName = context.getCurrentXmlTest().getParameter("filename");
		return ER.getExcelData(ExcelfileName, sheetname, TotalNumberOfCols);
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

	@Test(dataProvider = "PrintingRegisteredVehiclesReport")
	public void printRegisteredVHLReport(String trafficFile) throws Exception {
		commonPage = new CommonPageOnline(driver);
		commonPage.loginByTrafficFile(trafficFile);
		
		dbQueries.removeBlocker(trafficFile);
		
		commonPage.gotoLicensing();
		commonPage.gotoMyVehicles();
		commonPage.gotoMainService("Printing Registered vehicle Report");
		
		printingRegisteredVehiclesReportPages = new PrintingRegisteredVehiclesReportPages(driver);
		printingRegisteredVehiclesReportPages.proceedTrs();

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
			System.out.println("PrintingRegisteredVehiclesReport Online trns: " + trns);
		}
	}
}
