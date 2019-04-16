package isoft.etraffic.cta.sdditest;

import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.cta.sddipages.NewTLNOCPage;
import isoft.etraffic.cta.sddipages.UpdateTLpage;
import isoft.etraffic.data.ExcelReader;
import isoft.etraffic.db.CtaDBQueries;
import isoft.etraffic.testbase.TestBase;

public class UpdateTLTest  {

	WebDriver driver;
	
	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
	@Optional("CHROME") String browser, @Optional("en") String lang) throws Exception {
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;

	}
	
	@DataProvider(name = "UpdateTL")
	public Object[][] ActivityData(ITestContext context) throws IOException {
		 String ExcelfileName = context.getCurrentXmlTest().getParameter("filename");
		// get data from Excel Reader class
		ExcelReader ER = new ExcelReader();
		System.out.println(ExcelfileName);
		int TotalNumberOfCols = 3;
		String sheetname = "UpdateTL";
		return ER.getExcelData(ExcelfileName, sheetname, TotalNumberOfCols);}

	
	@Parameters({ "certification", "AppNo", "modify" })
	
	@Test(dataProvider = "UpdateTL")
	private void updateNewTL(String certificatenumber, String AppNo, String modify) throws InterruptedException, ClassNotFoundException, SQLException {

		UpdateTLpage UpdateTLobject = new UpdateTLpage(driver);
		NewTLNOCPage NOCpage = new NewTLNOCPage(driver); 
		CtaDBQueries dbqueries = new CtaDBQueries(); 
		
		UpdateTLobject.searchForComp(certificatenumber, AppNo);

		switch (modify) {
		case "No":
			UpdateTLobject.fillTLData(AppNo, "شركة جديدة", "New Company", "01-01-2019", "01-01-2021", "0506242628",
					"04065958684", "test@test.com", "15619849", "Company Address");
			UpdateTLobject.submitTRX();
			break;
		case "Yes":

			WebElement fileInput = driver.findElement(By.id("tradeLicenseCopyId"));
			fileInput.sendKeys(System.getProperty("user.dir") + "//attachments//Lighthouse.jpg");

			UpdateTLobject.submitTRX();
		default:break;}

		String UpdateTrxID = NOCpage.UpdateTraxID();
		System.out.println("New Transaction Number : " + UpdateTrxID);
		dbqueries.EPSapproval(UpdateTrxID);
	}
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, InterruptedException {
		TestBase testBase = new TestBase();
		testBase.tearDown(result);
		driver = testBase.driver;
	}
}
