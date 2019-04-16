package isoft.etraffic.vhl.sdditest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import isoft.etraffic.vhl.sddipages.MortgageAddAndReleasePages;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;

public class AddThenApproveMortgageRelease {
	
	WebDriver driver;
	CommonPageOnline commonPage;
	MortgageAddAndReleasePages addMortgageReleasePages;
	List<String> transactionsLst = new ArrayList<String>();
	
	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang)
			throws ClassNotFoundException, SQLException, InterruptedException {

		transactionsLst.add("");
		System.out.println("transactionsLst.size(): " + transactionsLst.size());
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;
	}
	
	@Parameters({"trafficFile", "plateNumber", "plateCode"})
	@Test
	public void addMortgageRelease(String trafficFile, String plateNumber, String plateCode) throws Exception {

		commonPage = new CommonPageOnline(driver);
		commonPage.loginAsTrustedAgent("بنك دبي الاسلامي - مقدمي الطلبات");
		
		addMortgageReleasePages = new MortgageAddAndReleasePages(driver);
		addMortgageReleasePages.gotoTrafficFileTab();
		addMortgageReleasePages.searchByTRFFile(trafficFile);
		addMortgageReleasePages.clickConfirmViewCustomerProfileBtn();
		commonPage.gotoMyVehicles();
		commonPage.searchByPlate("Private", plateNumber, plateCode);
		commonPage.clickViewVehicleDetails();
		addMortgageReleasePages.addMortgageRelease();
		
		driver.get("https://tst12c:7793/trfesrv/test_login.jsp");
		approveMortgageRelease(trafficFile);
	}

	private void approveMortgageRelease(String trafficFile) throws Exception {
		
		commonPage = new CommonPageOnline(driver);
		commonPage.loginAsTrustedAgent("بنك دبي الاسلامي - مدققي الطلبات");
		
		addMortgageReleasePages = new MortgageAddAndReleasePages(driver);
		addMortgageReleasePages.gotoTrafficFileTab();
		addMortgageReleasePages.gotoMortgageRequests();
		addMortgageReleasePages.searchByTRFFile(trafficFile);
		addMortgageReleasePages.clickApproveBtn();
		addMortgageReleasePages.clickConfirmAndProceedtoPaymentBtn();
		
		commonPage.payCashOnline();
		commonPage.printTransactionNumber();
	}
}

