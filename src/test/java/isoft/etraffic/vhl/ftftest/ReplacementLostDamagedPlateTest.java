package isoft.etraffic.vhl.ftftest;

import java.sql.SQLException;

 
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.OldPlateStatus;
import isoft.etraffic.enums.PlateCategory;
import isoft.etraffic.enums.PlateSize;
import isoft.etraffic.enums.ReplacedPlate;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.enums.VehicleWeight;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.ReplacementLostDamagedPlatePage;

public class ReplacementLostDamagedPlateTest extends TestBase{

	String username = "rta13580";// "isabdulrahman";//"rta10686";
	String center = "مؤسسة الترخيص - ديرة";// "";// "قصيص";//
	VehicleClass vehicleClass = VehicleClass.LightVehicle;
	PlateCategory plateCategoryId = PlateCategory.Private;
	VehicleWeight vehicleWeight = VehicleWeight.Any;
	OldPlateStatus oldPlateStatus = OldPlateStatus.Lost;
	boolean isOrganization = false;

	 
	String trafficFile, plateCategory, plateNumber, plateCode, chassis, weight;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	ReplacementLostDamagedPlatePage replacementPage;

	@BeforeMethod
	public void setup() throws ClassNotFoundException, SQLException {
		String[] vehicle = dbQueries.getVehicle(vehicleClass, vehicleWeight, plateCategoryId, isOrganization);
		trafficFile = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory =  vehicle[3];//"خصوصي";//
		chassis = vehicle[4];
		weight = vehicle[5];
		dbQueries.removeBlocker(trafficFile);
		
		 
	}

	@Test
	public void replaceLostDamagedPlate() throws InterruptedException, ClassNotFoundException, SQLException {

		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoPlateService("رقم بدل فاقد/ تالف");
		
		replacementPage = new ReplacementLostDamagedPlatePage(driver);
		//replacementPage.proceedTrs(oldPlateStatus, ReplacedPlate.Front, PlateSize.Short, false);
		replacementPage.selectOldPlateStatus(oldPlateStatus);
		replacementPage.clickproceedBtn();
		
		commonPage.selectLostPlate_PStrategy(false, ReplacedPlate.Front, PlateSize.Long, PlateSize.Long);
		commonPage.waitImmediateDeliveryBtn();
		commonPage.clickContinue_PStrategy();
		
		commonPage.payFTF();
	}
}
