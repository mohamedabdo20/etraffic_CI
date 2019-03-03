package isoft.etraffic.ctatestcases;

import java.sql.SQLException;

import org.testng.annotations.Test;

import isoft.etraffic.ctapages.CertifyNewLuxuryVehicleModelPage;
import isoft.etraffic.ctapages.DeliveryMethod;
import isoft.etraffic.ctapages.ReviewTLPage;
import isoft.etraffic.db.GetTFAndTL;
import isoft.etraffic.db.NewNocApprovals;
import isoft.etraffic.testbase.TestBase;

public class CertifyNewLuxuryVHL extends TestBase {

	@Test
	public void certifyNewLuxuryVHLmodel() throws SQLException, InterruptedException, ClassNotFoundException {
		// CertifyNewLuxuryVehicleModel certifyPage = new
		// CertifyNewLuxuryVehicleModel(driver);
		CertifyNewLuxuryVehicleModelPage certifyPage = new CertifyNewLuxuryVehicleModelPage(driver);

		certifyPage.InitiateService();
		GetTFAndTL getTFAndTL = new GetTFAndTL();
		getTFAndTL.getTFandTLFordealer();
		String TrafficFile = getTFAndTL.TRF;
		String TradeLicense = getTFAndTL.TL;

		certifyPage.logintoservice(TrafficFile, TradeLicense);
		certifyPage.filltransactionsdata("KIA");

		String TrxID = certifyPage.TraxID();
		String AppNo = certifyPage.AppNo();
		System.out.println("Transaction ID" + certifyPage.TraxID());
		System.out.println("Appication No" + certifyPage.AppNo());

		// Approve for EPS

		NewNocApprovals epsapproval = new NewNocApprovals();
		epsapproval.EPSapproval(TrxID);
		epsapproval.TRXupdateStatus(AppNo, "3");
		certifyPage.backtomainservices();

		// Review TL
		ReviewTLPage ReviewObject = new ReviewTLPage(driver);
		ReviewObject.ReviewTL(TrxID, AppNo);

		DeliveryMethod DeliveryObject = new DeliveryMethod(driver);
		DeliveryObject.delivermethod("0501234657", "04065858585", "test@test.com", "test@test.com");
		ReviewObject.SubmitFees();
		
	}

}
