package isoft.etraffic.cta.ftftest;

import java.awt.AWTException;
import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.cta.sddipages.PushNotificationsPage;
import isoft.etraffic.cta.sddipages.SendNotificationsPage;
import isoft.etraffic.db.DBQueries;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class PushNotifications {

	WebDriver driver;

	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang) throws Exception {
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;

	}

	String username = "kareem";// "rta10686";
	String center = "مركز أيديال سوفت للدعم الفني";
	DBQueries dbQuery = new DBQueries();
	LoginFTFPage loginPage;

	@Test
	public void pushNotifications() throws InterruptedException, AWTException, ClassNotFoundException, SQLException {

		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQuery.getUserPassword(username), center);
		System.out.println("----------------Push Notification----------------");
		PushNotificationsPage pushnoti = new PushNotificationsPage(driver);
		pushnoti.pushNotificationPage();
		System.out.println("----------------Send Notification----------------");
		SendNotificationsPage sendnoti = new SendNotificationsPage(driver);
		sendnoti.sendNotifcationsPage();
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, InterruptedException {
		TestBase testBase = new TestBase();
		testBase.tearDown(result);
		driver = testBase.driver;
	}
}
