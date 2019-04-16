package isoft.etraffic.cta.ftftest;

import java.awt.AWTException;
import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.cta.ftfpages.AddClassificationPage;
import isoft.etraffic.cta.sddipages.ContractsNavigationPage;
import isoft.etraffic.db.DBQueries;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class AddClassificationOnContract {
	WebDriver driver;
	
	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
	@Optional("CHROME") String browser, @Optional("en") String lang) throws Exception {
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;

	}

	String username = "kareem";
	String center = "معهد الإمارات للسياقة - القصيص";
	 
	@Test
	public void addclassificationoncontract() throws InterruptedException, AWTException, ClassNotFoundException, SQLException {
		
		LoginFTFPage loginftf = new LoginFTFPage(driver);
		DBQueries dbQueries = new DBQueries();
		loginftf.loginFTF(username, dbQueries.getUserPassword(username), center);
		
		ContractsNavigationPage openclassification = new ContractsNavigationPage(driver);
		openclassification.openclassifactionpage();
	//	CreateNewContractTypePage addcontractobject = new CreateNewContractTypePage(driver);
	//	addcontractobject.createNewContractTypePage();
		AddClassificationPage addclassifiction = new AddClassificationPage(driver);
		addclassifiction.addClassificationPage();
		addclassifiction.addVehiclesOnClassification();
}
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, InterruptedException {
		TestBase testBase = new TestBase();
		testBase.tearDown(result);
		driver = testBase.driver;
	}
}
