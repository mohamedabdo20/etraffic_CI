package isoft.etraffic.vhl.ftftest;

import java.sql.SQLException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.ClearanceCertificatePage;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class ClearanceCertificateTest extends TestBase{
	String username = "rta13580";// "rta10686";
	String center = "مؤسسة الترخيص - ديرة";
	String plateNumber, plateCategory, plateCode, trafficFileNo;
 
	//WebDriver driver;
	DBQueries dbQueries = new DBQueries();
	CommonPage commonPage;
	LoginFTFPage loginPage;
	ClearanceCertificatePage clearanceCertificatePage;

	@BeforeMethod
	public void setup() throws ClassNotFoundException, SQLException {
		//startBrowser("ie");
		String[] vehicle = dbQueries.getVehicle();
		trafficFileNo = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = "خصوصي";
	}

	@Test
	public void vehicleClearanceCert() throws InterruptedException, ClassNotFoundException, SQLException {
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoSmartServices();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoPlateService("شهادة براءة ذمة");

		clearanceCertificatePage = new ClearanceCertificatePage(driver);
		clearanceCertificatePage.proceedTrs();

		commonPage.payFTF();
	}

}
