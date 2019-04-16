package isoft.etraffic.spl.sdditest;

import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import isoft.etraffic.vhl.sddipages.*;

import isoft.etraffic.cta.sddipages.PaymentCreaditCard;
import isoft.etraffic.spl.sddipages.GeneralPages;
import isoft.etraffic.spl.sddipages.SubscribeToElctronicPage;

public class SubscribeToElectronic {
	
	WebDriver driver;
	String trafficFileNo = "10003030";
	CommonPageOnline commonPage;

	@BeforeTest
	public void beforeTest() throws InterruptedException, ClassNotFoundException, SQLException {
		startBrowser("chrome");
	}

	@Test
	public void subscribeToElectronic() throws InterruptedException {
		commonPage = new CommonPageOnline(driver);
		commonPage.loginByTrafficFile(trafficFileNo);
		commonPage.gotoLicensing();
		
		SubscribeToElctronicPage SubscribeToElctronicobject = new SubscribeToElctronicPage(driver);
		SubscribeToElctronicobject.changePlateOwnership();
	
		
		GeneralPages GP = new GeneralPages(driver);
		GP.applyForService();
		
		SubscribeToElctronicobject.SelectActionType();
		SubscribeToElctronicobject.confirmProcess();

		Thread.sleep(3000);
		PaymentCreaditCard paymentObject = new PaymentCreaditCard(driver);
		paymentObject.paymentcreaditcard(driver);
		
		GP.closehappinies();
		
		String TrxID = SubscribeToElctronicobject.TraxIDForSubscription();
		System.out.println("Transaction ID: " + TrxID);

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
