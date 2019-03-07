package isoft.etraffic.vhl.ftftest;

import java.sql.SQLException;

 
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.Replacement;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.ReplacementLostDamagedOwnershipPage;

public class ReplacementLostDamagedRegistrationCard extends TestBase{

	String username = "rta10781";//"rta3224";//"rta10686";
	String center = "مؤسسة الترخيص - ديرة";

	 
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	ReplacementLostDamagedOwnershipPage replacementPage;
	String trafficFile, plateCategory, plateNumber, plateCode, chassis, weight;

	@BeforeMethod
	public void setup() throws ClassNotFoundException, SQLException {
		String[] vehicle = dbQueries.getVehicle();//VehicleClass.LightVehicle, PlateCategory.Private, VehicleWeight.LessThan3001);
		trafficFile = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory =  "خصوصي";// vehicle[3];
		chassis = vehicle[4];
		weight = vehicle[5];
		 
		dbQueries.removeBlocker(trafficFile);
	}

	@Test
	public void replaceLostNumber() throws InterruptedException, ClassNotFoundException, SQLException {

		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoPlateService("ملكية بدل فاقد");
		replacementPage = new ReplacementLostDamagedOwnershipPage(driver);
		replacementPage.proceedTrs(Replacement.Damaged);

		commonPage.payFTF();
	}
}
