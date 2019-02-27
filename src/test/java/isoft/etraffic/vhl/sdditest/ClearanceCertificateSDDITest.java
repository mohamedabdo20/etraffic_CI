package isoft.etraffic.vhl.sdditest;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.PlateCategory;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.enums.VehicleWeight;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.sddipages.ClearenceCertificatePage;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;

public class ClearanceCertificateSDDITest extends TestBase {
	VehicleClass vehicleClass = VehicleClass.LightVehicle;
	VehicleWeight vehicleWeight = VehicleWeight.LessThan3001;
	PlateCategory plateCategoryValue = PlateCategory.Private;

	// WebDriver driver;
	String trafficFileNo, plateNumber, plateCode, plateCategory, chassis, weight;
	DBQueries dbQueries = new DBQueries();
	CommonPageOnline commonPage;
	ClearenceCertificatePage clearenceCertificatePage;

	@BeforeMethod
	public void beforeClass() throws Exception {
		String[] vehicle = dbQueries.getVehicle();// vehicleClass, plateCategoryValue, vehicleWeight);
		trafficFileNo = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = dbQueries.getPlateCategoryEnDesc(vehicle[3]);
		chassis = vehicle[4];
		weight = vehicle[5];
		dbQueries.removeBlocker(trafficFileNo);

	}

	@Test
	public void clearanceCertificateSDDITest() throws Exception {

		commonPage = new CommonPageOnline(driver);
		commonPage.loginByTrafficFile(trafficFileNo);
		commonPage.gotoLicensing();
		commonPage.gotoMyVehicles();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoPlateService("Vehicle(s) Clearance Certificate");
		clearenceCertificatePage = new ClearenceCertificatePage(driver);
		clearenceCertificatePage.proceedTrs();
		commonPage.payOnline();
	}

}