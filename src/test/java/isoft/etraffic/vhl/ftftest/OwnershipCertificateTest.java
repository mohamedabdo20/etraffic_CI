package isoft.etraffic.vhl.ftftest;

import java.sql.SQLException;

 
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.PlateCategory;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.enums.VehicleWeight;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.OwnerShipCertificatePage;

public class OwnershipCertificateTest extends TestBase{

	String username = "rta13580";//"rta10686";
	String center = "مؤسسة الترخيص - ديرة";
	
	 
	String trafficFile, plateCategory, plateNumber, plateCode, chassis, weight;
	DBQueries dbQueries = new DBQueries();
	VehicleClass vehicleClass;
	LoginFTFPage loginPage;
	CommonPage commonPage;
	OwnerShipCertificatePage ownerShipCertificatePage;

	@BeforeMethod
	public void setup() throws ClassNotFoundException, SQLException {
		String[] vehicle = dbQueries.getVehicle(VehicleClass.LightVehicle, PlateCategory.Private, VehicleWeight.Any);
		trafficFile = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = vehicle[3];
		chassis = vehicle[4];
		weight = vehicle[5];
		dbQueries.removeBlocker(trafficFile);
		 
	}

	@Test
	public void vehicleOwnerchipCert() throws InterruptedException, ClassNotFoundException, SQLException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoPlateService("شهادة امتلاك مركبة");

		ownerShipCertificatePage = new OwnerShipCertificatePage(driver);
		ownerShipCertificatePage.proceedTrs();
		
		commonPage.payFTF();
	}
}
