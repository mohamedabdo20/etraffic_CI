package isoft.etraffic.vhl.sdditest;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import isoft.etraffic.data.ExcelReader;
import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.PlateCategory;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.enums.VehicleWeight;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;
import isoft.etraffic.vhl.sddipages.ExportPages;

public class ExportCertificateOnlineTest {

	VehicleClass vehicleClass;
	VehicleWeight vehicleWeight;
	PlateCategory plateCategoryId;
	boolean isOrganization;
	
	WebDriver driver;
	String trafficFileNo, plateNumber, plateCode, plateCategory, chassis, weight;
	DBQueries dbQueries = new DBQueries();
	CommonPageOnline commonPage;
	ExportPages exportPages;
	List<String> transactionsLst = new ArrayList<String>();
	ExcelReader ER = new ExcelReader();
	int TotalNumberOfCols = 4;
	String ExcelfileName, sheetname = "ExportOnline";

	@DataProvider(name = "ExportOnline")
	public Object[][] vehicleData(ITestContext context) throws IOException {
		
		ExcelfileName = context.getCurrentXmlTest().getParameter("filename");
		return ER.getExcelData(ExcelfileName, sheetname, TotalNumberOfCols);
	}

	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang)
			throws ClassNotFoundException, SQLException, InterruptedException {
		transactionsLst.add("");
		TestBase testBase = new TestBase();
		testBase.setup(url, browser, lang);
		driver = testBase.driver;
	}
	
	@Test(dataProvider = "ExportOnline")
	public void exportVehicle(String vehicleClassValue, String plateCategoryValue, String vehicleWeightRange, String isOrganizationValue)
			throws Exception {

		vehicleClass = dbQueries.getVehicleClassEnDescription(vehicleClassValue);
		vehicleWeight = dbQueries.setVehicleWeightEnum(vehicleWeightRange);
		plateCategoryId = dbQueries.getPlateCategoryEnum(plateCategoryValue);
		isOrganization = Boolean.parseBoolean(isOrganizationValue);
		
		getVehicle();

		commonPage = new CommonPageOnline(driver);
		commonPage.loginByTrafficFile(trafficFileNo);
		commonPage.gotoLicensing();
		commonPage.gotoMyVehicles();
		
		dbQueries.addTest(chassis, vehicleClass, weight);
		
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoPlateService("Export Certificate");
		
		exportPages = new ExportPages(driver);
		exportPages.proceedTrs();
		
		if(commonPage.isBRShown(By.id("dateOfDelivery_<%=deliveryDay%>")))
		{
			transactionsLst.remove(transactionsLst.size() - 1);
			transactionsLst.add(commonPage.getBRText());
			assertTrue(false);
		}
		exportPages.setDeliveryDetails();
		
		transactionsLst.remove(transactionsLst.size() - 1);
		transactionsLst.add(commonPage.getTransactionNumber());
		
		commonPage.payOnline();
	}

	private void getVehicle() throws ClassNotFoundException, SQLException {
		String[] vehicle = dbQueries.getVehicle(vehicleClass, plateCategoryId, vehicleWeight);
		trafficFileNo = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = dbQueries.getPlateCategoryEnDesc(vehicle[3]);
		chassis = vehicle[4];
		weight = vehicle[5];
		dbQueries.removeBlocker(trafficFileNo);
	}
	
	@AfterMethod
	 public void aftermethod() {
	 driver.quit();
	 }

	@AfterClass
	public void afterClass() {
		for (String trns : transactionsLst) {
			System.out.println("ExportCertificate Online trns: " + trns);
		}
	}
}
