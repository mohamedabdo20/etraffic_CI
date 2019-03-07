package isoft.etraffic.vhl.ftftest;

 
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.RegistrationPage;
import isoft.etraffic.vhl.ftfpages.TourismPage;

public class TourismCertificateTest extends TestBase{

	String username = "rta13580", center = "مؤسسة الترخيص - ديرة";
	 
	LoginFTFPage loginPage;
	CommonPage commonPage;
	RegistrationPage registrationPage;

	TourismPage tourismPage;
	DBQueries dbQuery = new DBQueries();

	@Parameters({ "trafficFile", "plateCategory", "plateNumber", "plateCode", "chassis", "vehicleWeight", "vehicleClass" })
	@Test
	public void getTourismCertificate(String trafficFile, String plateCategory, String plateNumber, String plateCode, String chassis, String vehicleWeight, String vehicleClass) throws Exception {

		dbQuery.addInsurance(chassis, dbQuery.getVehicleClassEnDescription(vehicleClass));
		dbQuery.removeBlocker(trafficFile);
		 
		System.out.println("plateCode = " + plateCode);
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQuery.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoPlateService("شهادة سياحة");

		tourismPage = new TourismPage(driver);
		tourismPage.proceedTrs("مصر");
		commonPage.payFTF();
	}
}
