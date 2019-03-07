package isoft.etraffic.vhl.ftftest;

import java.awt.AWTException;
import java.sql.SQLException;

 
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.OldPlateStatus;
import isoft.etraffic.enums.PlateCategory;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.enums.VehicleWeight;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.ExportCertificatePage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class ExportCertificateTest extends TestBase{

	String username = "rta13580";// "rta10686";
	String center = "اينوك"; 
	VehicleClass vehicleClass;
	PlateCategory plateCategoryId;
	VehicleWeight vehicleWeight;
	OldPlateStatus oldPlateStatus = OldPlateStatus.Reserved;
	 
	boolean isOrganization;
	String trafficFile, plateCategory, plateNumber, plateCode, chassis, weight, licenseNumber;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	ExportCertificatePage exportCertificatePage;

	@Parameters({ "vehicleClassValue", "plateCategoryValue", "vehicleWeightRange", "isOrganizationValue" })
	@Test
	public void ExportVehicle(String vehicleClassValue, String plateCategoryValue, String vehicleWeightRange, String isOrganizationValue)
			throws InterruptedException, ClassNotFoundException, SQLException, AWTException {

		vehicleClass = dbQueries.getVehicleClassEnDescription(vehicleClassValue);
		vehicleWeight = dbQueries.setVehicleWeightEnum(vehicleWeightRange);
		plateCategoryId = dbQueries.getPlateCategoryEnum(plateCategoryValue);
		isOrganization = Boolean.parseBoolean(isOrganizationValue);
		setup();
		dbQueries.addInsurance(chassis, vehicleClass);
		dbQueries.addTest(chassis, vehicleClass, weight);
		
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoPlateService("شهادة تصدير");

		exportCertificatePage = new ExportCertificatePage(driver);
		exportCertificatePage.proceedTrs("مصر", oldPlateStatus, true, licenseNumber);

		if (oldPlateStatus.equals(OldPlateStatus.ReturntoRTA) || oldPlateStatus.equals(OldPlateStatus.Reserved)) {
			commonPage.printApplicationForm();
			commonPage.goToPayment();
		}
		commonPage.payFTF();
	}

	@Parameters({ "newOwnertrafficFileNo", "vehicleClassValue", "plateCategoryValue", "vehicleWeightRange" })
	@Test
	public void ExportVehicleToAnotherOwner(String newOwnertrafficFileNo, String vehicleClassValue,
			String plateCategoryValue, String vehicleWeightRange)
			throws InterruptedException, ClassNotFoundException, SQLException, AWTException {

		vehicleClass = dbQueries.getVehicleClassEnDescription(vehicleClassValue);
		vehicleWeight = dbQueries.setVehicleWeightEnum(vehicleWeightRange);
		plateCategoryId = dbQueries.getPlateCategoryEnum(plateCategoryValue);
		//isOrganization = Boolean.parseBoolean(isOrganizationValue);
		setup();
		dbQueries.addInsurance(chassis, vehicleClass);
		dbQueries.addTest(chassis, vehicleClass, weight);
		
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoPlateService("شهادة تصدير");

		exportCertificatePage = new ExportCertificatePage(driver);
		exportCertificatePage.exportToNewOwner("مصر", oldPlateStatus, false, newOwnertrafficFileNo, licenseNumber);

		if (oldPlateStatus.equals(OldPlateStatus.ReturntoRTA) || oldPlateStatus.equals(OldPlateStatus.Reserved)) {
			commonPage.printApplicationForm();
			commonPage.goToPayment();
		}
		commonPage.payFTF();
	}

	private void setup() throws ClassNotFoundException, SQLException {
		String[] vehicle = dbQueries.getVehicle(vehicleClass, vehicleWeight, plateCategoryId, isOrganization);// (vehicleClass,
																												// plateCategoryId,
																												// vehicleWeight);
		trafficFile = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = vehicle[3];
		chassis = vehicle[4];
		weight = vehicle[5];
		
		//dbQueries.addTest(chassis, vehicleClass, weight);

		// trafficFile = "13965518";
		// plateNumber = "79771";
		// plateCode = "U";
		// plateCategory = "خصوصي";
		// chassis = "24102018T14073900";
		// weight = "15000";

		 

		dbQueries.removeBlocker(trafficFile);
		licenseNumber = dbQueries.getLicenseNumber(trafficFile);
	}
}
