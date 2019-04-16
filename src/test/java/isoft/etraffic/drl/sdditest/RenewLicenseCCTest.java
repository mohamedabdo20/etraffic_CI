package isoft.etraffic.drl.sdditest;

import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import isoft.etraffic.db.*;
import isoft.etraffic.drl.sddipages.*;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;

public class RenewLicenseCCTest {
	CommonPageOnline commonPage;
	DBQueries dbQueries = new DBQueries();
	LicenseFramePage licenseFramePage;
	WebDriver driver;
	String trafficFile, licenseNumber, birthYear, issueDate,PersonId;

	@Test
	public void RenewLicenseCC() throws Exception {
		commonPage = new CommonPageOnline(driver); 
		commonPage.loginAsCallCenter("rta2003");

		commonPage.gotoTab("Traffic File No.");
		//trafficFile = "10527275";
		commonPage.searchByTRFFileTrustedAgent(trafficFile);
		commonPage.clickFullNameChbox();
		commonPage.clickByLinkTxt("Confirm View Customer Profile");
		commonPage.gotoLicensing();
		commonPage.gotoLicenseTrustedAgent();
		commonPage.gotoService("License Renewal");
		commonPage.startTransaction();

		commonPage.clickConfirmAndProceedtoDeliveryMethod();
		commonPage.selectDeliveryDateCourierTrustedAgent(true);
	}

	@BeforeMethod
	public void beforeTest() throws ClassNotFoundException, SQLException {
		String[] person = dbQueries.getExpiredLicense();
		
		trafficFile = person[0];
		licenseNumber = person[1];
		birthYear = person[2];
		issueDate = person[3];
		PersonId = person[4];

		dbQueries.updatePersonAge(PersonId, "19");
		dbQueries.updatePersonPhoto(PersonId);
		dbQueries.removeBlocker(trafficFile);
		
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);
		driver.get("https://tst12c:7793/trfesrv/test_login.jsp");
	}
}