package isoft.etraffic.vhl.ftftest;

 
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.ImportCertificatePage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;

public class ImportCetificateTest extends TestBase{

	String username = "rta4733";
	String center = "اينوك تسجيل جبل علي";
	String trafficFileNo = "10010088";
	String vehicleWeight = "2500";

	 
	LoginFTFPage loginPage;
	CommonPage commonPage;
	ImportCertificatePage importCertificatePage;
	String chassis;

	DBQueries dbQuery = new DBQueries();

	@BeforeMethod
	public void setup() throws Exception {
		 
	}

	@Test
	public void importVehicle() throws Exception {

		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQuery.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFileNo);
		commonPage.gotoMainService("استيراد");

		importCertificatePage = new ImportCertificatePage(driver);
		importCertificatePage.proceedTrs(dbQuery.generateChassisNo());

		commonPage.payFTF();
	}
}
