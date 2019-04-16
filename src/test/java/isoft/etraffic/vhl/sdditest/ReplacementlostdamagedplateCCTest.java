package isoft.etraffic.vhl.sdditest;

import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.PlateCategory;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.enums.VehicleWeight;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;
import isoft.etraffic.vhl.sddipages.ReplacementLostDamagedPlateNumberPages;

public class ReplacementlostdamagedplateCCTest {

	WebDriver driver;
	String trafficFileNo, plateNumber, plateCode, plateCategory, chassis, weight;
	DBQueries dbQueries = new DBQueries();
	CommonPageOnline commonPage;
	ReplacementLostDamagedPlateNumberPages replacementLostDamagedPlateNumberPages;
	List<String> transactionsLst = new ArrayList<String>();
	
	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang)
			throws ClassNotFoundException, SQLException, InterruptedException {
		
		String[] vehicle = dbQueries.getVehicle(VehicleClass.LightVehicle, VehicleWeight.LessThan3001, PlateCategory.Private, false, false);
		trafficFileNo = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = dbQueries.getPlateCategoryEnDesc(vehicle[3]);
		chassis = vehicle[4];
		weight = vehicle[5];
		dbQueries.removeBlocker(trafficFileNo);
		
		transactionsLst.add("");
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;
	}
	
	@Test
	public void replaceDamagedPlateOnline() throws Exception {
		commonPage = new CommonPageOnline(driver);
		commonPage.loginAsCallCenter("rta2003");
		commonPage.gotoTab("Traffic File No");
		commonPage.searchByTRFFileTrustedAgent(trafficFileNo);
		commonPage.clickFullNameChbox();
		commonPage.clickByLinkTxt("Confirm View Customer");
		
		commonPage.gotoLicensing();
		commonPage.gotoMyVehicles();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoPlateService("Replacement of lost or damaged plate number");
		
		replacementLostDamagedPlateNumberPages = new ReplacementLostDamagedPlateNumberPages(driver);
		replacementLostDamagedPlateNumberPages.proceedTrs();

		if(commonPage.isBRShown(By.id("addRemoveDubaiLogo")))
		{
			transactionsLst.remove(transactionsLst.size() - 1);
			transactionsLst.add(commonPage.getBRText());
			assertTrue(false);
		}
		
		replacementLostDamagedPlateNumberPages.selectNewPlates();
		
		commonPage.setDeliveryDetailsOnline();
		
		transactionsLst.remove(transactionsLst.size() - 1);
		transactionsLst.add(commonPage.getTransactionNumber());
		
		commonPage.payCreditCardCallCenter();
	}
	
	@AfterMethod
	 public void aftermethod() {
	 driver.quit();
	 }

	@AfterClass
	public void afterClass() {
		for (String trns : transactionsLst) {
			System.out.println("Replacementlost/damagedplate Online trns: " + trns);
		}
	}
}
