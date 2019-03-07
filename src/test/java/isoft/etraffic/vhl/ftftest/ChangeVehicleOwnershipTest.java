package isoft.etraffic.vhl.ftftest;

import java.awt.AWTException;
import java.sql.SQLException;

 
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.OldPlateStatus;
import isoft.etraffic.enums.PlateCategory;
import isoft.etraffic.enums.PlateDesign;
import isoft.etraffic.enums.PlateSize;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.enums.VehicleWeight;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.ChangeVehicleOwnershipPage;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class ChangeVehicleOwnershipTest extends TestBase{

	String newOwnertrafficFileNo;// = "10184041";
	VehicleClass vehicleClass;// = VehicleClass.LightVehicle;
	PlateCategory plateCategoryId;// = PlateCategory.Private;
	VehicleWeight vehicleWeight;// = VehicleWeight.LessThan3001;
	OldPlateStatus oldPlateStatus = OldPlateStatus.Transfered;
	String username = "rta13580";// "rta10686";
	String center = "مؤسسة الترخيص - ديرة";

	 
	String trafficFileNo, plateNumber, plateCode, plateCategory, chassis, weight;
	DBQueries dbQueires = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	ChangeVehicleOwnershipPage changeVehicleOwnershipPage;

	@Parameters({ "newOwnertrafficFileNoValue", "vehicleClassValue", "plateCategoryValue", "vehicleWeightRange" })
	@Test
	public void changeVehicleOwnership(String newOwnertrafficFileNo, String vehicleClassValue,
			String plateCategoryValue, String vehicleWeightRange)
			throws InterruptedException, ClassNotFoundException, SQLException, AWTException {
		vehicleClass = dbQueires.getVehicleClassEnDescription(vehicleClassValue);
		vehicleWeight = dbQueires.setVehicleWeightEnum(vehicleWeightRange);
		plateCategoryId = dbQueires.getPlateCategoryEnum(plateCategoryValue);
		setup();

		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueires.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(newOwnertrafficFileNo);

		commonPage.gotoMainService("نقل ملكية مركبة");
		changeVehicleOwnershipPage = new ChangeVehicleOwnershipPage(driver);
		changeVehicleOwnershipPage.searchbyPlate(plateCategory, plateCode, plateNumber);
		dbQueires.removeBlocker(newOwnertrafficFileNo);
		changeVehicleOwnershipPage.proceedTrs(oldPlateStatus);

		// PlateStrategy
		//if (plateCode.equals("A") || plateCode.equals("B") || plateCode.equals("C")) {
			commonPage.selectPlateDesign_PStrategy(PlateDesign.New);
			
			if (plateCategoryId == PlateCategory.Private)
				commonPage.selectNewPlates_PStrategy(false, PlateSize.Long, PlateSize.Short);
			else {
				commonPage.skipLogoStep();
				if(plateCategoryId == PlateCategory.Motorcycle || plateCategoryId == PlateCategory.Trailer)
					commonPage.selectNewPlates_PStrategy(PlateSize.Short);
				else commonPage.selectNewPlates_PStrategy(PlateSize.Short, PlateSize.Short);
			}
			
			commonPage.selectCollectionCenter_PStrategy("مركز تسليم - اينوك تسجيل القصيص");
			commonPage.selectDeliveryDate_PStrategy();
			commonPage.clickContinue_PStrategy();
		//}

		commonPage.printApplicationForm();
		commonPage.goToPayment();
//		commonPage.waitForElement(By.xpath("//*[@class='dropdown-menu inner selectpicker']/li[2]/a"));
//		dbQueires.addTestUnRegisteredVehicle(chassis, VehicleClass.HeavyVehicle, weight);
		commonPage.payFTF();
	}

	private void setup() throws ClassNotFoundException, SQLException {
		String[] vehicle = dbQueires.getVehicle(vehicleClass, plateCategoryId, vehicleWeight);
		trafficFileNo = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = vehicle[3];
		chassis = vehicle[4];
		weight = vehicle[5];

		// trafficFileNo = "13965518";
		// plateNumber = "44213";
		// plateCode = "نقل";
		// plateCategory = "نقل";
		// chassis = "18102018T12450100";
		// weight = "15000";

		dbQueires.removeBlocker(trafficFileNo);
		dbQueires.addTest(chassis, vehicleClass, weight);
		 
		dbQueires.addInsurance(chassis, vehicleClass);
	}
}