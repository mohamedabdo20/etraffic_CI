package isoft.etraffic.vhl.ftftest;

 
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.ReplacementExportCertifcatePage;

public class ReplacementExportCertificateTest extends TestBase{

	String username = "rta13580";// "rta10686";
	String center = "مؤسسة الترخيص - ديرة";
	 
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	ReplacementExportCertifcatePage replacementPage;

	@Parameters({ "trafficFile", "certificateNumber" })
	@Test
	public void replaceCertificate(String trafficFile, String certificateNumber) throws Exception {
		 
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFile);
		commonPage.gotoMainService("تسفير بدل فاقد/تالف");

		replacementPage = new ReplacementExportCertifcatePage(driver);
		replacementPage.proceedTrs(certificateNumber);

		commonPage.payFTF();
	}
}
