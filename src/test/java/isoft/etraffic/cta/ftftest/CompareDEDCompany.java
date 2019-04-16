package isoft.etraffic.cta.ftftest;

import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import isoft.etraffic.cta.ftfpages.CreateCompareToCTAOrganizationPage;
import isoft.etraffic.data.ExcelReader;
import isoft.etraffic.db.DBQueries;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class CompareDEDCompany  {
	WebDriver driver;
	
	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
	@Optional("CHROME") String browser, @Optional("en") String lang) throws Exception {
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;

	}


	
	@DataProvider(name = "DEDCompare")
	public Object[][] ActivityData(ITestContext context) throws IOException {
		 String ExcelfileName = context.getCurrentXmlTest().getParameter("filename");
		// get data from Excel Reader class
		ExcelReader ER = new ExcelReader();
		System.out.println(ExcelfileName);
		int TotalNumberOfCols = 1;
		String sheetname = "DED";
		return ER.getExcelData(ExcelfileName, sheetname, TotalNumberOfCols);}
	
	String username = "kareem";
	String center = "معهد الإمارات للسياقة - القصيص";

	
	@Description("Make new Compare between Traffic and DED" )
	@Step("This is new compare trade license {0}" )
	@Test(dataProvider="DEDCompare") 
	public void compareDEDCompany (String TradeLicense) throws InterruptedException, ClassNotFoundException, SQLException {
		
		//(String username , String password , String centerName,
		LoginFTFPage loginftf = new LoginFTFPage(driver);
		DBQueries dbQueries = new DBQueries();
		loginftf.loginFTF(username, dbQueries.getUserPassword(username), center);
		
	
		CreateCompareToCTAOrganizationPage dedcompare = new CreateCompareToCTAOrganizationPage(driver);
		//dedcompare.selectTradeLicense(TradeLicense);
		dedcompare.createCompareOrganizationPage(TradeLicense);
		
		
		
		}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, InterruptedException {
		TestBase testBase = new TestBase();
		testBase.tearDown(result);
		driver = testBase.driver;
	}
	}
