package isoft.etraffic.spl.sdditest;

import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;
import isoft.etraffic.spl.sddipages.BuyPlateBlackListTestPage;
import isoft.etraffic.spl.sddipages.GeneralPages;

public class BuyPlateBlackList {

	WebDriver driver;
	@BeforeTest
	public void beforeTest() throws InterruptedException, ClassNotFoundException, SQLException {
		startBrowser("chrome");
	}

	@Test
	public void buyPlateBlackList() throws InterruptedException, ClassNotFoundException, SQLException {
		DBQueries dbQueries = new DBQueries();
		dbQueries.getTrafficFileHasBlackList();
		String TrafficFile = dbQueries.TrafficFileNo;
	
		CommonPageOnline commonPage;
		commonPage = new CommonPageOnline(driver);
		commonPage.loginByTrafficFile(TrafficFile);
		commonPage.gotoLicensing();

		BuyPlateBlackListTestPage blackplateobject = new BuyPlateBlackListTestPage(driver);
		blackplateobject.buyPlateBlackListTestPage();

		Thread.sleep(2000);
		GeneralPages GP = new GeneralPages(driver);
		GP.applyForService();

		Thread.sleep(2000);
		blackplateobject.confirmProcess();

		Thread.sleep(2000);
		blackplateobject.AssertionBR();

	}

	private void startBrowser(String browser) {
		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--new-window");
			options.addArguments("-incognito");
			ChromeDriverService service = ChromeDriverService.createDefaultService();
			driver = new ChromeDriver(service, options);
			// Go to URL
			driver.get("https://tst12c:7793/trfesrv/test_login.jsp");
			driver.manage().window().maximize();
			break;
		case "ie":
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			// Go to URL
			driver.get("https://tst12c:7793/trfesrv/test_login.jsp");
			break;
		default:
			System.out.println("Invalid browser type");
			break;
		}
	}

}
