package isoft.etraffic.drl.sdditest;

import java.sql.SQLException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import isoft.etraffic.db.DBQueries;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;


public class ExperiencCertificate {
	String trafficFile="10184041";
	
	CommonPageOnline commonPage;
	DBQueries dbQueries = new DBQueries();
	WebDriver driver;

	@Test
	public void testEasy() throws Exception {
		commonPage = new CommonPageOnline(driver);
		commonPage.loginByTrafficFile(trafficFile);
		commonPage.gotoLicensing();
		commonPage.gotoMyLicense();
		commonPage.gotoService("Experience cert");
		commonPage.startTransaction();

		commonPage.selectDeliveryDateFromCenterTrustedAgent(false);
		commonPage.payOnline();
	}

	@BeforeMethod
	public void beforeTest() throws ClassNotFoundException, SQLException {
		dbQueries.removeBlocker(trafficFile);
		
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);
		driver.get("https://tst12c:7793/trfesrv/test_login.jsp");
	}
}