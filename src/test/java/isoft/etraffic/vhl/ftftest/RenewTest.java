package isoft.etraffic.vhl.ftftest;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

 
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.OldPlateStatus;
import isoft.etraffic.enums.PlateCategory;
import isoft.etraffic.enums.PlateDesign;
import isoft.etraffic.enums.PlateSize;
import isoft.etraffic.enums.PlateSource;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.enums.VehicleWeight;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.ftfpages.CommonPage;
import isoft.etraffic.vhl.ftfpages.LoginFTFPage;
import isoft.etraffic.vhl.ftfpages.RenewPage;

public class RenewTest extends TestBase{

	/////////////////// Test Data ////////////////////
	VehicleClass vehicleClass = VehicleClass.LightVehicle;
	VehicleWeight vehicleWeight = VehicleWeight.Any;
	PlateCategory plateCategoryValue = PlateCategory.Private;
	OldPlateStatus oldPlateStatus = OldPlateStatus.Reserved;
	boolean isOrganization = false;
	///////////////////////////////////////////////////
	String events = "'EV_VLD_9', 'EV_VLD_35', 'EV_VLD_36', 'EV_VLD_40, 'EV_VLD_41', 'EV_VLD_45', 'EV_VLD_60', 'EV_VLD_61', 'EV_VLD_62', 'EV_VLD_63', 'EV_VLD_66', 'EV_VLD_68', 'EV_VLD_69', 'EV_VLD_70', 'EV_VLD_76', 'EV_VLD_97', 'EV_VLD_122', 'EV_VLD_123', 'EV_VLD_131', 'EV_VLD_134', 'EV_VLD_135'";
	///////////////////////////////////////////////////
	String username = "rta13580";// "rta4733";//"ISAbdulrahman";
	String center = "ينوك تسجيل";// "مؤسسة الترخيص - ديرة";//"اينوك تسجيل جبل علي";//"مؤسسة الترخيص - ديرة"
	// "rta10686";

	 
	String plateCategory, plateNumber, plateCode, chassis, trafficFileNo, weight, testDateTime;
	DBQueries dbQueries = new DBQueries();
	LoginFTFPage loginPage;
	CommonPage commonPage;
	RenewPage renewPage;

