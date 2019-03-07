package isoft.etraffic.vhl.ftftest;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.OldPlateStatus;
import isoft.etraffic.enums.PlateSize;
import isoft.etraffic.enums.PlateSource;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.TradePlatePage;

public class TradePlateTansactionsTest extends TestBase{

	String username = "rta10686";
	String center = "مؤسسة الترخيص - ديرة";
	DBQueries dbQuery = new DBQueries(); 
	LoginFTFPage loginPage;
	CommonPage commonPage;
	TradePlatePage tradePlatePage;
	

	@Parameters({"trafficFileNo"})
	@Test
	public void issueTradePlate(String trafficFileNo) throws Exception {

		System.out.println("-------------Gowaaaaaaaaaaaaaa---------------------");
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQuery.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByTRFFile(trafficFileNo);
		commonPage.gotoMainService("اصدار ملكية لوحات تجارية");

		tradePlatePage = new TradePlatePage(driver);
		tradePlatePage.issueTradePlate(OldPlateStatus.Lost, PlateSize.Long, PlateSize.Long, PlateSource.Daily, false);
		
		commonPage.skipLogoStep();
		commonPage.selectNewPlates_PStrategy(PlateSize.Short, PlateSize.Short);
		commonPage.clickContinue_PStrategy();
		commonPage.payFTF();
	}

	@Test
	public void renewTradePlate() throws Exception
	{
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQuery.getUserPassword(username), center);
		
		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		String plateNumber = dbQuery.getTradePlate();
		dbQuery.makeTradePlateExpired(plateNumber);
		commonPage.searchByPlate("تجارية", plateNumber, "");
		commonPage.gotoPlatesTab(plateNumber);
		commonPage.gotoRenewPlate();
		
		tradePlatePage = new TradePlatePage(driver);
		tradePlatePage.fillInsuranceDetails(true);
		commonPage.clickContinue_PStrategy();
		commonPage.selectNewPlates_PStrategy(PlateSize.Short, PlateSize.Short);
		commonPage.clickContinue_PStrategy();

		commonPage.payFTF();
	}
	
	@Test
	public void cancelTradePlate() throws Exception
	{
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQuery.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		String plateNumber = dbQuery.getTradePlate();
		commonPage.searchByPlate("تجارية", plateNumber, "");
		commonPage.gotoPlatesTab(plateNumber);
		commonPage.gotoCancelPlate();
		
		tradePlatePage = new TradePlatePage(driver);
		tradePlatePage.proceedTrs();
		
		commonPage.printApplicationForm();
		commonPage.goToPayment();
		//Free Service commonPage.payFTF();	
	}
}
