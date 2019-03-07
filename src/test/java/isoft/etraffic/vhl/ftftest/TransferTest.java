package isoft.etraffic.vhl.ftftest;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.JavascriptExecutor;
 
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
import isoft.etraffic.vhl.ftfpages.RegisteredVehicleSelectionPage;
import isoft.etraffic.vhl.ftfpages.RegistrationPage;

public class TransferTest extends TestBase{
	// Test Data
	String username = "rta13580";//"rta10686";
	String center = "مؤسسة الترخيص - ديرة";
	VehicleClass vehicleClass;
	PlateCategory plateCategoryId;
	VehicleWeight vehicleWeight;
	OldPlateStatus oldPlateStatus = OldPlateStatus.Reserved;
	String electricVehicleOwner = "13965518";
	boolean isOrganization;
	
	String trafficFile, plateCategory, plateCode, plateNumber, chassis, weight;
	 
	JavascriptExecutor js;
	LoginFTFPage loginPage;
	CommonPage commonPage;
	RegistrationPage registrationPage;
	RegisteredVehicleSelectionPage registeredVehiclePage;
	DBQueries dbQueries= new DBQueries();
	
	
	public void setup() throws Exception {
		String[] vehicle = dbQueries.getVehicle(vehicleClass, plateCategoryId, vehicleWeight);
		//String[] vehicle = dbQueries.getElectricVehicleByTrf(electricVehicleOwner, vehicleWeight);
		trafficFile = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = vehicle[3];
		chassis = vehicle[4];
		weight = vehicle[5];
//		
//		trafficFile = "13965518";
//		plateNumber = "44263";
//		plateCode = "نقل";
//		plateCategory = "نقل";
//		chassis = "22102018T08332300";
//		weight = "15000";
		
		 
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		chassis =  sdf.format(cal.getTime()).replace("-", "").replace(":", "")+"00";
		
		dbQueries.addInsurance(chassis, vehicleClass);
		dbQueries.removeBlocker(trafficFile);
	}

	@Parameters({"vehicleClassValue", "plateCategoryValue", "vehicleWeightRange", "isOrganizationValue"})
	@Test
	public void transfer(String vehicleClassValue, String plateCategoryValue, String vehicleWeightRange, String isOrganizationValue) throws Exception {
		
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
		commonPage.gotoPlateService("شهادة تحويل");
		
		
		registeredVehiclePage = new RegisteredVehicleSelectionPage(driver);
		registeredVehiclePage.transfer("عجمان", oldPlateStatus);
		
		if (oldPlateStatus.equals(OldPlateStatus.ReturntoRTA) || oldPlateStatus.equals(OldPlateStatus.Reserved)) {
			commonPage.printApplicationForm();
			commonPage.goToPayment();
		}
		commonPage.payFTF();
	}

	@Parameters({ "newOwnertrafficFileNo", "vehicleClassValue", "plateCategoryValue", "vehicleWeightRange", "isOrganizationValue" })
	@Test
	public void transferToNewOwner(String newOwnertrafficFileNo, String vehicleClassValue, String plateCategoryValue, String vehicleWeightRange, String isOrganizationValue) throws Exception {
		
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
		commonPage.gotoPlateService("شهادة تحويل");
		
		
		registeredVehiclePage = new RegisteredVehicleSelectionPage(driver);
		registeredVehiclePage.transferToNewOwner("عجمان", oldPlateStatus, newOwnertrafficFileNo);
		
		if (oldPlateStatus.equals(OldPlateStatus.ReturntoRTA) || oldPlateStatus.equals(OldPlateStatus.Reserved)) {
			commonPage.printApplicationForm();
			commonPage.goToPayment();
		}
		commonPage.payFTF();
		//Assert.assertTrue(commonPage.assertPaymentSuccess("المبالغ المستلمة"));
	}
}
