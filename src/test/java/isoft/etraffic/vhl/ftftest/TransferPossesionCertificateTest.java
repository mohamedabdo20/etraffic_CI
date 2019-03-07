package isoft.etraffic.vhl.ftftest;

 
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.TransferPossesionCertificatePage;

public class TransferPossesionCertificateTest extends TestBase{
	String trafficFile= "10184041";
	
	String username = "rta13580";// "rta10686";
	String center = "مؤسسة الترخيص - ديرة";
	 
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	public TransferPossesionCertificatePage transferPossesionCertificatePage;

	@Parameters({"certificateNumber"})
	@Test
	public void replaceCertificate(String certificateNumber) throws Exception {

		 
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFile);
		commonPage.gotoMainService("نقل حيازة");

		transferPossesionCertificatePage = new TransferPossesionCertificatePage(driver);
		transferPossesionCertificatePage.proceedTrs(certificateNumber);

		commonPage.payFTF();

	}
}