	@BeforeMethod
	public void beforeTest() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		testDateTime = sdf.format(cal.getTime());
	}

	@Test
	public void renewVehicleWithCurrentDesign() throws InterruptedException, ClassNotFoundException, SQLException {

		setup();

		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoRenewl();

		renewPage = new RenewPage(driver);
		renewPage.proceedTrs();

		// PlateStrategy
		// if (plateCode.equals("A") || plateCode.equals("B") || plateCode.equals("C"))
		// {
		commonPage.selectPlateDesign_PStrategy(PlateDesign.Current);
		// }

		// commonPage.goToPayment();
		commonPage.payFTF();
		// Assert.assertTrue(driver.findElement(By.xpath("//*[@id='titlePanel']/h1")).getText().contains("ملخص
		// الفواتير ("+fees+" درهم)")
		// ||
		// driver.findElement(By.xpath("//*[@id='titlePanel']/h1")).getText().contains("المبالغ
		// المستلمة ("+fees+" درهم)"));
	}

	@Test
	public void renewVehicleAndSelectNewDesign() throws InterruptedException, ClassNotFoundException, SQLException {

		setup();

		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoRenewl();

		renewPage = new RenewPage(driver);
		renewPage.proceedTrs();

		// PlateStrategy
		// if (plateCode.equals("A") || plateCode.equals("B") || plateCode.equals("C"))
		// {
		commonPage.selectPlateDesign_PStrategy(PlateDesign.New);
		if (plateCategoryValue == PlateCategory.Private)
			commonPage.selectNewPlates_PStrategy(false, PlateSize.Long, PlateSize.Short);
		else {
			commonPage.skipLogoStep();
			if (plateCategoryValue == PlateCategory.Motorcycle || plateCategoryValue == PlateCategory.Trailer)
				commonPage.selectNewPlates_PStrategy(PlateSize.Short);
			else
				commonPage.selectNewPlates_PStrategy(PlateSize.Short, PlateSize.Short);
		}
		commonPage.selectCollectionCenter_PStrategy("مركز تسليم - اينوك تسجيل القصيص");
		commonPage.selectDeliveryDate_PStrategy();
		commonPage.clickContinue_PStrategy();
		// }

		// commonPage.goToPayment();
		commonPage.payFTF();
	}

	@Test
	public void renewAndChangePlate() throws InterruptedException, ClassNotFoundException, SQLException, AWTException {

		setup();
		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoRenewl();

		renewPage = new RenewPage(driver);
		renewPage.changePlateNumber(oldPlateStatus, PlateSource.Reserved, true);
		renewPage.clickproceedBtn();

		commonPage.selectPlateDesign_PStrategy(PlateDesign.New);
		if (plateCategoryValue == PlateCategory.Private)
			commonPage.selectNewPlates_PStrategy(false, PlateSize.Long, PlateSize.Short);
		else {
			commonPage.skipLogoStep();
			if (plateCategoryValue == PlateCategory.Motorcycle || plateCategoryValue == PlateCategory.Trailer)
				commonPage.selectNewPlates_PStrategy(PlateSize.Short);
			else
				commonPage.selectNewPlates_PStrategy(PlateSize.Short, PlateSize.Short);
		}
		commonPage.waitImmediateDeliveryBtn();
		commonPage.clickContinue_PStrategy();

		commonPage.printApplicationForm();
		commonPage.goToPayment();
		commonPage.payFTF();
	}

	@Test
	public void renewAndChangePlateWithPreservedAndAddLogo()
			throws InterruptedException, ClassNotFoundException, SQLException, AWTException {

		String[] vehicle = dbQueries.getVehicleOfTRFHasPreservedPlate();
		trafficFileNo = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = vehicle[3];
		chassis = vehicle[4];
		weight = vehicle[5];

		dbQueries.makeVehicleExpired(chassis);
		dbQueries.removeBlocker(trafficFileNo);

		dbQueries.addInsurance(chassis, vehicleClass);
		dbQueries.addTest(chassis, vehicleClass, weight);

		loginPage = new LoginFTFPage(driver);
		loginPage.loginFTF(username, dbQueries.getUserPassword(username), center);

		commonPage = new CommonPage(driver);
		commonPage.gotoHomePage();
		commonPage.gotoSmartServices();
		commonPage.searchByPlate(plateCategory, plateNumber, plateCode);
		commonPage.gotoRenewl();

		renewPage = new RenewPage(driver);
		renewPage.changePlateNumber(oldPlateStatus, PlateSource.Reserved, true);
		renewPage.clickproceedBtn();

		commonPage.selectNewPlates_PStrategy(true, PlateSize.Long, PlateSize.Short);
		commonPage.waitImmediateDeliveryBtn();
		commonPage.clickContinue_PStrategy();

		commonPage.printApplicationForm();
		commonPage.goToPayment();
		commonPage.payFTF();
	}

	public void setup() throws ClassNotFoundException, SQLException {
		String[] vehicle = dbQueries.getExpiredVehicle(vehicleClass, vehicleWeight, plateCategoryValue, isOrganization);
		trafficFileNo = vehicle[0];
		plateNumber = vehicle[1];
		plateCode = vehicle[2];
		plateCategory = vehicle[3];
		chassis = vehicle[4];
		weight = vehicle[5];

		// trafficFileNo = "10029934";
		// plateNumber = "43215";
		// plateCode = "D";
		// plateCategory = "خصوصي";
		// chassis = "JTHBK262492086677";
		// weight = "1765";
		System.out.println("got");
		if (weight.equals("0")) {
			if (vehicleClass.equals(VehicleClass.HeavyVehicle))
				weight = "3500";
			else
				weight = "2500";
		}

		dbQueries.addTest(chassis, vehicleClass, weight);
		System.out.println("test");
		System.out.println("Ins");
		if (!vehicleClass.equals(VehicleClass.Trailer)) {
			dbQueries.addInsurance(chassis, vehicleClass);
		}

		dbQueries.removeBlocker(trafficFileNo);

	}

	// @AfterMethod
	public void AfterTest() throws ClassNotFoundException, SQLException {
		List<String> eventsList = dbQueries.checkEvents(events, testDateTime);
		assertTrue(eventsList.size() == 0);
	}
}
