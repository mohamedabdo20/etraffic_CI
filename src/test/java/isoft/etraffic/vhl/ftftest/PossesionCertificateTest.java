package isoft.etraffic.vhl.ftftest;

 
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.OldPlateStatus;
import isoft.etraffic.enums.PlateCategory;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.enums.VehicleWeight;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.PossessionPage;

public class PossesionCertificateTest extends TestBase{
	String username = "rta13580";// "rta10686";
	String center = "مؤسسة الترخيص - ديرة";
	VehicleClass vehicleClass;
	PlateCategory plateCategoryId;
	VehicleWeight vehicleWeight;
	OldPlateStatus oldPlateStatus = OldPlateStatus.Reserved;
	boolean isOrganization;

	 
	String trafficFile, plateCategory, plateNumber, plateCode, chassis, weight;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	PossessionPage possessionPage;

	@Parameters({ "vehicleClassValue", "plateCategoryValue", "vehicleWeightRange", "isOrganizationValue" })
	@Test
	public void getPossessionCertificate(String vehicleClassValue, String plateCategoryValue, String vehicleWeightRange,
			String isOrganizationValue) throws Exception {

		vehicleClass = dbQueries.getVehicleClassEnDescription(vehicleClassValue);
		vehicleWeight = dbQueries.setVehicleWeightEnum(vehicleWeightRange);
		plateCategoryId = dbQueries.getPlateCategoryEnum(plateCategoryValue);
		isOrganization = Boolean.parseBoolean(isOrganizationValue);
		setup();

		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoPlateService("شهادة حيازة");

		possessionPage = new PossessionPage(driver);
		possessionPage.proceedTrs(oldPlateStatus, false, "");
		commonPage.printApplicationForm();
		commonPage.goToPayment();
//		commonPage.waitForElement(By.xpath("//*[@class='dropdown-menu inner selectpicker']/li[2]/a"));
//		dbQueries.addTestUnRegisteredVehicle(chassis, VehicleClass.HeavyVehicle, "5000");
		commonPage.payFTF();
	}

	 @Parameters({ "newOwnertrafficFileNo", "vehicleClassValue", "plateCategoryValue", "vehicleWeightRange", "isOrganizationValue" })
	 @Test
	public void getPossessionCertificateWithChangeOwner(String newOwnertrafficFileNo, String vehicleClassValue,
			String plateCategoryValue, String vehicleWeightRange, String isOrganizationValue) throws Exception {

		vehicleClass = dbQueries.getVehicleClassEnDescription(vehicleClassValue);
		vehicleWeight = dbQueries.setVehicleWeightEnum(vehicleWeightRange);
		plateCategoryId = dbQueries.getPlateCategoryEnum(plateCategoryValue);
		isOrganization = Boolean.parseBoolean(isOrganizationValue);
		setup();

		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoPlateService("شهادة حيازة");

		possessionPage = new PossessionPage(driver);
		if(trafficFile.equals(newOwnertrafficFileNo) && !trafficFile.equals("10031526"))
			newOwnertrafficFileNo = "10031526";
		dbQueries.removeBlocker(newOwnertrafficFileNo);
		possessionPage.proceedTrs(oldPlateStatus, true, newOwnertrafficFileNo);
		commonPage.printApplicationForm();
		commonPage.goToPayment();
		commonPage.payFTF();
	}

	private void setup() throws Exception {
		String[] vehicle = dbQueries.getVehicle(vehicleClass, vehicleWeight, plateCategoryId, isOrganization);
		trafficFile = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = vehicle[3];
		chassis = vehicle[4];
		weight = vehicle[5];
		dbQueries.removeBlocker(trafficFile);
		 
	}
}
