package isoft.etraffic.vhl.ftftest;

import java.awt.AWTException;
import java.sql.SQLException;

 
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.OldPlateStatus;
import isoft.etraffic.enums.PlateCategory;
import isoft.etraffic.enums.PlateSize;
import isoft.etraffic.enums.PlateSource;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.enums.VehicleWeight;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.ChangePlateNumberPage;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class ChangePlateNumberTest extends TestBase{

	String username = "rta10686";//"rta13580";//"rta10686";
	String center = "مؤسسة الترخيص - ديرة";
	 
	String trafficFile, plateCategory, plateNumber, plateCode, chassis, weight;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	ChangePlateNumberPage changePlateNumberPage;

	public ChangePlateNumberTest() {
		// TODO Auto-generated constructor stub
	}
	
	@BeforeMethod
	public void setup() throws ClassNotFoundException, SQLException {
//		String[] vehicle = dbQueries.getVehicle();
//		trafficFileNo = vehicle[0];
//		plateNumber = vehicle[1];
//		plateCode = vehicle[2];
//		chassis = vehicle[3];
	//	plateCategory = "خصوصي";
		
		String[] vehicle = dbQueries.getVehicle(VehicleClass.LightVehicle, PlateCategory.Private, VehicleWeight.Any);
		trafficFile = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = vehicle[3];
		chassis = vehicle[4];
		weight = vehicle[5];
	}

	@Test
	public void changePlateNumber() throws InterruptedException, ClassNotFoundException, SQLException, AWTException {

		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoSmartServices();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoPlateService("تغيير رقم اللوحة");

		changePlateNumberPage = new ChangePlateNumberPage(driver);
		changePlateNumberPage.proceedTrs(OldPlateStatus.ReturntoRTA, PlateSize.Short, PlateSize.Long, PlateSource.Daily, false);

		commonPage.printApplicationForm();
		commonPage.goToPayment();
		commonPage.payFTF();
		}
}
